package hls.analyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EXTXENDLIST extends Validator{
	private List<String> _dataFileArray;
	private String _fileName;
	EXTXENDLIST(List<String> dataFileArray,String fileName){
		_fileName = fileName;
		_dataFileArray = dataFileArray;
	}
	@Override
	public List<ValidationReport> isValid() throws IOException {
		List<ValidationReport> errorMsgs = new ArrayList<ValidationReport>(); 
		String tag = _dataFileArray.get(_dataFileArray.size() - 1); // Checking the last tag to be EXT-X-ENDLIST	
		if(!tag.isEmpty() && !tag.equals(Constants.EXTXENDLIST)){
			errorMsgs.add(new ValidationReport(Constants.EXTXENDLIST, _fileName, "Last line of the file is not "+ Constants.EXTXENDLIST +" tag"));
		}		
		return errorMsgs;
	}
}
