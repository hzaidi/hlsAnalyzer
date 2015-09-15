package hls.analyzer;

import java.util.List;

public abstract class Validator implements IValidator{
	public abstract  List<ValidationReport> isValid();
}
