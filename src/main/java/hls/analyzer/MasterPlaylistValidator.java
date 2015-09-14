package hls.analyzer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MasterPlaylistValidator implements IMasterPlayListValidator {

	private List<String> _dataFileArray;
	MasterPlaylistValidator(List<String> dataFileArray){
		_dataFileArray = dataFileArray;
	}
	
	public void doValidation() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {		
		Class<MasterTagValidator> validation = MasterTagValidator.class;
		Object obj= validation.newInstance();		
		Method[] methods = IMasterTagValidator.class.getDeclaredMethods();
		for (Method method : methods) {
		    method.invoke(obj, _dataFileArray);
		}
	}

}
