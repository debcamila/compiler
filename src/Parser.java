import java.io.IOException;
import java.util.ArrayList;

public class Parser {
	private Boolean elseAve = false;
	private Scanner scanner;
	private Token token;
	private AnalisadorSemantico analisador = new AnalisadorSemantico();
	

	public Parser(String arquivo) throws IOException, ParserException, ScannerException {
		this.scanner = new Scanner(arquivo);
		this.token = this.scanner.scan();
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
			this.token = scanner.scan();
			while (this.token.getToken().equals(TipoToken.OP_ARITMETICO_MULTIPLICACAO)|| this.token.getToken().equals(TipoToken.OP_ARITMETICO_DIVISAO)) {
				this.token = scanner.scan();
				analisarFator();
				this.token = scanner.scan();
			}
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
		throw new ParserException("ERRO. Esperava-se um operador relacional. Encontrou um " + this.token.getLexema(), this.scanner.getLinhaColuna());
	}
	
	public Boolean analisarExpRelacional() throws IOException, ParserException, ScannerException{
		analisarExpressao();
		analisarOpRelacional();
		this.token = scanner.scan();
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
		TipoToken varTipo = this.token.getToken();
		if(analisarTipoDado()) {
			this.token = scanner.scan();
			ArrayList<String> varName = new ArrayList<>();
			if(this.token.getToken().equals(TipoToken.IDENTIFICADOR)) {
				varName.add(this.token.getLexema());
				this.token = scanner.scan();
				while(this.token.getToken().equals(TipoToken.ESP_VIRGULA)) {
					this.token = scanner.scan();
					if(!this.token.getToken().equals(TipoToken.IDENTIFICADOR)){
						throw new ParserException("ERRO. Esperava-se um identificador. Encontrou um "+ this.token.getLexema(), this.scanner.getLinhaColuna());
					}
					varName.add(this.token.getLexema());
					this.token = scanner.scan();
				}
				if(this.token.getToken().equals(TipoToken.ESP_PONTO_E_VIRGULA)) {
					varName.forEach((n) -> {
						try {
							analisador.adicionarItemTabela(varTipo, n, this.scanner);
						} catch (ParserException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.exit(0);
						} catch (ScannerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.exit(0);
						}
					});
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
	
	public void analisarComandoBasico() throws IOException, ParserException, ScannerException{
		if(this.token.getToken().equals(TipoToken.IDENTIFICADOR)) {
			analisarAtribuicao();
		}else if(this.token.getToken().equals(TipoToken.ESP_ABRE_CHAVES)) {
			analisarBloco();
		}
	}
	
	public void analisarComando() throws IOException, ParserException, ScannerException{
		if(this.token.getToken().equals(TipoToken.IDENTIFICADOR) || this.token.getToken().equals(TipoToken.ESP_ABRE_CHAVES)) {
			analisarComandoBasico();
		}else if(this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_WHILE) || this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_DO)) {
			analisarInteracao();
		}
		else if(this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_IF)) {
			this.token = scanner.scan();
			if(this.token.getToken().equals(TipoToken.ESP_ABRE_PARENTESES)) {
				this.token = scanner.scan();
				analisarExpRelacional();
				if(this.token.getToken().equals(TipoToken.ESP_FECHA_PARENTESES)) {
					this.token = scanner.scan();
					analisarComando();
					if(this.elseAve == false) {
						this.token = scanner.scan();
					}
					if(this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_ELSE)) {
						this.token = scanner.scan();
						analisarComando();
						this.elseAve = false;
					}else {
						this.elseAve = true;
					}
				}else {
					throw new ParserException("ERRO. Esperava-se um ). Encontrou um " + this.token.getLexema(), this.scanner.getLinhaColuna());
				}
			}else {
				throw new ParserException("ERRO. Esperava-se um (. Encontrou um " + this.token.getLexema(), this.scanner.getLinhaColuna());
			}
		}else {
			throw new ParserException("ERRO. Esperava-se um IDENTIFICADOR, ), WHILE, DO ou IF. Encontrou um " + this.token.getLexema(), this.scanner.getLinhaColuna());
		}
	}
	
	public void analisarInteracao() throws IOException, ParserException, ScannerException{
		if(this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_DO)) { //DO
			this.token = scanner.scan();
			analisarComando();
			this.token = scanner.scan();
			if(this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_WHILE)) {
				this.token = scanner.scan();
				if(this.token.getToken().equals(TipoToken.ESP_ABRE_PARENTESES)) {
					this.token = scanner.scan();
					analisarExpRelacional();
					if(this.token.getToken().equals(TipoToken.ESP_FECHA_PARENTESES)){
						this.token = scanner.scan();
						if(!this.token.getToken().equals(TipoToken.ESP_PONTO_E_VIRGULA)) {
							throw new ParserException("ERRO. Esperava-se ; . Encontrou um " + this.token.getLexema(), this.scanner.getLinhaColuna());
						}
					}else {
						throw new ParserException("ERRO. Esperava-se ). Encontrou um " + this.token.getLexema(), this.scanner.getLinhaColuna());
					}
				}else {
					throw new ParserException("ERRO. Esperava-se (. Encontrou um " + this.token.getLexema(), this.scanner.getLinhaColuna());
				}
			}else {
				throw new ParserException("ERRO. Esperava-se WHILE. Encontrou um " + this.token.getLexema(), this.scanner.getLinhaColuna());
			}
		}else if(this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_WHILE)){ //WHILE
			this.token = scanner.scan();
			if(this.token.getToken().equals(TipoToken.ESP_ABRE_PARENTESES)) {
				this.token = scanner.scan();
				analisarExpRelacional();
				if(this.token.getToken().equals(TipoToken.ESP_FECHA_PARENTESES)) {
					this.token = scanner.scan();
					analisarComando();
				}else {
					throw new ParserException("ERRO. Esperava-se ). Encontrou um " + this.token.getLexema(), this.scanner.getLinhaColuna());
				}
			}else {
				throw new ParserException("ERRO. Esperava-se (. Encontrou um " + this.token.getLexema(), this.scanner.getLinhaColuna());
			}
		}else {
			throw new ParserException("ERRO. Esperava-se DO ou WHILE. Encontrou um " + this.token.getLexema(), this.scanner.getLinhaColuna());
		}
	}

	public void analisarBloco() throws IOException, ParserException, ScannerException {
		if(this.token.getToken().equals(TipoToken.ESP_ABRE_CHAVES)) {
			this.token = scanner.scan();
			while(this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_INT) || this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_FLOAT) 
					|| this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_CHAR)){
					analisarDeclaracaoVariavel();
					this.token = scanner.scan();
			}
			while(this.token.getToken().equals(TipoToken.IDENTIFICADOR) || this.token.getToken().equals(TipoToken.ESP_ABRE_CHAVES) 
					|| this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_DO)
					|| this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_WHILE) || this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_IF)) {
				analisarComando();
				if(this.elseAve == false) {
					this.token = scanner.scan();
				}else {
					this.elseAve = false;
				}
			}
			if(!this.token.getToken().equals(TipoToken.ESP_FECHA_CHAVES)) {
				throw new ParserException("ERRO. Esperava-se }. Encontrou um " + this.token.getLexema(), this.scanner.getLinhaColuna());
			}
		}else {
			throw new ParserException("ERRO. Esperava-se {. Encontrou um " + this.token.getLexema(), this.scanner.getLinhaColuna());
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
						analisarBloco();
						analisador.showAll();
						this.token = scanner.scan();
						if (!this.token.getToken().equals(TipoToken.FIM_DE_ARQUIVO)) {
							throw new ParserException("ERRO. Nao pode haver tokens apos o fim do bloco. Encontrou um " + this.token.getLexema(), this.scanner.getLinhaColuna());
						}
					} else {
						throw new ParserException("ERRO. Esperava-se um ). Encontrou um " + this.token.getLexema(), this.scanner.getLinhaColuna());
					}
				} else {
					throw new ParserException("ERRO. Esperava-se um (. Encontrou um " + this.token.getLexema(), this.scanner.getLinhaColuna());
				}
			} else {
				throw new ParserException("ERRO. Esperava-se um MAIN. Encontrou um " + this.token.getLexema(), this.scanner.getLinhaColuna());
			}
		} else {
			throw new ParserException("ERRO. Esperava-se um INT. Encontrou um " + this.token.getLexema(), this.scanner.getLinhaColuna());
		}
		
	}
}