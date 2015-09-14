package hls.analyzer;

import java.util.List;

public interface IBasicTagValidator{
	public void checkEXTM3U(List<String> dataFileArray);
	public void checkEXTXVERSION(List<String> dataFileArray);
}
