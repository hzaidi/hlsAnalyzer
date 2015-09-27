package hls.analyzer;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class TESTEXTXVERSION extends TestCase {
	 protected EXTXVERSION _tag;
	 protected String _fileName;
	 // test method catch if there is multiple verison tags
	 public void testCheckMultipleVersionTags(){
	   //Act
	   List<String> dataFileArray = new ArrayList<String>();
	   _fileName = "index.m3u8";      
	   _tag = new EXTXVERSION(dataFileArray, _fileName);
	   dataFileArray.add("#EXTM3U");	    
	   dataFileArray.add("#EXT-X-VERSION:3");
	   dataFileArray.add("#EXT-X-VERSION:2");
	  List<ValidationReport> errors = new ArrayList<ValidationReport>();
	  
	  //Action
	  errors.addAll(_tag.isValid());
	   
	  //Assert
	  assertEquals(errors.size(),1);
	  assertEquals(errors.get(0).Detail, "Invalid file because multiple EXT-X-VERSION tag detected.");
	  assertEquals(errors.get(0).ErrorTag, Constants.EXTXVERSION);
	  assertEquals(errors.get(0).FileName,_fileName);
	}
	 
	// test method catch if version tag exist
	 public void testCheckDetectingVersionTags(){
	   //Act
	   List<String> dataFileArray = new ArrayList<String>();
	   _fileName = "index.m3u8";      
	   _tag = new EXTXVERSION(dataFileArray, _fileName);
	   dataFileArray.add("#EXTM3U");	    
	   List<ValidationReport> errors = new ArrayList<ValidationReport>();
	  
	  //Action
	  errors.addAll(_tag.isValid());
	   
	  //Assert
	  assertEquals(errors.size(),1);
	  assertEquals(errors.get(0).Detail, "Unable to detect the version tag.");
	  assertEquals(errors.get(0).ErrorTag, Constants.EXTXVERSION);
	  assertEquals(errors.get(0).FileName,_fileName);
	}
	 
	// test method catch if version tag value is NAN
	 public void testCheckVersionTagsValueIfItsNAN(){
	   //Act
	   List<String> dataFileArray = new ArrayList<String>();
	   _fileName = "index.m3u8";      
	   _tag = new EXTXVERSION(dataFileArray, _fileName);
	   dataFileArray.add("#EXTM3U");	 
	   dataFileArray.add("#EXT-X-VERSION:ABS");
	   List<ValidationReport> errors = new ArrayList<ValidationReport>();
	  
	  //Action
	  errors.addAll(_tag.isValid());
	   
	  //Assert
	  assertEquals(errors.size(),1);
	  assertEquals(errors.get(0).Detail, "Version number should be an integer");
	  assertEquals(errors.get(0).ErrorTag, Constants.EXTXVERSION);
	  assertEquals(errors.get(0).FileName,_fileName);
	}
	 
	// test method catch if version tag value is 2
	//EXT-X-KEY does not have IV attr
	public void testCheckVersion2TagsIfEXTXKEYWithoutAttrIV(){
	  //Act
	  List<String> dataFileArray = new ArrayList<String>();
	  _fileName = "index.m3u8";      
	  _tag = new EXTXVERSION(dataFileArray, _fileName);
	  dataFileArray.add("#EXTM3U");	 
	  dataFileArray.add("#EXT-X-VERSION:2");
	  dataFileArray.add("#EXT-X-KEY:IVISINVALID=\"AB4F\"");
	  
	  List<ValidationReport> errors = new ArrayList<ValidationReport>();
	  
	 //Action
	 errors.addAll(_tag.isValid());
	   
	 //Assert
	 assertEquals(errors.size(),1);
	 assertEquals(errors.get(0).Detail, "Version number is 2 and unable to detect the IV attribute on EXTXKEY.");
	 assertEquals(errors.get(0).ErrorTag, Constants.EXTXVERSION);
	 assertEquals(errors.get(0).FileName,_fileName);
	}
	
	// test method catch if version tag value is 2
	//EXT-X-KEY have IV attr
	 public void testCheckVersion2TagsIfEXTXKEYWithAttrIV(){
	   //Act
	   List<String> dataFileArray = new ArrayList<String>();
	   _fileName = "index.m3u8";      
	   _tag = new EXTXVERSION(dataFileArray, _fileName);
	   dataFileArray.add("#EXTM3U");	 
	   dataFileArray.add("#EXT-X-VERSION:2");
	   dataFileArray.add("#EXT-X-KEY:IV=\"AB4F\"");
	   
	   List<ValidationReport> errors = new ArrayList<ValidationReport>();
	  
	  //Action
	  errors.addAll(_tag.isValid());
	   
	  //Assert
	  assertEquals(errors.size(),0);	  
	}

	// test method catch if version tag value is 2
	// EXTINF has a floating vlaue
	public void testCheckVersion3TagsIfEXTINFValueIsNotValid(){
	  //Act
	  List<String> dataFileArray = new ArrayList<String>();
	  _fileName = "index.m3u8";      
	  _tag = new EXTXVERSION(dataFileArray, _fileName);
	  dataFileArray.add("#EXTM3U");	 
	  dataFileArray.add("#EXT-X-VERSION:2");
	  dataFileArray.add("#EXTINF:9.009,");
	  
	  List<ValidationReport> errors = new ArrayList<ValidationReport>();
	  
	 //Action
	 errors.addAll(_tag.isValid());
	   
	 //Assert
	 assertEquals(errors.size(),1);
	 assertEquals(errors.get(0).Detail, "Version number is 2 which is less then 3 and EXTINF duration values are in floating-point values.");
	 assertEquals(errors.get(0).ErrorTag, Constants.EXTXVERSION);
	 assertEquals(errors.get(0).FileName,_fileName);
	}
	
	// test method catch if version tag value is 3
	// EXTINF has a floating vlaue
	public void testCheckVersion3TagsIfEXTINFValueIsValid(){
	  //Act
	  List<String> dataFileArray = new ArrayList<String>();
	  _fileName = "index.m3u8";      
	  _tag = new EXTXVERSION(dataFileArray, _fileName);
	  dataFileArray.add("#EXTM3U");	 
	  dataFileArray.add("#EXT-X-VERSION:3");
	  dataFileArray.add("#EXTINF:9.009,");
	  
	  List<ValidationReport> errors = new ArrayList<ValidationReport>();
	  
	 //Action
	 errors.addAll(_tag.isValid());
	   
	 //Assert
	 assertEquals(errors.size(),0);	 
	}
	
	// test method catch if version tag value is 3
	// EXT-X-BYTERANGE tag should not be present
	public void testCheckVersion4TagsIfVersionIs3ItContainsEXTXBYTERANGE(){
	  //Act
	  List<String> dataFileArray = new ArrayList<String>();
	  _fileName = "index.m3u8";      
	  _tag = new EXTXVERSION(dataFileArray, _fileName);
	  dataFileArray.add("#EXTM3U");	 
	  dataFileArray.add("#EXT-X-VERSION:3");
	  dataFileArray.add("#EXT-X-BYTERANGE");
	  
	  List<ValidationReport> errors = new ArrayList<ValidationReport>();
	  
	 //Action
	 errors.addAll(_tag.isValid());
	   
	 //Assert
	 assertEquals(errors.size(),1);
	 assertEquals(errors.get(0).Detail, "Version number is 3 and EXT-X-BYTERANGE tag is detected.");
	 assertEquals(errors.get(0).ErrorTag, Constants.EXTXVERSION);
	 assertEquals(errors.get(0).FileName,_fileName);
	}
	
	// test method catch if version tag value is 4
	// EXT-X-BYTERANGE tag should not be present
	public void testCheckVersion4TagsIfVersionIs4ItContainsEXTXBYTERANGE(){
	  //Act
	  List<String> dataFileArray = new ArrayList<String>();
	  _fileName = "index.m3u8";      
	  _tag = new EXTXVERSION(dataFileArray, _fileName);
	  dataFileArray.add("#EXTM3U");	 
	  dataFileArray.add("#EXT-X-VERSION:4");
	  dataFileArray.add("#EXT-X-BYTERANGE");
	  
	  List<ValidationReport> errors = new ArrayList<ValidationReport>();
	  
	 //Action
	 errors.addAll(_tag.isValid());
	   
	 //Assert
	 assertEquals(errors.size(),0);	 
	}
	
	
	// test method catch if version tag value is 3
	// EXT-X-I-FRAMES-ONLY tag should not be present
	public void testCheckVersion4TagsIfVersionIs3ItContainsEXTXIFRAMESONLY(){
	  //Act
	  List<String> dataFileArray = new ArrayList<String>();
	  _fileName = "index.m3u8";      
	  _tag = new EXTXVERSION(dataFileArray, _fileName);
	  dataFileArray.add("#EXTM3U");	 
	  dataFileArray.add("#EXT-X-VERSION:3");
	  dataFileArray.add("#EXT-X-I-FRAMES-ONLY");
	  
	  List<ValidationReport> errors = new ArrayList<ValidationReport>();
	  
	 //Action
	 errors.addAll(_tag.isValid());
	   
	 //Assert
	 assertEquals(errors.size(),1);
	 assertEquals(errors.get(0).Detail, "Version number is 3 and EXT-X-I-FRAMES-ONLY tag is detected.");
	 assertEquals(errors.get(0).ErrorTag, Constants.EXTXVERSION);
	 assertEquals(errors.get(0).FileName,_fileName);
	}
	
	// test method catch if version tag value is 4
	// EXT-X-I-FRAMES-ONLY tag should not be present
	public void testCheckVersion4TagsIfVersionIs4ItContainsEXTXIFRAMESONLY(){
	  //Act
	  List<String> dataFileArray = new ArrayList<String>();
	  _fileName = "index.m3u8";      
	  _tag = new EXTXVERSION(dataFileArray, _fileName);
	  dataFileArray.add("#EXTM3U");	 
	  dataFileArray.add("#EXT-X-VERSION:4");
	  dataFileArray.add("#EXT-X-I-FRAMES-ONLY");
	  
	  List<ValidationReport> errors = new ArrayList<ValidationReport>();
	  
	 //Action
	 errors.addAll(_tag.isValid());
	   
	 //Assert
	 assertEquals(errors.size(),0);	 
	}
	
}
