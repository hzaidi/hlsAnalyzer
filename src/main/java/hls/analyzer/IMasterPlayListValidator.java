package hls.analyzer;

import java.lang.reflect.InvocationTargetException;

public interface IMasterPlayListValidator {
	public void doValidation() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}
