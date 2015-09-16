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
    	System.out.println("Please enter the Url or the Physical Location of file" );    	
    	Scanner inputReader = new Scanner(System.in);
    	List<String> dataFileArray = new ArrayList<String>();
    	List<ValidationReport> reports = new ArrayList<ValidationReport>();
    	final String filePath = inputReader.nextLine();
    	inputReader.close();    	    	
    	FileReaderHandler fh = new FileReaderHandler(filePath);    	
		String baseUrl = fh.baseUrl();
		if(!baseUrl.isEmpty()){
			System.out.println("Base Url:" + fh.baseUrl());
		}
		dataFileArray = fh.getFileAsArray();
		if(dataFileArray != null){		    
			List<Validator> validations = new ArrayList<Validator>();
			validations.add(new EXTM3U(dataFileArray));
			validations.add(new EXTXVERSION(dataFileArray));
			validations.add(new EXTXMEDIA(dataFileArray));
			validations.add(new EXTXSTREAMINF(baseUrl,dataFileArray));
			
			for (Validator validator : validations) {
				reports.addAll(validator.isValid());
			}
			
			for (ValidationReport report : reports) {
				System.out.println("Line#" + report.LineNumber + "-Tag:" + report.ErrorTag + "-File:" + report.FileName +"-Details:" + report.Detail);
			}
			
		}else{
			System.out.println("Unable to read content from the file specified.");
		}
	
    }
}
