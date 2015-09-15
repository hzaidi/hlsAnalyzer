package hls.analyzer;

public class ValidationReport {
	public int LineNumber;
	public String ErrorTag;
	public String FileName;
	public String Detail;
	
	ValidationReport(int lineNumber, String errorTag, String fileName, String detail){
		LineNumber = lineNumber;
		ErrorTag = errorTag;
		FileName = fileName;
		Detail = detail;
	}
	
}
