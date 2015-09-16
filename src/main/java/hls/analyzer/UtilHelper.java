package hls.analyzer;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilHelper {
	
	public static boolean isUrl(String url){
		return url.matches("^" + Constants.urlRegex);
	}
	

	public static String parseStringAttr(String line, String regex){
		Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(line);
		if(matcher.find()){
		 int count = matcher.groupCount();
		 return matcher.group(count);			    
		}
		return null;
	}
		
	public static boolean exists(String URLName){
	    try {
	      HttpURLConnection.setFollowRedirects(false);
	      // note : you may also need
	      //        HttpURLConnection.setInstanceFollowRedirects(false)
	      HttpURLConnection con =
	         (HttpURLConnection) new URL(URLName).openConnection();
	      con.setRequestMethod("HEAD");
	      return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
	    }
	    catch (Exception e) {	      
	       return false;
	    }
	  }  
}
