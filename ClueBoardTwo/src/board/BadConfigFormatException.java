package board;
import java.io.FileWriter;
import java.io.IOException;



//Naomi and Brandon
public class BadConfigFormatException extends Exception{
	private String message;
	private String outFile = "exceptionLog.txt";
	
	public BadConfigFormatException(String message) {
		super(message);
		try {
		FileWriter out = new FileWriter(outFile,true);
		out.write(message + "\n");
		}
		catch(IOException e) {
			System.err.println("exceptionLog.txt does not exist");
		}
		
	}

	@Override
	public String toString() {
		return message;
	}
	
	
	
}
