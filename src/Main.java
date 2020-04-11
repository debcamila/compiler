import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException, ScannerException{
		
		Scanner scanner = new Scanner(args[0]);
		
		while(true) {
			Token t = scanner.scan();
			if(t.getToken() == TipoToken.FIM_DE_ARQUIVO) {
				break;
			}
		}		
	}
}
