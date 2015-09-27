package hls.analyzer;

import java.util.ArrayList;
import java.util.List;

public class EXTXVERSION extends Validator{
	
	private List<String> _dataFileArray;
	private String _fileName;
	EXTXVERSION(List<String> dataFileArray, String fileName){
		_fileName = fileName;
		_dataFileArray = dataFileArray;
	}
	
	@Override
	public List<ValidationReport> isValid() {
		List<ValidationReport> errorMsgs = new ArrayList<ValidationReport>(); 
		int version = 0;
		//Checking if duplicate Version tags exist
		if(!checkDuplicateVersionTag()){
			errorMsgs.add(new ValidationReport(Constants.EXTXVERSION,_fileName,"Invalid file because multiple EXT-X-VERSION tag detected."));
		}
		if(errorMsgs.size() == 0){
			String dataItem = UtilHelper.dataItemByTag(_dataFileArray,Constants.versionRegex);
			if(dataItem != null){		
				String versionId = UtilHelper.parseStringAttr(dataItem, Constants.versionRegex);
				if(UtilHelper.match(versionId,Constants.intRegex)){
					version =  Integer.parseInt(UtilHelper.parseStringAttr(dataItem, Constants.versionRegex));
				}else{
					errorMsgs.add(new ValidationReport(Constants.EXTXVERSION,_fileName,"Version number should be an integer"));
				}		
			}
		}
		
		if(errorMsgs.size() == 0 && version > 0 && _dataFileArray.size() > 0){			
			String dataItem;
			switch (version) {
			case 2:				
				//A Media Playlist MUST indicate a EXT-X-VERSION of 2 or higher if it contains:
				//The IV attribute of the EXT-X-KEY tag.
				dataItem = UtilHelper.dataItemByTag(_dataFileArray,Constants.EXTXKEY);
				
				break;				
			default:
				break;
			}
		}
		// TODO https://datatracker.ietf.org/doc/draft-pantos-http-live-streaming/?include_text=1
		// TODO version check discussed in the section 7
		return errorMsgs;
	}
	
	private boolean checkDuplicateVersionTag(){
		int count = 0;
		for (String dataItem : _dataFileArray) {
			if(UtilHelper.IsMatching(dataItem,Constants.versionRegex)){
				count++;
			}
		}
		return count == 0;
	}
	
	

}
