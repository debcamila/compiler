import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
	private Scanner scanner;

	private Token token;
	private int escopo;
	/*
	private List<Tabela> tabela;
	private Boolean declaracao;
	private int registrador;
	private int label;
	*/

	public Parser(String arquivo) throws IOException, ParserException, ScannerException {
		this.scanner = new Scanner(arquivo);
		this.token = this.scanner.scan();
		/*
		this.tabela = new ArrayList<>();
		this.escopo = 0;
		this.registrador = 0;
		this.label = 0;
		 */
	}

	public Boolean analisarFator() throws IOException, ParserException, ScannerException {		
		if (this.token.getToken().equals(TipoToken.IDENTIFICADOR)) {
			return true;			
		} else if (this.token.getToken().equals(TipoToken.VALOR_FLOAT)) {
			return true;
		} else if (this.token.getToken().equals(TipoToken.VALOR_INT)) {
			return true;
		} else if (this.token.getToken().equals(TipoToken.VALOR_CHAR)) {
			return true;
		} else if (this.token.getToken().equals(TipoToken.ESP_ABRE_PARENTESES)) {
			this.token = this.scanner.scan();
			analisarExpressao();
			if(this.token.getToken().equals(TipoToken.ESP_FECHA_PARENTESES)) {
				return true;
			}
			else {
				throw new ParserException("ERRO. Esperava-se um ). Encontrou um " + this.token.getLexema(), this.scanner.getLinhaColuna());
			}
		}
		throw new ParserException("ERRO. Esperava-se um valor INT, FLOAT ou CHAR. Encontrou um " + this.token.getLexema(), this.scanner.getLinhaColuna());
	}

	public Boolean analisarTermo() throws IOException, ParserException, ScannerException {
		if (analisarFator()) {
			System.out.println("li" +this.token.getLexema());
			this.token = scanner.scan();
			while (this.token.getToken().equals(TipoToken.OP_ARITMETICO_MULTIPLICACAO)|| this.token.getToken().equals(TipoToken.OP_ARITMETICO_DIVISAO)) {
				this.token = scanner.scan();
				analisarFator();
				this.token = scanner.scan();
			}
			System.out.println("leu" +this.token.getLexema());
			return true;
		} else {
			return false;
		} 
	}

	public Boolean analisarExpressao() throws IOException, ParserException, ScannerException {
			analisarTermo();
			analisarExpressaoAritmetrica();
			return true;		
	}
	
	public Boolean analisarExpressaoAritmetrica() throws IOException, ParserException, ScannerException {
		if (this.token.getToken().equals(TipoToken.OP_ARITMETICO_MAIS) || (this.token.getToken().equals(TipoToken.OP_ARITMETICO_MENOS))) {
			this.token = scanner.scan();
			analisarTermo();
			analisarExpressaoAritmetrica();
		}
		return true;
	}

	public Boolean analisarOpRelacional() throws IOException, ParserException, ScannerException{
		if(this.token.getToken().equals(TipoToken.OP_RELACIONAL_MAIOR_IGUAL)) {
			return true;
		}else if (this.token.getToken().equals(TipoToken.OP_RELACIONAL_MENOR_IGUAL)) {
			return true;
		}else if(this.token.getToken().equals(TipoToken.OP_RELACIONAL_MAIOR)) {
			return true;
		}else if(this.token.getToken().equals(TipoToken.OP_RELACIONAL_MENOR)) {
			return true;
		}else if(this.token.getToken().equals(TipoToken.OP_RELACIONAL_DIFERENTE)) {
			return true;
		}else if(this.token.getToken().equals(TipoToken.OP_RELACIONAL_COMPARACAO)) {
			return true;
		}
		return false;
	}
	
	public Boolean analisarExpRelacional() throws IOException, ParserException, ScannerException{
		//this.token = scanner.scan();
		analisarExpressao();
		this.token = scanner.scan();
		analisarOpRelacional();
		//this.token = scanner.scan();
		analisarExpressao();
		return true;
	}
	
	public Boolean analisarAtribuicao() throws IOException, ParserException, ScannerException{
		if(this.token.getToken().equals(TipoToken.IDENTIFICADOR)) {
			this.token = scanner.scan();
			if(this.token.getToken().equals(TipoToken.OP_ARITMETICO_IGUAL)) {
				this.token = scanner.scan();
				if(analisarExpressao()) {
					if(this.token.getToken().equals(TipoToken.ESP_PONTO_E_VIRGULA)) {
						return true;
					}else {
						throw new ParserException("ERRO. Esperava-se ;. Encontrou um "+ this.token.getLexema(), this.scanner.getLinhaColuna());
					}
				}
			}
			else {
				throw new ParserException("ERRO. Esperava-se =. Encontrou um "+ this.token.getLexema(), this.scanner.getLinhaColuna());
			}
		}
		else {
			throw new ParserException("ERRO. Esperava-se um identificador. Encontrou um "+ this.token.getLexema(), this.scanner.getLinhaColuna());
		}
		return false;
	}
	
	public Boolean analisarDeclaracaoVariavel() throws IOException, ParserException, ScannerException{
		if(analisarTipoDado()) {
			this.token = scanner.scan();
			if(this.token.getToken().equals(TipoToken.IDENTIFICADOR)) {
				this.token = scanner.scan();
				while(this.token.getToken().equals(TipoToken.ESP_VIRGULA)) {
					this.token = scanner.scan();
					if(!this.token.getToken().equals(TipoToken.IDENTIFICADOR)){
						throw new ParserException("ERRO. Esperava-se um identificador. Encontrou um "+ this.token.getLexema(), this.scanner.getLinhaColuna());
					}
					this.token = scanner.scan();
				}
				if(this.token.getToken().equals(TipoToken.ESP_PONTO_E_VIRGULA)) {
					return true;
				}else {
					throw new ParserException("ERRO. Esperava-se ;. Encontrou um "+ this.token.getLexema(), this.scanner.getLinhaColuna());
				}
			}
		}
		return false;
	}
	
	public Boolean analisarTipoDado() throws IOException, ParserException, ScannerException{
		if(this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_INT)) {
			return true;
		}else if(this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_FLOAT)) {
			return true;
		}else if(this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_CHAR)) {
			return true;
		}else {
			throw new ParserException("ERRO. Esperava-se INT, FLOAT ou CHAR. Encontrou um " + this.token.getLexema(), this.scanner.getLinhaColuna());
		}
	}
	

	public void analisarPrograma() throws IOException, ParserException, ScannerException {
		if (this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_INT)) {
			this.token = scanner.scan();
			if (this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_MAIN)) {
				this.token = scanner.scan();
				if (this.token.getToken().equals(TipoToken.ESP_ABRE_PARENTESES)) {
					this.token = scanner.scan();
					if (this.token.getToken().equals(TipoToken.ESP_FECHA_PARENTESES)) {
						this.token = scanner.scan();
						// analiso o bloco
						if (!this.token.getToken().equals(TipoToken.FIM_DE_ARQUIVO)) {
							// Após o fechamento do bloco do programa (main) não pode haver mais tokens, ou
							// seja, o proximo retorno do scanner deve ser fim_de_arquivo.
						}
					} else {
						// deveria ser um )
					}
				} else {
					// deveria ser um (
				}
			} else {
				// deveria ser um main
			}
		} else {
			// programa deve ser iniciado com int
		}
	}

	public void analisarBloco() throws IOException, ScannerException {

	}
}
