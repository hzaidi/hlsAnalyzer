package hls.analyzer;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {    	    	
    	System.out.println("Please enter the Url or the Physical Location of file" );    	
    	Scanner inputReader = new Scanner(System.in);
    	List<String> dataFileArray = new ArrayList<String>();
    	String baseUrl = "";
    	final String filePath = inputReader.nextLine();
    	inputReader.close();    	    	
    	FileReaderHandler fh = new FileReaderHandler(filePath);
    	new LogWriter(new File(filePath).getName());
    	Scanner scannedFile;
		scannedFile = fh.getScannerStream();
		baseUrl = fh.baseUrl();
		if(!baseUrl.isEmpty()){
			System.out.println("Base Url:" + fh.baseUrl());
		}
		if(scannedFile != null){		    
			while(scannedFile.hasNextLine()) {
				 String line = scannedFile.nextLine();
				 dataFileArray.add(line.trim());
	        }			
			BasicValidator basicValidator =new BasicValidator(dataFileArray);
			basicValidator.doValidation();
			MasterPlaylistValidator masterValidator = new MasterPlaylistValidator(dataFileArray);
			masterValidator.doValidation();			
		}	
    }
}
