import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException, ScannerException, ParserException{
		Parser parser = new Parser(args[0]);
		parser.analisarPrograma();	
	}
}
