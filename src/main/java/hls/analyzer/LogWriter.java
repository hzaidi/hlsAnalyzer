package hls.analyzer;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class LogWriter {
	
	//private static final String _filePath = "C:/Users/hzaidi/M3U8ParserLogs/";	
	//private static final String _filePath = "V:/M3U8ParserLogs";
	private static String _fileName = "";
	private static final String _filePostFix = "log";
	private List<ValidationReport> _errorMessages;
	
	LogWriter(String fileName,List<ValidationReport> errorMessages)  throws IOException{
		_fileName = fileName;      
		_errorMessages = errorMessages;
	}
	public void Createlog(){
		if(_errorMessages.size() > 0){
			System.out.println("In the process of generating the log file.");
			Workbook workbook = new XSSFWorkbook();
	        Sheet studentsSheet = workbook.createSheet(FilenameUtils.removeExtension(_fileName) + _filePostFix);	       
	        int rowIndex = 0;
	        Row row = studentsSheet.createRow(rowIndex++);
	        int cellIndex = 0;
            row.createCell(cellIndex++).setCellValue("Line Number");
            row.createCell(cellIndex++).setCellValue("Tag");
            row.createCell(cellIndex++).setCellValue("File");
            row.createCell(cellIndex++).setCellValue("Details");
	        for(ValidationReport errorMessage : _errorMessages){
	        	if(errorMessage != null){
		        	row = studentsSheet.createRow(rowIndex++);
		        	
		            cellIndex = 0;            
		            
		            row.createCell(cellIndex++).setCellValue(rowIndex - 1);
	
		            row.createCell(cellIndex++).setCellValue(errorMessage.ErrorTag);
	
		            row.createCell(cellIndex++).setCellValue(errorMessage.FileName);
	
		            row.createCell(cellIndex++).setCellValue(errorMessage.Detail);
	        	}

	        }
	        try {
	        	String path = System.getProperty("user.dir");	        	
	        	String fullFilePathAndName = path + "/" + FilenameUtils.removeExtension(_fileName) + ".xlsx";
	            FileOutputStream fos = new FileOutputStream(fullFilePathAndName);
	            workbook.write(fos);
	            fos.close();	            
	            System.out.println(FilenameUtils.removeExtension(_fileName) + ".xlsx log file has been generated.");
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }


		}else{
			System.out.println("No errors found.");
		}
	}
	
}
