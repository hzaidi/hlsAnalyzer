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
}
