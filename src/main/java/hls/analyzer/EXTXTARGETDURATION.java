package hls.analyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EXTXTARGETDURATION extends Validator{
	private List<String> _dataFileArray;
	private String _baseUrl;
	private String _fileName;
	EXTXTARGETDURATION(String baseUrl,List<String> dataFileArray,String fileName){
		_baseUrl = baseUrl;
		_fileName = fileName;
		_dataFileArray = dataFileArray;
	}
	@Override
	public List<ValidationReport> isValid() throws IOException {
		List<ValidationReport> errorMsgs = new ArrayList<ValidationReport>(); 
		
		String dataItem = UtilHelper.dataItemByTag(_dataFileArray,Constants.durationRegex);
		if(dataItem == null){
			errorMsgs.add(new ValidationReport(Constants.EXTXTARGETDURATION,_fileName,"EXT-X-TARGETDURATION tag not detected which make this file invalid."));
		}else{
			String durationValue = UtilHelper.parseStringAttr(dataItem, Constants.durationRegex);
			if(durationValue.matches(Constants.intRegex)){
				String duration =  UtilHelper.parseStringAttr(dataItem, Constants.durationRegex);
				errorMsgs.addAll(new EXTINF(_baseUrl,_dataFileArray,_fileName, duration).isValid());
			}else{
				errorMsgs.add(new ValidationReport(Constants.EXTXTARGETDURATION,_fileName,"EXT-X-TARGETDURATION duration number should be an integer or float"));
			}					
		}
		
		return errorMsgs;
	}

}
