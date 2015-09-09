package hls.analyzer;
import java.io.*;
import java.util.logging.*;

public class LogWriter {
	String _fileName = "";
	String _filePostFix = ".log";
	protected static final Logger logger=Logger.getLogger("MYLOG");
	FileHandler file = null; 

	LogWriter(String fileName)  throws IOException{
		_fileName = fileName;
        try {
        	if(file == null){
	            file= new FileHandler("C:\\Users\\hzaidi\\M3U8ParserLogs\\" + _fileName + _filePostFix);        	
	            logger.addHandler(file);
	            SimpleFormatter formatter = new SimpleFormatter();  
	            file.setFormatter(formatter);  
	            logger.setUseParentHandlers(false);
        	}
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public static void log(String logEntry){
		logger.info(logEntry);
	}
	
}
