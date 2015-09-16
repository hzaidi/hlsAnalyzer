package hls.analyzer;

public class StreamUriContainer {
	public int LineNumber;
	public String TagLine;
	public String Uri;
	
	StreamUriContainer(int lineNumber, String tagLine, String uri){
		LineNumber = lineNumber;
		TagLine = tagLine;
		Uri= uri;
	}
	
}
