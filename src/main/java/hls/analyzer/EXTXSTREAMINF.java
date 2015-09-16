package hls.analyzer;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

public class EXTXSTREAMINF extends Validator{
	private List<String> _dataFileArray;
	private String _baseUrl;
	EXTXSTREAMINF(String baseUrl,List<String> dataFileArray){
		_baseUrl = baseUrl;
		_dataFileArray = dataFileArray;
	}
	@Override
	public List<ValidationReport> isValid() throws IOException {
		List<ValidationReport> errorMsgs = new ArrayList<ValidationReport>(); 
		List<StreamUriContainer> pairList = new ArrayList<StreamUriContainer>();
		for (String dataItem : _dataFileArray){			
			if(dataItem.matches(Constants.streamRegex)){
				int index = _dataFileArray.indexOf(dataItem);
				int uriIndex = index + 1;
				pairList.add(new StreamUriContainer(index,dataItem,_dataFileArray.get(uriIndex)));
			}
		}
		
		for (StreamUriContainer entry : pairList) {
			
			String tagLine = entry.TagLine;			
			//Validating the BANDWIDTH attribute
			String bandWidthStr = UtilHelper.parseStringAttr(tagLine, Constants.bandwidthRegex);
			if(bandWidthStr == null){
				errorMsgs.add(new ValidationReport(entry.LineNumber,Constants.EXTXSTREAMINF,"","BANDWIDTH attribute is required."));
			}else{
				int bandWidth= Integer.parseInt(bandWidthStr);
			}
						
			//-----------------------------------------------------------------
			//TODO More attributes can be verified here in future
			// e.g. AVERAGE-BANDWIDTH, CODECS, RESOLUTION, AUDIO
			// VIDEO, SUBTITLES, CLOSED-CAPTIONS
			//-----------------------------------------------------------------	
			
			
			String fullUri = _baseUrl + entry.Uri;
			if(!fullUri.matches(Constants.extensionRegex)){
				errorMsgs.add(new ValidationReport(entry.LineNumber + 1,Constants.EXTXSTREAMINF,"","Extension of the Uri file is invalid"));
				continue;
			}			
			if(!UtilHelper.exists(fullUri)){
				errorMsgs.add(new ValidationReport(entry.LineNumber + 1,Constants.EXTXSTREAMINF,"","File " + entry.Uri + " mentioned in the menifest file doest not exist on the server."));
			}else{
				FileReaderHandler fh = new FileReaderHandler(fullUri);
		    	Scanner scannedFile;
				scannedFile = fh.getScannerStream();
				if(scannedFile != null){
					
				}
			}
			
		}
		return errorMsgs;
	}
}
