package hls.analyzer;

import java.io.IOException;
import java.util.List;

public abstract class Validator implements IValidator{
	public abstract  List<ValidationReport> isValid() throws IOException;
	
}
