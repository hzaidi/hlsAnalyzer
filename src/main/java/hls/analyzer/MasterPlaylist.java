package hls.analyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MasterPlaylist {
	List<String> _dataFileArray;
	String _fileName;
	String _baseUrl;
	MasterPlaylist(String fileName,String baseUrl,List<String> dataFileArray){
		_fileName = fileName;
		_baseUrl = baseUrl;
		_dataFileArray = dataFileArray;
	}
	public List<ValidationReport> parse() throws IOException{
		List<ValidationReport> reports = new ArrayList<ValidationReport>();
		List<Validator> validations = new ArrayList<Validator>();
		validations.add(new EXTM3U(_dataFileArray,_fileName));		
		validations.add(new EXTXMEDIA(_dataFileArray,_fileName));
		validations.add(new EXTXSTREAMINF(_baseUrl,_dataFileArray,_fileName));
		for (Validator validator : validations) {
			reports.addAll(validator.isValid());
		}
		return reports;
	}
}
