package hls.analyzer;

public class ValidationReport {	
	public String ErrorTag;
	public String FileName;
	public String Detail;
	
	ValidationReport(String errorTag, String fileName, String detail){		
		ErrorTag = errorTag;
		FileName = fileName;
		Detail = detail;
	}
	
}
