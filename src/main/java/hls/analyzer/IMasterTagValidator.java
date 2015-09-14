package hls.analyzer;

import java.util.List;

public interface IMasterTagValidator {
	public void checkEXTXMEDIA(List<String> dataFileArray);	
	public void checkEXTXSTREAMINF(List<String> dataFileArray);
}
