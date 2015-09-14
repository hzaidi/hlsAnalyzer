package hls.analyzer;

import java.util.List;

public class BasicTagValidator implements IBasicTagValidator {

	
	
	public void checkEXTM3U(List<String> dataFileArray) {
		String tag = dataFileArray.get(0); // Checking the first tag to be EXTM3U		
		if(!tag.equals(Constants.EXTM3U)){
			LogWriter.log("First line of the file is not #EXTM3U tag");
		}
	}

	public void checkEXTXVERSION(List<String> dataFileArray) {
		int version = -1;
		for (String dataItem : dataFileArray) {
			if(!dataItem.isEmpty() && dataItem.startsWith(Constants.EXTXVERSION)){
				if(version < 0){
					version = Integer.parseInt(dataItem.split(":")[1]);
				}else{
					LogWriter.log("Invalid file because multiple EXT-X-VERSION tag detected.");
					break;
				}				
			}
		}
		// TODO https://datatracker.ietf.org/doc/draft-pantos-http-live-streaming/?include_text=1
		// TODO version check discussed in the section 7
	
	}

}
