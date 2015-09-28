package hls.analyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;

public class EXTINF extends Validator{
	private List<String> _dataFileArray;
	private String _baseUrl;
	private String _fileName;
	private Float _duration;
	EXTINF(String baseUrl,List<String> dataFileArray,String fileName, Float duration){
		_baseUrl = baseUrl;
		_fileName = fileName;
		_dataFileArray = dataFileArray;
		_duration = duration;
	}
	@Override
	public List<ValidationReport> isValid() throws IOException {
		List<ValidationReport> errorMsgs = new ArrayList<ValidationReport>();
		List<Integer> tsFilesPostFixNumbers = new ArrayList<Integer>(); 
		float duration = -1;
		for (String dataItem : _dataFileArray) {			
			if(!dataItem.isEmpty() && UtilHelper.match(dataItem,Constants.extInfDurationRegex)){				
				String durationValue = UtilHelper.parseStringAttr(dataItem, Constants.extInfDurationRegex);					
				if(UtilHelper.match(durationValue,Constants.intRegex)){
					duration =  Float.parseFloat(UtilHelper.parseStringAttr(dataItem, Constants.extInfDurationRegex));
					if(duration > _duration){
						errorMsgs.add(new ValidationReport(Constants.EXTINF,_fileName,"EXTINF duration value should not be greater then EXT-X-TARGETDURATION tag duration value."));
					}
				}else{
					errorMsgs.add(new ValidationReport(Constants.EXTINF,_fileName,"EXTINF duration number should be an integer or float"));
				}
				
			}			
			if(!dataItem.isEmpty() && FilenameUtils.getExtension(FilenameUtils.getName(dataItem)).equals("ts")){
				String fullUri = UtilHelper.isUrl(dataItem) ? dataItem : _baseUrl + dataItem;
				if(!UtilHelper.exists(fullUri)){
					errorMsgs.add(new ValidationReport(Constants.EXTINF,_fileName,dataItem + " file does not exist on the server."));
				}
				String postFixNumber =UtilHelper.parseStringAttr(dataItem,"_(\\d+)");
				tsFilesPostFixNumbers.add(Integer.parseInt(postFixNumber));				
			}
		}
		
		if(tsFilesPostFixNumbers.size() > 1){	
			List<Integer> duplicates = findDuplicates(tsFilesPostFixNumbers);
			for (Integer integer : duplicates) {
				errorMsgs.add(new ValidationReport(Constants.EXTINF,_fileName, FilenameUtils.removeExtension(_fileName)+"_" + integer + ".ts is duplicate."));
			}
			List<Integer> missing = missingInSequence(tsFilesPostFixNumbers);
			for (Integer integer : missing) {
				errorMsgs.add(new ValidationReport(Constants.EXTINF,_fileName, FilenameUtils.removeExtension(_fileName)+"_" + integer + ".ts is missing in the sequence."));
			}
		}
		
		return errorMsgs;
	}
	
	public List<Integer> findDuplicates(List<Integer> listContainingDuplicates)
	{ 
	  final List<Integer> setToReturn = new ArrayList<Integer>(); 
	  final Set<Integer> set1 = new HashSet();

	  for (Integer yourInt : listContainingDuplicates)
	  {
	   if (!set1.add(yourInt))
	   {
	    setToReturn.add(yourInt);
	   }
	  }
	  return setToReturn;
	}
	
	public List<Integer> missingInSequence(List<Integer> listContainingMissing)
	{ 
		final List<Integer> setToReturn = new ArrayList<Integer>();
		int first = listContainingMissing.get(0); // sets the first value in seq. to var
		int size = listContainingMissing.size(); // sets the seq size to var
		for (int i = 0; i < size; i++)
		{
		    if ((first + i) != listContainingMissing.get(i)) {
		    	setToReturn.add(first + i);		        
		    }
		}
		return setToReturn;
	}

	
}
