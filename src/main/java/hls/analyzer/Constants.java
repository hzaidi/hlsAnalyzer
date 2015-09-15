package hls.analyzer;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Constants {

	//Regular expression to validate URL	
	public static final String urlRegex = "((?:ftp|http|https)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])";
		
	// Valid Media tag Values
	public static final List<String> ValidTypes = Arrays.asList("AUDIO","VIDEO","SUBTITLES","CLOSED-CAPTIONS");
		
	// Tags
	public static final String EXTXMEDIA = "#EXT-X-MEDIA";
	public static final String EXTM3U = "#EXTM3U";
	public static final String EXTXVERSION = "#EXT-X-VERSION";
	public static final String EXTXSTREAMINF = "#EXT-X-STREAM-INF";
	
	//Regular Expression to validate tags
	public static final String versionRegex = "^" + EXTXVERSION + ":(.*)";
	public static final String mediaRegex = "^" + EXTXMEDIA + ":(.*)";	
	public static final String streamRegex = "^" + EXTXSTREAMINF + ":(.*)";
	public static final String typeRegex = "(TYPE=(AUDIO|VIDEO|SUBTITLES|CLOSED-CAPTION))";
	public static final String uriRegex = "(URI=\""+ urlRegex +"\")";
	public static final String groupRegex = "(GROUP-ID=\"(.*)\")";
	
	
		
}
