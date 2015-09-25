package hls.analyzer;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class TESTEXTM3U extends TestCase {
	   protected EXTM3U _tag;
	   protected String _fileName;
	   
	   // test method catch invalid first line in the menifest file
	   public void testCheckFirstLineWhenTagIsInvalid(){
		   //Act
		   List<String> dataFileArray = new ArrayList<String>();
		   _fileName = "index.m3u8";      
		   _tag = new EXTM3U(dataFileArray, _fileName);
		   dataFileArray.add("#EXTM3U2");	    
		  List<ValidationReport> errors = new ArrayList<ValidationReport>();
		  
		  //Action
		  errors.addAll(_tag.isValid());
		   
		  //Assert
	      assertEquals(errors.size(),1);
	      assertEquals(errors.get(0).Detail, "First line of the file is not "+ Constants.EXTM3U +" tag");
	      assertEquals(errors.get(0).ErrorTag, Constants.EXTM3U);
	      assertEquals(errors.get(0).FileName,_fileName);
	   }
	   
	// test method catch Valid first line in the menifest file
	   public void testCheckFirstLineWhenTagIsValid(){
		   //Act
		   List<String> dataFileArray = new ArrayList<String>();
		   _fileName = "index.m3u8";      
		   _tag = new EXTM3U(dataFileArray, _fileName);
		   dataFileArray.add("#EXTM3U");	    
		  List<ValidationReport> errors = new ArrayList<ValidationReport>();
		  
		  //Action
		  errors.addAll(_tag.isValid());
		   
		  //Assert
	      assertEquals(errors.size(),0);
	   }
}
