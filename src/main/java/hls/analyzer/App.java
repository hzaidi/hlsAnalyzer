package hls.analyzer;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class App 
{
    public static void main( String[] args ) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {    	    	
//    	System.out.println("data item matches:" + "#EXTINF:6, no desc".matches("^#EXTINF:(\\d+),.*"));
//    	
//    	System.out.println("data item matches:" + "#EXTINF:6".matches("^#EXTINF:(\\d+)(?,.*)"));
//    	
    	System.out.println("Please enter the Url or the Physical Location of file" );    	
    	Scanner inputReader = new Scanner(System.in);
    	List<String> dataFileArray = new ArrayList<String>();
    	List<ValidationReport> reports = new ArrayList<ValidationReport>();
    	final String filePath = inputReader.nextLine();
    	
    	
    	
    	inputReader.close();    	    	
    	FileReaderHandler fh = new FileReaderHandler(filePath);    	
		String baseUrl = fh.baseUrl();
		String fileName = fh.fileName();
		
		dataFileArray = fh.getFileAsArray();
		if(dataFileArray != null){	
			MasterPlaylist masterPlayList = new MasterPlaylist(fileName, baseUrl, dataFileArray);
			reports.addAll(masterPlayList.parse());
			
			for (ValidationReport report : reports) {
				System.out.println("-----------------------------------------------");
				System.out.println("Line:		" + report.LineNumber);
				System.out.println("Tag:		" + report.ErrorTag);
				System.out.println("File:		" + report.FileName );
				System.out.println("Details:	" + report.Detail);
				System.out.println("-----------------------------------------------");				
			}
			
		}else{
			System.out.println("Unable to read content from the file specified.");
		}
	
    }
}
