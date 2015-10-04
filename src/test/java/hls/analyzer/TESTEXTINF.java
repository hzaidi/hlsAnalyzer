package hls.analyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class TESTEXTINF extends TestCase {
		protected EXTINF _tag;
		protected String _fileName;
		protected String _baseUrl;
		protected String _duration;
	   
	   // test method check if the duration is less than duration specified on the INF duration 
		public void testCheckDurationValueIsLessThanINFDurationValue() throws IOException{
		   //Act
		   List<String> dataFileArray = new ArrayList<String>();
		   _fileName = "index.m3u8";
		   _baseUrl = "http://media.example.com/";
		   _duration = "6.0";
		   _tag = new EXTINF(_baseUrl,dataFileArray, _fileName,_duration);
		   dataFileArray.add("#EXTINF:9.009,");
		   dataFileArray.add("http://media.example.com/first.ts");		   
		   List<ValidationReport> errors = new ArrayList<ValidationReport>();
		  
		  //Action
		  errors.addAll(_tag.isValid());
		   
		  //Assert		
	      assertEquals(errors.size(),1);
	      assertEquals(errors.get(0).Detail, "EXTINF duration value should not be greater then EXT-X-TARGETDURATION tag duration value.");
	      assertEquals(errors.get(0).ErrorTag, Constants.EXTINF);
	      assertEquals(errors.get(0).FileName,_fileName);
	   }
	   
		// test method catch if the duration specified is valid integer or float
		public void testCheckINFDurationValueIsNAN() throws IOException{
		   //Act
		   List<String> dataFileArray = new ArrayList<String>();
		   _fileName = "index.m3u8";
		   _baseUrl = "http://media.example.com/";
		   _duration = "6.0";
		   _tag = new EXTINF(_baseUrl,dataFileArray, _fileName,_duration);
		   dataFileArray.add("#EXTINF:ABC,");
		   dataFileArray.add("http://media.example.com/first.ts");		   
		   List<ValidationReport> errors = new ArrayList<ValidationReport>();
		  
		  //Action
		  errors.addAll(_tag.isValid());
		   
		  //Assert		
	      assertEquals(errors.size(),1);
	      assertEquals(errors.get(0).Detail, "EXTINF duration number should be an integer or float");
	      assertEquals(errors.get(0).ErrorTag, Constants.EXTINF);
	      assertEquals(errors.get(0).FileName,_fileName);
	   }
		
		// test method catch if the ts files specified in the menifiest files are valid.
		public void testCheckINFMediaFileIsValid() throws IOException{
			   //Act
			   List<String> dataFileArray = new ArrayList<String>();
			   _fileName = "index.m3u8";
			   _baseUrl = "http://media.example.com/";
			   _duration = "6.0";
			   _tag = new EXTINF(_baseUrl,dataFileArray, _fileName,_duration);
			   dataFileArray.add("#EXTINF:5,");
			   dataFileArray.add("Jibrish.ts");		   
			   List<ValidationReport> errors = new ArrayList<ValidationReport>();
			  
			  //Action
			  errors.addAll(_tag.isValid());
			   
			  //Assert		
		      assertEquals(errors.size(),1);
		      assertEquals(errors.get(0).Detail, "Jibrish.ts file does not exist on the server.");
		      assertEquals(errors.get(0).ErrorTag, Constants.EXTINF);
		      assertEquals(errors.get(0).FileName,_fileName);
		   }
		
	public void testIsFloatWhenStringIsFloat(){
		 List<String> dataFileArray = new ArrayList<String>();
		_fileName = "index.m3u8";
	   _baseUrl = "http://media.example.com/";
	   _duration = "6.0";
	   _tag = new EXTINF(_baseUrl,dataFileArray, _fileName,_duration);
	   
	   boolean result = _tag.isFloat("6.0");
	   
	   assertTrue(result);
	}
	
	public void testIsFloatWhenStringIsNotFloat(){
		 List<String> dataFileArray = new ArrayList<String>();
		_fileName = "index.m3u8";
	   _baseUrl = "http://media.example.com/";
	   _duration = "6.0";
	   _tag = new EXTINF(_baseUrl,dataFileArray, _fileName,_duration);
	   
	   boolean result = _tag.isFloat("6");
	   
	   assertFalse(result);
	 }
	
	public void testIsIntegerWhenStringIsInteger(){
		 List<String> dataFileArray = new ArrayList<String>();
		_fileName = "index.m3u8";
	   _baseUrl = "http://media.example.com/";
	   _duration = "6.0";
	   _tag = new EXTINF(_baseUrl,dataFileArray, _fileName,_duration);
	   
	   boolean result = _tag.isInteger("6");
	   
	   assertTrue(result);
	}
	
	public void testIsIntegerWhenStringIsNotInteger(){
		 List<String> dataFileArray = new ArrayList<String>();
		_fileName = "index.m3u8";
	   _baseUrl = "http://media.example.com/";
	   _duration = "6.0";
	   _tag = new EXTINF(_baseUrl,dataFileArray, _fileName,_duration);
	   
	   boolean result = _tag.isInteger("6.0");
	   
	   assertFalse(result);
	   
	   
	}
}
