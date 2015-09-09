package hls.analyzer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MasterValidator implements IMasterValidator {

	private List<String> _dataFileArray;
	MasterValidator(List<String> dataFileArray){
		_dataFileArray = dataFileArray;
	}
	
	public boolean isValid() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> noparams[] = {};
		Class<TagValidator> validation = TagValidator.class;
		Object obj= validation.newInstance();		
		Method[] methods = ITagValidator.class.getDeclaredMethods();
		for (Method method : methods) {
		    method.invoke(obj, noparams);
		}
		
		return false;
	}

}
