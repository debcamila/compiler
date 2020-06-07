import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Scanner {
	private int lookAhead = ' ';
	private BufferedReader bufferR;
	private LinhaColuna lc;
	private Token token;

	public Scanner(String arquivo) throws FileNotFoundException {
		this.bufferR = new BufferedReader(new FileReader(arquivo));
		this.lc = new LinhaColuna();
	}

	public LinhaColuna getLinhaColuna() {
		return this.lc;
	}
	public Token scan() throws IOException, ScannerException {
		lc.incrementeColuna(1);
		while (Character.isWhitespace((char) lookAhead)) {

			lookAhead = bufferR.read();
			if ((char) lookAhead == '\n') { // fim de coluna
				lc.incrementeLinha(1);
				lc.setColuna(0);
			} else if ((char) lookAhead == '\t') { // TAB
				lc.incrementeColuna(4);
			} else {
				lc.incrementeColuna(1);
			}
		}

		//OPERADORES RELACIONAIS
		if ((char) lookAhead == '<') {
			lookAhead = bufferR.read();
			lc.incrementeColuna(1);
			if ((char) lookAhead == '=') {
				token = new Token(TipoToken.OP_RELACIONAL_MENOR_IGUAL, "<=", lc);
				lookAhead = bufferR.read();
				return token;
			} else {
				token = new Token(TipoToken.OP_RELACIONAL_MENOR, "<",lc);
				return token;
			}
		} else if ((char) lookAhead == '>') {
			lookAhead = bufferR.read();
			lc.incrementeColuna(1);
			if ((char) lookAhead == '=') {
				token = new Token(TipoToken.OP_RELACIONAL_MAIOR_IGUAL, ">=",lc);
				lookAhead = bufferR.read();
				lc.incrementeColuna(1);
				return token;
			} else {
				token = new Token(TipoToken.OP_RELACIONAL_MAIOR, ">",lc);
				//lookAhead = bufferR.read();
				return token;
			}
		} else if ((char) lookAhead == '!') {
			lookAhead = bufferR.read();
			lc.incrementeColuna(1);
			if ((char) lookAhead == '=') {
				token = new Token(TipoToken.OP_RELACIONAL_DIFERENTE, "!=", lc);
				lookAhead = bufferR.read();
				return token;
			} else {
				throw new ScannerException("ERRO. Exclamacao sozinha. Esperava um =. Encontrou um: " +(char)lookAhead, lc);
			}
			
		//OPERADORES ARITMETICOS
		}else if((char) lookAhead == '-') {
			lookAhead = bufferR.read();
			token = new Token(TipoToken.OP_ARITMETICO_MENOS, "-", lc);
			return token;
		}else if((char) lookAhead == '+') {
			lookAhead = bufferR.read();
			token = new Token(TipoToken.OP_ARITMETICO_MAIS, "+", lc);
			return token;
		}else if((char) lookAhead == '*') {
			lookAhead = bufferR.read();
			token = new Token(TipoToken.OP_ARITMETICO_MULTIPLICACAO, "*", lc);
			return token;
		}else if((char) lookAhead == '=') {
			lookAhead = bufferR.read();
			if((char) lookAhead == '=') {
				lookAhead = bufferR.read();
				token = new Token(TipoToken.OP_RELACIONAL_COMPARACAO, "==", lc);
				return token;
			}else {
				//lookAhead = bufferR.read();
				token = new Token(TipoToken.OP_ARITMETICO_IGUAL, "=", lc);
				return token;
			}
			
		//ESPECIAIS
		}else if((char) lookAhead == '{') {
			lookAhead = bufferR.read();
			token = new Token(TipoToken.ESP_ABRE_CHAVES, "{", lc);
			return token;
		}else if((char) lookAhead == '}') {
			lookAhead = bufferR.read();
			token = new Token(TipoToken.ESP_FECHA_CHAVES, "}", lc);
			return token;
		}else if((char) lookAhead == '(') {
			lookAhead = bufferR.read();
			token = new Token(TipoToken.ESP_ABRE_PARENTESES, "(", lc);
			return token;
		}else if((char) lookAhead == ')') {
			lookAhead = bufferR.read();
			token = new Token(TipoToken.ESP_FECHA_PARENTESES, ")", lc);
			return token;
		}else if((char) lookAhead == ',') {
			lookAhead = bufferR.read();
			token = new Token(TipoToken.ESP_VIRGULA, ",", lc);
			return token;
		}else if((char) lookAhead == ';') {
			lookAhead = bufferR.read();
			token = new Token(TipoToken.ESP_PONTO_E_VIRGULA, ";", lc);
			return token;
		
		// CHAR
		}else if((char) lookAhead == '\'') {
			lookAhead = bufferR.read();
			char c = (char)lookAhead;
			if(Character.isDigit(c) || Character.isLetter(c)) {
				lookAhead = bufferR.read();
				if((char)lookAhead == '\'') {
					token = new Token(TipoToken.VALOR_CHAR, "'"+c+"'", lc);
					lookAhead = bufferR.read();
					return token;
				}else {
					throw new ScannerException("ERRO. Char mal formado. Esperava um fecha aspas. Encontrou um: " +(char)lookAhead, lc);
				}
			}else {
				throw new ScannerException("ERRO. Char mal formado. Esperava um digito ou uma letra. Encontrou um: " +(char)lookAhead, lc);
			}
			
		//IDENTIFICADOR E PALAVRA RESERVADA
		}else if(Character.isLetter((char)lookAhead) || (char)lookAhead == '_')   {
			String result = "" + (char)lookAhead;
			lookAhead = bufferR.read();
			while(Character.isLetter((char)lookAhead) || (char)lookAhead == '_' || Character.isDigit((char)lookAhead))  {
				result+=(char)lookAhead;
				lookAhead = bufferR.read();
			}
			
			// main  |  if  |  else  |  while  |  do  |  for  |  int  |  float  |  char
			if(result.equals("main")) {
				return token = new Token(TipoToken.PALAVRA_RESERVADA_MAIN, "main", lc);
			}else if(result.equals("if")) {
				return token = new Token(TipoToken.PALAVRA_RESERVADA_IF, "if", lc);
			}else if(result.equals("else")) {
				return token = new Token(TipoToken.PALAVRA_RESERVADA_ELSE, "else", lc);
			}else if(result.equals("while")) {
				return token = new Token(TipoToken.PALAVRA_RESERVADA_WHILE, "while", lc);
			}else if(result.equals("do")) {
				return token = new Token(TipoToken.PALAVRA_RESERVADA_DO, "do", lc);
			}else if(result.equals("for")) {
				return token = new Token(TipoToken.PALAVRA_RESERVADA_FOR, "for", lc);
			}else if(result.equals("int")) {
				return token = new Token(TipoToken.PALAVRA_RESERVADA_INT, "int", lc);
			}else if(result.equals("float")) {
				return token = new Token(TipoToken.PALAVRA_RESERVADA_FLOAT, "float", lc);
			}else if(result.equals("char")) {
				return token = new Token(TipoToken.PALAVRA_RESERVADA_CHAR, "char", lc);
			}else {
				return token = new Token(TipoToken.IDENTIFICADOR, result, lc);
			}
			
		// DIGITO
		}else if((char)lookAhead == '.') { //float
			String result = ""+(char)lookAhead;
			lookAhead = bufferR.read();
			while(Character.isDigit((char)lookAhead)) {
				result+=(char)lookAhead;
				lookAhead = bufferR.read();
			}
			if(result.equals(".")) {
				throw new ScannerException("ERRO. Float mal formado. Esperava um digito depois do ponto. Encontrou um: " +(char)lookAhead, lc);
			}
			return token = new Token(TipoToken.VALOR_FLOAT, result, lc);
		}else if(Character.isDigit((char)lookAhead)) { //inteiro
			String result = ""+(char)lookAhead;
			lookAhead = bufferR.read();
			while(Character.isDigit((char)lookAhead)) {
				result+=(char)lookAhead;
				lookAhead = bufferR.read();
			}
			if((char)lookAhead == '.') { 
				result+=(char)lookAhead;
				lookAhead = bufferR.read();
				if(!Character.isDigit((char)lookAhead)){
					throw new ScannerException("ERRO. Float mal formado. Esperava um digito depois do ponto. Encontrou um: " +(char)lookAhead, lc);
				}
				while(Character.isDigit((char)lookAhead)) {
					result+=(char)lookAhead;
					lookAhead = bufferR.read();
				}
				return token = new Token(TipoToken.VALOR_FLOAT, result, lc);
			}else {
				return token = new Token(TipoToken.VALOR_INT, result, lc);
			}
			
			
		//COMENTARIOS
		}else if((char) lookAhead == '/') { //COMENTARIO SIMPLES
			lookAhead = bufferR.read();
			if((char) lookAhead == '/') {
				while((char) lookAhead != '\n') {
					lookAhead = bufferR.read();
					lc.incrementeColuna(1);
				}
				if((char) lookAhead == '\n') {
					lookAhead = bufferR.read();
					lc.setColuna(0);
					lc.incrementeLinha(1);
					return this.scan();
				}
				if((char) lookAhead == -1) {
					System.out.println("fim de arquivo");
					return null;
				}
				
			}else if((char)lookAhead == '*') { //COMENTARIO MULTILINHA
				char lookAheadPast = (char)lookAhead;
				lookAhead = bufferR.read();
				while(!(lookAheadPast == '*') || !((char)lookAhead == '/')) {
					if(lookAhead == -1) {
						throw new ScannerException("ERRO. Fim de arquivo dentro de um comentario.Encontrou um: " +(char)lookAhead, lc);
					}
					else if((char)lookAhead == '\n') {
						lc.incrementeLinha(1);
					}
					lookAheadPast = (char)lookAhead;
					lookAhead = bufferR.read();
					lc.incrementeColuna(1);
				}
				lookAhead = bufferR.read();
				return this.scan();
			
			
			}else {
				//lookAhead = bufferR.read();
				token = new Token(TipoToken.OP_ARITMETICO_DIVISAO, "/", lc);
				return token;
			}
		}
		else if (lookAhead == -1) {
			System.out.println("fim de arquivo");
			return token = new Token(TipoToken.FIM_DE_ARQUIVO, "EOF", lc);
		}
		else { // CHARACTER INVALIDO
			throw new ScannerException("ERRO. Character invalido.Encontrou um: " +(char)lookAhead, lc);
		}
		return null;
	}
}

