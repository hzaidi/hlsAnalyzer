package hls.analyzer;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MasterTagValidator implements IMasterTagValidator{

	
	
	public void checkEXTXMEDIA(List<String> dataFileArray) {
		for (String dataItem : dataFileArray) {
			if(dataItem.matches(Constants.tagRegex)){
				String type = parseStringAttr(dataItem,Constants.typeRegex);
				//Validating the Media type attribute
				//Media type attribute should be (AUDIO,VEDIO,SUBTITLES or CLOSED-CAPTION)
				if(type == null || !Constants.ValidTypes.contains(type)){
					LogWriter.log("Invalid media type attribute is detected.");
				}
				//Validating the URI attribute
				//if the Media type attribute is "CLOSED-CAPTION" URI Attribute should not be present.
				String uri = parseStringAttr(dataItem, Constants.uriRegex);
				if(uri != null && type != null && type.equals("CLOSED-CAPTION") && !uri.isEmpty()){
					LogWriter.log("If the media type is 'CLOSED-CAPTION' URI attribute should not be present.");
				}else{
					if(uri != null && !UtilHelper.isUrl(uri)){
						LogWriter.log("Uri path is invalid.");
					}
				}
				String groupId = parseStringAttr(dataItem, Constants.groupRegex);
				if(groupId == null){
					LogWriter.log("Group id is required and its missing from the menifest file.");
				}
						
			}
		}
				
	}
	
	
	
	
	
	
	private String parseStringAttr(String line, String regex){
		Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(line);
		if(matcher.find()){
		 int count = matcher.groupCount();
		 return matcher.group(count);			    
		}
		return null;
	}

}
