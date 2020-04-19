import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException, ScannerException, ParserException{
		
		Parser parser = new Parser("data.txt");
		
		if(parser.analisarDeclaracaoVariavel() == true) {
			System.out.println("iuhu");
		}else {
			System.out.println("merda");
		}
	
	}
}
