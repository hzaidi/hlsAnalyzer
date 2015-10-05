package hls.analyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EXTXSTREAMINF extends Validator{
	private List<String> _dataFileArray;
	private String _baseUrl;
	private String _fileName;
	EXTXSTREAMINF(String baseUrl,List<String> dataFileArray, String fileName){
		_fileName = fileName;
		_baseUrl = baseUrl;
		_dataFileArray = dataFileArray;
	}
	@Override
	public List<ValidationReport> isValid() throws IOException {
		List<ValidationReport> errorMsgs = new ArrayList<ValidationReport>(); 
		List<StreamUriContainer> pairList = new ArrayList<StreamUriContainer>();
		for (String dataItem : _dataFileArray){			
			if(UtilHelper.match(dataItem,Constants.streamRegex)){
				int index = _dataFileArray.indexOf(dataItem);
				int uriIndex = index + 1;
				pairList.add(new StreamUriContainer(dataItem,_dataFileArray.get(uriIndex)));
			}
		}
		
		for (StreamUriContainer entry : pairList) {
			
			String tagLine = entry.TagLine;			
			//Validating the BANDWIDTH attribute
			String bandWidthStr = UtilHelper.parseStringAttr(tagLine, Constants.bandwidthRegex);
			if(bandWidthStr == null){
				errorMsgs.add(new ValidationReport(Constants.EXTXSTREAMINF,_fileName,"BANDWIDTH attribute is required."));
			}else{
				int bandWidth= Integer.parseInt(bandWidthStr);
			}
						
			//-----------------------------------------------------------------
			//TODO More attributes can be verified here in future
			// e.g. AVERAGE-BANDWIDTH, CODECS, RESOLUTION, AUDIO
			// VIDEO, SUBTITLES, CLOSED-CAPTIONS
			//-----------------------------------------------------------------	
			
			String fileName = entry.Uri;
			String fullUri = _baseUrl + fileName;
			if(!UtilHelper.match(fullUri,Constants.extensionRegex)){
				errorMsgs.add(new ValidationReport(Constants.EXTXSTREAMINF,_fileName,"Extension of the Uri file is invalid"));
				continue;
			}		
			System.out.println("Reteriving "+ entry.Uri +" file.");
			if(!UtilHelper.exists(fullUri)){
				System.out.println("---------------> Unable to reterive "+ entry.Uri +" file.");
				errorMsgs.add(new ValidationReport(Constants.EXTXSTREAMINF,_fileName,"File " + entry.Uri + " mentioned in the menifest file doest not exist on the server."));
			}else{
				System.out.println("---------------> Starting analyzing "+ entry.Uri +" file.");
				FileReaderHandler fh = new FileReaderHandler(fullUri);
				List<String> subdataFileArray = new ArrayList<String>();
				subdataFileArray = fh.getFileAsArray();
				if(subdataFileArray != null){		    
					MediaPlaylist mediaPlaylist = new MediaPlaylist(_baseUrl,fileName,subdataFileArray);
					System.out.println("---------------> Analyzing "+ entry.Uri +" file.");
					errorMsgs.addAll(mediaPlaylist.parse());
					System.out.println("---------------> Finish analyzing "+ entry.Uri +" file.");
				}
			}
			
		}
		return errorMsgs;
	}
}
