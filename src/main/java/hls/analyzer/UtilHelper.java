package hls.analyzer;

public class UtilHelper {
	
	public static boolean isUrl(String url){
		return url.matches("^" + Constants.urlRegex);
	}
		
}
