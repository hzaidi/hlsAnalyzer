package hls.analyzer;

import java.io.IOException;
import java.util.Scanner;

public interface IFileReaderHandler {
	public Scanner getScannerStream() throws IOException;
}
