package hls.analyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MediaPlaylist {
	List<String> _dataFileArray;
	String _fileName;
	MediaPlaylist(String fileName,List<String> dataFileArray){
		_fileName = fileName;		
		_dataFileArray = dataFileArray;
	}
	public List<ValidationReport> parse() throws IOException{
		List<ValidationReport> reports = new ArrayList<ValidationReport>();
		List<Validator> validations = new ArrayList<Validator>();
		validations.add(new EXTM3U(_dataFileArray,_fileName));
		validations.add(new EXTXVERSION(_dataFileArray,_fileName));		
		validations.add(new EXTXTARGETDURATION(_dataFileArray, _fileName));		
		validations.add(new EXTXENDLIST(_dataFileArray, _fileName));
		for (Validator validator : validations) {
			reports.addAll(validator.isValid());
		}
		return reports;
	}
}
