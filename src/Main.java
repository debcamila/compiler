import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException, ScannerException{
		
		Scanner scanner = new Scanner("data.txt");
		
		while(true) {
			Token t = scanner.scan();
			System.out.println(t.getLexema());
			System.out.println(t.getToken());
			System.out.println(t);
			if(t == null) {
				break;
			}
		}
		/*
		Arquivo arq = new Arquivo(args[0]);
		Scanner scanner = new Scanner(arq);
		try {
			
		}
		catch (ScannerException e){ // IMPRIMIR A LINHA E COLUNA DO ERRO 
			e.printStackTrace();
		}
		*/
		
		/*
		String theString = "";
		Arquivo arq = new Arquivo("data.txt");
		Scanner scanner = new Scanner(arq);

		theString = scanner.nextChar();
		while (scanner.hasNextLine()) {
		       theString = theString + "\n" + scanner.nextChar();
		}

		char[] charArray = theString.toCharArray();
		*/
		
		
		
		/*FileReader fileR = new FileReader("data.txt");
        BufferedReader bufferR = new BufferedReader(fileR); 
        
        int i = 0;
        
               
        while(true){
            int c  =  bufferR.read();
            System.out.println(c);
            if(c == -1){
                break;
            }
        }*/
		
	}

}
