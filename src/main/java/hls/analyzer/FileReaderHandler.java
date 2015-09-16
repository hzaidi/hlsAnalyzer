package hls.analyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReaderHandler implements IFileReaderHandler {
	
	String _filePath;
			
	FileReaderHandler (String filePath){
		_filePath = filePath;
	}
	
	public List<String> getFileAsArray()  throws IOException{
		List<String> dataFileArray = new ArrayList<String>();
		Scanner scannedFile = getScannerStream();
		if(scannedFile == null){
			return null;
		}
		while(scannedFile.hasNextLine()) {
			 String line = scannedFile.nextLine();
			 dataFileArray.add(line.trim());
       }
		return dataFileArray;
	}
		
	public Scanner getScannerStream() throws IOException{		
		if(verifyFileExtension()){			
	    	Scanner br = null;
	    	if(UtilHelper.isUrl(_filePath)){
	    		if(UtilHelper.exists(_filePath)){
		    		URL url = new URL(_filePath);    		
		    		br = new Scanner(url.openStream());
	    		}else{
	    			return null;
	    		}
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
		if(_filePath.matches(Constants.extensionRegex)){
			return true;
    	}   
		return false;
	} 
}
