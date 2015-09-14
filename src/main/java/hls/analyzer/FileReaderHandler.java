package hls.analyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class FileReaderHandler implements IFileReaderHandler {
	
	String _filePath;
			
	FileReaderHandler (String filePath){
		_filePath = filePath;
	}
		
	public Scanner getScannerStream() throws IOException{		
		if(verifyFileExtension()){			
	    	Scanner br = null;
	    	if(UtilHelper.isUrl(_filePath)){
	    		URL url = new URL(_filePath);    		
	    		br = new Scanner(url.openStream());    		
	    	}else{
	    		br = new Scanner(new BufferedReader(new FileReader(_filePath)));
	    	}
	    	return br;
		}
		return null;
	}

	public String baseUrl(){		
		if(UtilHelper.isUrl(_filePath)){
			int i = _filePath.lastIndexOf('/');
			return _filePath.substring(0, i + 1);
		}
		return "";
		
	}	
	
		private boolean verifyFileExtension() throws IOException{
		String extension = "";
    	int i = _filePath.lastIndexOf('.');
    	if (i > 0) {
    		extension = _filePath.substring(i+1);
    	}
    	if(extension.equals("m3u8") || extension.equals("m3u")){
    		return true;
    	}    	
    	LogWriter.log("Invalid file Extension to read.");    	
    	return false;
	} 
}
