package hls.analyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EXTXTARGETDURATION extends Validator{
	private List<String> _dataFileArray;
	private String _fileName;
	EXTXTARGETDURATION(List<String> dataFileArray,String fileName){
		_fileName = fileName;
		_dataFileArray = dataFileArray;
	}
	@Override
	public List<ValidationReport> isValid() throws IOException {
		List<ValidationReport> errorMsgs = new ArrayList<ValidationReport>(); 
		Float duration = (float) -1;
		for (String dataItem : _dataFileArray) {
			int lineNumber = _dataFileArray.indexOf(dataItem) + 1;
			if(!dataItem.isEmpty() && dataItem.matches(Constants.durationRegex)){
				if(duration < 0){
					String durationValue = UtilHelper.parseStringAttr(dataItem, Constants.durationRegex);
					if(durationValue.matches(Constants.intRegex)){
						duration =  Float.parseFloat(UtilHelper.parseStringAttr(dataItem, Constants.durationRegex));
						errorMsgs.addAll(new EXTINF(_dataFileArray,_fileName, duration).isValid());
					}else{
						errorMsgs.add(new ValidationReport(lineNumber,Constants.EXTXTARGETDURATION,_fileName,"EXT-X-TARGETDURATION duration number should be an integer or float"));
					}
				}		
			}
		}
		
		if(duration < 0){
			errorMsgs.add(new ValidationReport(null,Constants.EXTXTARGETDURATION,_fileName,"EXT-X-TARGETDURATION tag not detected which make this file invalid."));
		}
		
		return errorMsgs;
	}

}
