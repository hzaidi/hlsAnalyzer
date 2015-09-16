package hls.analyzer;

import java.io.IOException;
import java.util.List;

interface IValidator {
	 public List<ValidationReport> isValid() throws IOException;
}
