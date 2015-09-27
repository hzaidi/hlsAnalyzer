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
			}else{
				errorMsgs.add(new ValidationReport(Constants.EXTXVERSION,_fileName,"Unable to detect the version tag."));
			}
		}
		
		if(errorMsgs.size() == 0 && version > 0 && _dataFileArray.size() > 0){
			
			validateForVersion2(errorMsgs, version);
			
			validateForVersion3(errorMsgs, version);
			
			validateForVersion4(errorMsgs, version);			
			
			validateForVersion5(errorMsgs, version);
			
			
		}
		// TODO https://datatracker.ietf.org/doc/draft-pantos-http-live-streaming/?include_text=1
		// TODO version check discussed in the section 7
		return errorMsgs;
	}

	private void validateForVersion5(List<ValidationReport> errorMsgs,
			int version) {
		//A Media Playlist MUST indicate a EXT-X-VERSION of 5 or higher if it contains:
		//The KEYFORMAT and KEYFORMATVERSIONS attributes of the EXT-X-KEY tag.
		//The EXT-X-MAP tag.
		String dataItemKeyV5 = UtilHelper.dataItemByTag(_dataFileArray,Constants.EXTXKEY);
		String keyFormat = (dataItemKeyV5 == null) ? null : UtilHelper.parseStringAttr(dataItemKeyV5,Constants.keyFormatRegex);
		if(keyFormat != null && version < 5){
			errorMsgs.add(new ValidationReport(Constants.EXTXVERSION,_fileName,"Version number is "+ version +" and detect KEYFORMAT attribute on EXTXKEY."));
		}			
		String keyFormatVersions = (dataItemKeyV5 == null) ? null : UtilHelper.parseStringAttr(dataItemKeyV5,Constants.keyFormatVersionsRegex);
		if(keyFormatVersions != null && version < 5){
			errorMsgs.add(new ValidationReport(Constants.EXTXVERSION,_fileName,"Version number is "+ version +" and detect KEYFORMATVERSIONS attribute on EXTXKEY."));
		}
		String dataItemMapV5 = UtilHelper.dataItemByTag(_dataFileArray,Constants.EXTXMAP);
		if(dataItemMapV5 != null && version < 5){
			errorMsgs.add(new ValidationReport(Constants.EXTXVERSION,_fileName,"Version number is "+ version +" and detect KEYFORMATVERSIONS attribute on EXTXKEY."));
		}
	}

	private void validateForVersion4(List<ValidationReport> errorMsgs,
			int version) {
		//A Media Playlist MUST indicate a EXT-X-VERSION of 4 or higher if it contains:
		//The EXT-X-BYTERANGE tag.
		//The EXT-X-I-FRAMES-ONLY tag.
		String dataItemByteRangeV4 = UtilHelper.dataItemByTag(_dataFileArray,Constants.EXTXBYTERANGE);
		if(dataItemByteRangeV4 != null && version < 4){
			errorMsgs.add(new ValidationReport(Constants.EXTXVERSION,_fileName,"Version number is "+ version +" and EXT-X-BYTERANGE tag is detected."));
		}
		String dataItemIFramesOnlyV4 = UtilHelper.dataItemByTag(_dataFileArray,Constants.EXTXIFRAMESONLY);
		if(dataItemIFramesOnlyV4 != null && version < 4){
			errorMsgs.add(new ValidationReport(Constants.EXTXVERSION,_fileName,"Version number is "+ version +" and EXT-X-I-FRAMES-ONLY tag is detected."));
		}
	}

	private void validateForVersion3(List<ValidationReport> errorMsgs,
			int version) {
		//A Media Playlist MUST indicate a EXT-X-VERSION of 3 or higher if it contains:
		//Floating-point EXTINF duration values.
		for (String dataItemV3 : _dataFileArray) {
			boolean isFloat = false;
			if(!dataItemV3.isEmpty() && UtilHelper.match(dataItemV3,Constants.extInfDurationRegex)){	
				String durationValue = UtilHelper.parseStringAttr(dataItemV3, Constants.extInfDurationRegex);					
				if(UtilHelper.match(durationValue,Constants.intRegex)){
					float duration =  Float.parseFloat(UtilHelper.parseStringAttr(dataItemV3, Constants.extInfDurationRegex));
					if(duration != Math.round(duration) && version < 3){
						errorMsgs.add(new ValidationReport(Constants.EXTXVERSION,_fileName,"Version number is "+ version +" and EXTINF duration values are in floating-point values."));
					}
				}
			}
		}
	}

	private void validateForVersion2(List<ValidationReport> errorMsgs,
			int version) {
		//A Media Playlist MUST indicate a EXT-X-VERSION of 2 or higher if it contains:
		//The IV attribute of the EXT-X-KEY tag.
		String dataItemV2 = UtilHelper.dataItemByTag(_dataFileArray,Constants.EXTXKEY);
		String iv = (dataItemV2 == null) ? null : UtilHelper.parseStringAttr(dataItemV2,Constants.ivRegex);
		if(iv == null && version >= 2){
			errorMsgs.add(new ValidationReport(Constants.EXTXVERSION,_fileName,"Version number is "+ version +" and unable to detect the IV attribute on EXTXKEY."));
		}
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
