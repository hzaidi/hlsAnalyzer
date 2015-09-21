package hls.analyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EXTINF extends Validator{
	private List<String> _dataFileArray;
	private String _fileName;
	private Float _duration;
	EXTINF(List<String> dataFileArray,String fileName, Float duration){
		_fileName = fileName;
		_dataFileArray = dataFileArray;
		_duration = duration;
	}
	@Override
	public List<ValidationReport> isValid() throws IOException {
		List<ValidationReport> errorMsgs = new ArrayList<ValidationReport>();
		float duration = -1;
		for (String dataItem : _dataFileArray) {
			int lineNumber = _dataFileArray.indexOf(dataItem);
			if(!dataItem.isEmpty() && UtilHelper.match(dataItem,Constants.extInfDurationRegex)){
				if(duration < 0){
					String durationValue = UtilHelper.parseStringAttr(dataItem, Constants.extInfDurationRegex);					
					if(UtilHelper.match(durationValue,Constants.intRegex)){
						duration =  Float.parseFloat(UtilHelper.parseStringAttr(dataItem, Constants.extInfDurationRegex));
						if(duration > _duration){
							errorMsgs.add(new ValidationReport(lineNumber,Constants.EXTINF,_fileName,"EXTINF duration value should not be greater then EXT-X-TARGETDURATION tag duration value."));
						}
					}else{
						errorMsgs.add(new ValidationReport(lineNumber,Constants.EXTINF,_fileName,"EXTINF duration number should be an integer or float"));
					}
				}
			}	
		}
		return errorMsgs;
	}
}
