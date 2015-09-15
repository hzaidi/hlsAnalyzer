package hls.analyzer;

import java.util.ArrayList;
import java.util.List;

public class EXTM3U extends Validator{
	
	private List<String> _dataFileArray;
	EXTM3U(List<String> dataFileArray){
		_dataFileArray = dataFileArray;
	}
	@Override
	public List<ValidationReport> isValid() {
		List<ValidationReport> errorMsgs = new ArrayList<ValidationReport>(); 
		String tag = _dataFileArray.get(0); // Checking the first tag to be EXTM3U		
		if(!tag.equals(Constants.EXTM3U)){
			errorMsgs.add(new ValidationReport(0, Constants.EXTM3U, "", "First line of the file is not "+ Constants.EXTM3U +" tag"));
		}		
		return errorMsgs;
	}

}
