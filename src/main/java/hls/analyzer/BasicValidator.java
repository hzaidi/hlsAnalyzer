package hls.analyzer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class BasicValidator  implements IBasicValidator{

	private List<String> _dataFileArray;
	public BasicValidator(List<String> dataFileArray){
		_dataFileArray = dataFileArray;
	}
	public void doValidation() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<BasicTagValidator> validation = BasicTagValidator.class;
		Object obj= validation.newInstance();		
		Method[] methods = IBasicTagValidator.class.getDeclaredMethods();
		for (Method method : methods) {
		    method.invoke(obj, _dataFileArray);
		}		
	}

}
