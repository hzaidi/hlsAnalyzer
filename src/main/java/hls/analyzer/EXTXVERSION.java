package hls.analyzer;

import java.util.ArrayList;
import java.util.List;

public class EXTXVERSION extends Validator{
	
	private List<String> _dataFileArray;
	EXTXVERSION(List<String> dataFileArray){
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
					version =  Integer.parseInt(UtilHelper.parseStringAttr(dataItem, Constants.versionRegex));
				}else{
					errorMsgs.add(new ValidationReport(lineNumber,Constants.versionRegex,"","Invalid file because multiple EXT-X-VERSION tag detected."));
					break;
				}				
			}
		}
		// TODO https://datatracker.ietf.org/doc/draft-pantos-http-live-streaming/?include_text=1
		// TODO version check discussed in the section 7
		return errorMsgs;
	}

}
