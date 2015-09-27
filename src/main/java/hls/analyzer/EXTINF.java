package hls.analyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
			}
		}
		return errorMsgs;
	}
}
