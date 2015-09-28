package hls.analyzer;

import java.util.Arrays;
import java.util.List;

public class Constants {

	//Regular expression to validate URL	
	public static final String urlRegex = "((?:ftp|http|https)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])";
	public static final String intRegex = "(^\\d+(?:\\.\\d+)?)";
	public static final String extensionRegex = "(^.*\\.(m3u8|m3u)$)";
		
	// Valid Media tag Values
	public static final List<String> ValidTypes = Arrays.asList("AUDIO","VIDEO","SUBTITLES","CLOSED-CAPTION");
		
	// Tags
	public static final String EXTXMEDIA = "#EXT-X-MEDIA";
	public static final String EXTM3U = "#EXTM3U";
	public static final String EXTXVERSION = "#EXT-X-VERSION";
	public static final String EXTXSTREAMINF = "#EXT-X-STREAM-INF";
	public static final String EXTXTARGETDURATION = "#EXT-X-TARGETDURATION";
	public static final String EXTINF = "#EXTINF";
	public static final String EXTXENDLIST = "#EXT-X-ENDLIST";
	public static final String EXTXKEY = "#EXT-X-KEY";
	public static final String EXTXBYTERANGE = "#EXT-X-BYTERANGE";
	public static final String EXTXIFRAMESONLY = "#EXT-X-I-FRAMES-ONLY";
	public static final String EXTXMAP = "#EXT-X-MAP";
	
	
	
	// Regular Expression to validate tags
	public static final String versionRegex = "^" + EXTXVERSION + ":(\\d+(?:\\.\\d+)?|[A-Z]+)";
	public static final String durationRegex = "^" + EXTXTARGETDURATION + ":(\\d+(?:\\.\\d+)?)";
	public static final String extInfDurationRegex = "^" + EXTINF + ":(\\d+(?:\\.\\d+)?|[A-Z]+)";	
	public static final String mediaRegex = "^" + EXTXMEDIA + ":(.*)";	
	public static final String streamRegex = "^" + EXTXSTREAMINF + ":(.*)";
	public static final String typeRegex = "(TYPE=(AUDIO|VIDEO|SUBTITLES|CLOSED-CAPTION))";
	public static final String uriRegex = "(URI=\""+ urlRegex +"\")";
	public static final String groupRegex = "(GROUP-ID=\"(.*)\")";
	public static final String bandwidthRegex = "(BANDWIDTH=(\\d+$))";
	public static final String ivRegex = "(IV=\"(.*)\")";
	public static final String keyFormatRegex = "(KEYFORMAT=\"(.*)\")";
	public static final String keyFormatVersionsRegex = "(KEYFORMATVERSIONS=\"(.*)\")";
	
	
		
}
