package hls.analyzer;

public class ValidationReport {
	public Integer LineNumber;
	public String ErrorTag;
	public String FileName;
	public String Detail;
	
	ValidationReport(Integer lineNumber, String errorTag, String fileName, String detail){
		LineNumber = lineNumber;
		ErrorTag = errorTag;
		FileName = fileName;
		Detail = detail;
	}
	
}
