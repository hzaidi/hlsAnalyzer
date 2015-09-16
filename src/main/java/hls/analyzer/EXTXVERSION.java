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
		int version = -1;
		for (String dataItem : _dataFileArray) {
			int lineNumber = _dataFileArray.indexOf(dataItem);
			if(!dataItem.isEmpty() && dataItem.matches(Constants.versionRegex)){
				if(version < 0){
					String versionId = UtilHelper.parseStringAttr(dataItem, Constants.versionRegex);
					if(versionId.matches(Constants.intRegex)){
						version =  Integer.parseInt(UtilHelper.parseStringAttr(dataItem, Constants.versionRegex));
					}else{
						errorMsgs.add(new ValidationReport(lineNumber,Constants.EXTXVERSION,_fileName,"Version number should be an integer"));
					}
				}else{
					errorMsgs.add(new ValidationReport(lineNumber,Constants.EXTXVERSION,_fileName,"Invalid file because multiple EXT-X-VERSION tag detected."));
					break;
				}				
			}
		}
		// TODO https://datatracker.ietf.org/doc/draft-pantos-http-live-streaming/?include_text=1
		// TODO version check discussed in the section 7
		return errorMsgs;
	}

}
