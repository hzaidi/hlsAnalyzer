package hls.analyzer;

import java.lang.reflect.InvocationTargetException;

public interface IMasterValidator {
	public boolean isValid() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException ;
}
