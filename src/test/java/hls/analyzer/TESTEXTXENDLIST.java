package hls.analyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class TESTEXTXENDLIST extends TestCase {
	  protected EXTXENDLIST _tag;
	   protected String _fileName;
	   
	   // test method catch invalid last line in the menifest file
	   public void testCheckLastLineWhenTagIsInvalid() throws IOException{
		   //Act
		   List<String> dataFileArray = new ArrayList<String>();
		   _fileName = "index.m3u8";      
		   _tag = new EXTXENDLIST(dataFileArray, _fileName);
		   dataFileArray.add("#EXT-X-ENDLISTX");	    
		  List<ValidationReport> errors = new ArrayList<ValidationReport>();
		  
		  //Action
		  errors.addAll(_tag.isValid());
		   
		  //Assert
	      assertEquals(errors.size(),1);
	      assertEquals(errors.get(0).Detail, "Last line of the file is not "+ Constants.EXTXENDLIST +" tag");
	      assertEquals(errors.get(0).ErrorTag, Constants.EXTXENDLIST);
	      assertEquals(errors.get(0).FileName,_fileName);
	   }
	   
	// test method catch Valid last line in the menifest file
	   public void testCheckLastLineWhenTagIsValid() throws IOException{
		   //Act
		   List<String> dataFileArray = new ArrayList<String>();
		   _fileName = "index.m3u8";      
		   _tag = new EXTXENDLIST(dataFileArray, _fileName);
		   dataFileArray.add("#EXT-X-ENDLIST");	    
		  List<ValidationReport> errors = new ArrayList<ValidationReport>();
		  
		  //Action
		  errors.addAll(_tag.isValid());
		   
		  //Assert
	      assertEquals(errors.size(),0);
	   }
}
