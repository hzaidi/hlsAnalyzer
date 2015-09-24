package hls.analyzer;

import java.util.ArrayList;
import java.util.List;

public class EXTM3U extends Validator{
	
	private List<String> _dataFileArray;
	private String _fileName;
	EXTM3U(List<String> dataFileArray, String fileName){
		_fileName = fileName;
		_dataFileArray = dataFileArray;
	}
	@Override
	public List<ValidationReport> isValid() {
		List<ValidationReport> errorMsgs = new ArrayList<ValidationReport>(); 
		String tag = _dataFileArray.get(0); // Checking the first tag to be EXTM3U		
		if(!tag.equals(Constants.EXTM3U)){
			errorMsgs.add(new ValidationReport(Constants.EXTM3U, _fileName, "First line of the file is not "+ Constants.EXTM3U +" tag"));
		}		
		return errorMsgs;
	}

}
