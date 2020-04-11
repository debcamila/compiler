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

	public Token scan() throws IOException, ScannerException {

		while (Character.isWhitespace((char) lookAhead)) {

			lookAhead = bufferR.read();
			if ((char) lookAhead == '\n') { // fim de coluna
				//lc.setLinha(1);
				lc.setColuna(-1);
				lc.incrementeLinha(1);
			} else if ((char) lookAhead == '\t') { // TAB
				//lc.setColuna(4);
				lc.incrementeColuna(4);
			} else {
				//lc.setColuna(1);
				lc.incrementeColuna(1);
			}
		}

		//OPERADORES RELACIONAIS
		if ((char) lookAhead == '<') {
			lookAhead = bufferR.read();
			//lc.incrementeColuna(1);
			if ((char) lookAhead == '=') {
				token = new Token(TipoToken.OP_RELACIONAL_MENOR_IGUAL, "<=", lc);
				lookAhead = bufferR.read();
				return token;
			} else {
				token = new Token(TipoToken.OP_RELACIONAL_MENOR, "<",lc);
				//lookAhead = bufferR.read();
				return token;
			}
		} else if ((char) lookAhead == '>') {
			lookAhead = bufferR.read();
			if ((char) lookAhead == '=') {
				token = new Token(TipoToken.OP_RELACIONAL_MAIOR_IGUAL, ">=",lc);
				lookAhead = bufferR.read();
				return token;
			} else {
				token = new Token(TipoToken.OP_RELACIONAL_MAIOR, ">",lc);
				lookAhead = bufferR.read();
				return token;
			}
		} else if ((char) lookAhead == '!') {
			lookAhead = bufferR.read();
			if ((char) lookAhead == '=') {
				token = new Token(TipoToken.OP_RELACIONAL_DIFERENTE, "!=", lc);
				lookAhead = bufferR.read();
				return token;
			} else {
				//throw new ScannerException("ERRO. Exclamação sozinha. Esperava um '='). Encontrou um: " +(char)lookAhead);
			}
			
		//OPERADORES ARITMÉTICOS
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
			String c = ""+(char)lookAhead;
			lookAhead = bufferR.read();
			if((char)lookAhead == '\'') {
				token = new Token(TipoToken.VALOR_CHAR, c, lc);
				lookAhead = bufferR.read();
				return token;
			}else {
				//throw new ScannerException("ERRO. Char mal formado. Esperava um fecha aspas. Encontrou um: " +(char)lookAhead);
			}
			
		//COMENTARIOS
		}else if((char) lookAhead == '/') {
			lookAhead = bufferR.read();
			if((char) lookAhead == '/') {
				while((char) lookAhead != '\n') {
					lookAhead = bufferR.read();
				}
				if((char) lookAhead == '\n') {
					lc.incrementeLinha(1);
					lc.setColuna(-1);
					lookAhead = bufferR.read();
					return this.scan();
				}
				if((char) lookAhead == -1) {
					System.out.println("fim de arquivo");
					return null;
				}
			}else {
				token = new Token(TipoToken.OP_ARITMETICO_DIVISAO, "/", lc);
				return token;
			}
		}
		//FAZER O ELSE PARA CARACTER INVALIDO
		if (lookAhead == -1) {
			System.out.println("fim de arquivo");
			return null;
		}
		return null;
	}
	
	public Boolean isNumber(int ascii) {
		if(ascii >= 48 && ascii <= 57) {
			return true;
		}else {
			return false;
		}
	}
	
	//toda vez que incrementar o lookahead incrementar a coluna, menos em comentario ????
	//Toda vez que achar um \n incrementa a linha e zera a coluna OK
	//pq o scannerexception nao funciona
	//rever a parte do character OK
	//fazer int e float e id e palavraReservada e comentario multi linha 
}

