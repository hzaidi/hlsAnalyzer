package hls.analyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

public class EXTINF extends Validator{
	private List<String> _dataFileArray;
	private String _baseUrl;
	private String _fileName;
	private String _duration;
	EXTINF(String baseUrl,List<String> dataFileArray,String fileName, String duration){
		_baseUrl = baseUrl;
		_fileName = fileName;
		_dataFileArray = dataFileArray;
		_duration = duration;
	}
	@Override
	public List<ValidationReport> isValid() throws IOException {
		List<ValidationReport> errorMsgs = new ArrayList<ValidationReport>();
		List<Integer> tsFilesPostFixNumbers = new ArrayList<Integer>(); 
		
		for (String dataItem : _dataFileArray) {			
			if(!dataItem.isEmpty() && UtilHelper.match(dataItem,Constants.extInfDurationRegex)){				
				String durationValue = UtilHelper.parseStringAttr(dataItem, Constants.extInfDurationRegex);					
				if(UtilHelper.match(durationValue,Constants.intRegex)){
					errorMsgs.add(checkDurations(_duration,durationValue));		
				}else{
					errorMsgs.add(new ValidationReport(Constants.EXTINF,_fileName,"EXTINF duration number should be an integer or float"));
				}
				
			}			
			// Reading the .ts files string line
			if(!dataItem.isEmpty() && FilenameUtils.getExtension(FilenameUtils.getName(dataItem)).equals("ts")){				
				String fullUri = UtilHelper.isUrl(dataItem) ? dataItem : _baseUrl + dataItem;
				//Url's should be valid and should exist on the server.
				if(!UtilHelper.exists(fullUri)){
					errorMsgs.add(new ValidationReport(Constants.EXTINF,_fileName,dataItem + " file does not exist on the server."));
				}
				//Parsing the Post fix number specified on the ts files 
				String postFixNumber =UtilHelper.parseStringAttr(dataItem,"_(\\d+)");
				if(postFixNumber != null){
					tsFilesPostFixNumbers.add(Integer.parseInt(postFixNumber));
				}
			}
		}
		
		// if the ts files are more than onw...
		// We should check for duplication and
		// Missing files in the sequence		
		if(tsFilesPostFixNumbers.size() > 1){
			// Finding the duplicates
			List<Integer> duplicates = findDuplicates(tsFilesPostFixNumbers);
			for (Integer integer : duplicates) {
				errorMsgs.add(new ValidationReport(Constants.EXTINF,_fileName, FilenameUtils.removeExtension(_fileName)+"_" + integer + ".ts is duplicate."));
			}
			// Finding the Missing files in the sequence.
			List<Integer> missing = missingInSequence(tsFilesPostFixNumbers);
			for (Integer integer : missing) {
				errorMsgs.add(new ValidationReport(Constants.EXTINF,_fileName, FilenameUtils.removeExtension(_fileName)+"_" + integer + ".ts is missing in the sequence."));
			}
		}
		
		return errorMsgs;
	}
	
	
	private ValidationReport checkDurations(String targerDuration, String infDuration){
		
		if(isFloat(targerDuration) && isFloat(infDuration)){
			if(Float.parseFloat(infDuration) > Float.parseFloat(targerDuration)){
				return new ValidationReport(Constants.EXTINF,_fileName,"EXTINF duration value should not be greater then EXT-X-TARGETDURATION tag duration value.");
			}
		}else if (isInteger(targerDuration) && isInteger(infDuration)){
			if(Integer.parseInt(infDuration) > Integer.parseInt(targerDuration)){
				return new ValidationReport(Constants.EXTINF,_fileName,"EXTINF duration value should not be greater then EXT-X-TARGETDURATION tag duration value.");
			}
		}else{
			return new ValidationReport(Constants.EXTINF,_fileName,"EXTINF duration value and the EXT-X-TARGETDURATION tag duration value are of different data types.");
		}
		return null;	
	}
	
	public boolean isFloat(String value){
		String decimalPattern = "([0-9]*)\\.([0-9]*)";  
		return Pattern.matches(decimalPattern, value);
	}
	
	public boolean isInteger(String value){
		String decimalPattern = "([0-9]*)";  
		return Pattern.matches(decimalPattern, value);
	}
	
	
	private List<Integer> findDuplicates(List<Integer> listContainingDuplicates)
	{ 
	  final List<Integer> setToReturn = new ArrayList<Integer>(); 
	  final Set<Integer> set1 = new HashSet<Integer>();

	  for (Integer yourInt : listContainingDuplicates)
	  {
		   if (!set1.add(yourInt)) {
			   setToReturn.add(yourInt);
		   }
	  }
	  return setToReturn;
	}
	
	private List<Integer> missingInSequence(List<Integer> listContainingMissing)
	{ 
		final List<Integer> setToReturn = new ArrayList<Integer>();
		int first = listContainingMissing.get(0); 
		int size = listContainingMissing.size();
		for (int i = 0; i < size; i++)
		{
		    if ((first + i) != listContainingMissing.get(i)) {
		    	setToReturn.add(first + i);		        
		    }
		}
		return setToReturn;
	}

	
}
