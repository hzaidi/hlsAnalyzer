package hls.analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EXTXSTREAMINF extends Validator{
	private List<String> _dataFileArray;
	EXTXSTREAMINF(List<String> dataFileArray){
		_dataFileArray = dataFileArray;
	}
	@Override
	public List<ValidationReport> isValid() {
		List<Map<String,String>> maps = new ArrayList<Map<String, String>>();
		for (String dataItem : _dataFileArray){			
			if(dataItem.matches(Constants.streamRegex)){
				int index = _dataFileArray.indexOf(dataItem);
				int uriIndex = index + 1;
				System.out.println("Line number:" + index + "--" + dataItem);
				System.out.println("Line number:" + uriIndex + "--" + _dataFileArray.get(uriIndex));
				/*Map<String, String> myMap1 = new HashMap<String, String>();
		        myMap1.put(dataItem, dataFileArray.get(dataFileArray.indexOf(dataItem) + 1));
				maps.add(myMap1);*/				
			}
		}
		return null;
	}
}
