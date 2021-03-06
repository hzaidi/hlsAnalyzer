package hls.analyzer;

import java.util.ArrayList;
import java.util.List;

public class EXTXMEDIA  extends Validator{
	private List<String> _dataFileArray;
	private String _fileName;
	EXTXMEDIA(List<String> dataFileArray,String fileName){
		_fileName = fileName;
		_dataFileArray = dataFileArray;
	}
	@Override
	public List<ValidationReport> isValid() {
		List<ValidationReport> errorMsgs = new ArrayList<ValidationReport>(); 
		String dataItem = UtilHelper.dataItemByTag(_dataFileArray,Constants.mediaRegex);
		if(dataItem != null){
			String type = UtilHelper.parseStringAttr(dataItem,Constants.typeRegex);
			//Validating the Media type attribute
			//Media type attribute should be (AUDIO,VEDIO,SUBTITLES or CLOSED-CAPTION)
			if(type == null || !Constants.ValidTypes.contains(type)){
				errorMsgs.add(new ValidationReport(Constants.EXTXMEDIA,_fileName,"Invalid media type attribute is detected."));
			}
			//Validating the URI attribute
			//if the Media type attribute is "CLOSED-CAPTION" URI Attribute should not be present.
			String uri = UtilHelper.parseStringAttr(dataItem, Constants.uriRegex);
			if(uri != null && type != null && type.equals("CLOSED-CAPTION") && !uri.isEmpty()){
				errorMsgs.add(new ValidationReport(Constants.EXTXMEDIA,_fileName,"If the media type is 'CLOSED-CAPTION' URI attribute should not be present."));
			}else{
				if(uri != null && !UtilHelper.isUrl(uri)){
					errorMsgs.add(new ValidationReport(Constants.EXTXMEDIA,_fileName,"Uri path is invalid."));
				}
			}
			String groupId = UtilHelper.parseStringAttr(dataItem, Constants.groupRegex);
			if(groupId == null){
				errorMsgs.add(new ValidationReport(Constants.EXTXMEDIA,_fileName,"Group id is required and its missing from the menifest file."));
			}
			
			//-----------------------------------------------------------------
			//TODO More attributes can be verified here in future
			// e.g. Language, Accoc-Language, Name, Default, Autoselect, Forced
			// instream-id, characterstics.
			//-----------------------------------------------------------------		
		}
		return errorMsgs;
	}
}
