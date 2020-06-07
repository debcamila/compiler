import java.io.IOException;
import java.util.ArrayList;

public class Parser {
	private Boolean elseAve = false;
	private Scanner scanner;
	private Token token;
	private AnalisadorSemantico analisador = new AnalisadorSemantico();
	private GeradorIntermediario gerador = new GeradorIntermediario();

	public Parser(String arquivo) throws IOException, ParserException, ScannerException {
		this.scanner = new Scanner(arquivo);
		this.token = this.scanner.scan();
	}

	public Token analisarFator() throws IOException, ParserException, ScannerException {

		if (this.token.getToken().equals(TipoToken.IDENTIFICADOR)) {
			Token temp = this.token;
			temp.setToken(analisador.retornaTipo(this.token.getLexema(), this.scanner));
			return temp;

		} else if (this.token.getToken().equals(TipoToken.VALOR_FLOAT)) {
			return this.token;

		} else if (this.token.getToken().equals(TipoToken.VALOR_INT)) {
			return this.token;

		} else if (this.token.getToken().equals(TipoToken.VALOR_CHAR)) {
			return this.token;

		} else if (this.token.getToken().equals(TipoToken.ESP_ABRE_PARENTESES)) {
			this.token = this.scanner.scan();
			Token valorFinal = analisarExpressao();

			if (this.token.getToken().equals(TipoToken.ESP_FECHA_PARENTESES)) {
				return valorFinal;
			} else {
				throw new ParserException("ERRO. Esperava-se um ). Encontrou um " + this.token.getLexema(),
						this.scanner.getLinhaColuna());
			}
		}
		throw new ParserException(
				"ERRO. Esperava-se um valor INT, FLOAT ou CHAR. Encontrou um " + this.token.getLexema(),
				this.scanner.getLinhaColuna());
	}

	public Token analisarTermo() throws IOException, ParserException, ScannerException {
		Token primeiroFator = analisarFator();
		String operacaoValor = "";
		this.token = scanner.scan();

		while (this.token.getToken().equals(TipoToken.OP_ARITMETICO_MULTIPLICACAO)|| this.token.getToken().equals(TipoToken.OP_ARITMETICO_DIVISAO)) {
			operacaoValor = this.token.getLexema();
			TipoToken operacao = this.token.getToken();
			this.token = scanner.scan();
			Token segundoFator = analisarFator();

			if (primeiroFator.getToken().equals(TipoToken.VALOR_FLOAT) && segundoFator.getToken().equals(TipoToken.VALOR_INT) && operacao.equals(TipoToken.OP_ARITMETICO_MULTIPLICACAO)) {
				String varTemp = gerador.getNewVar();
				System.out.println(varTemp + "=(float)" + segundoFator.getLexema());
				String varTempDois = gerador.getNewVar();
				System.out.println(varTempDois + "=" + primeiroFator.getLexema() + "*" + varTemp);
				primeiroFator.setToken(TipoToken.VALOR_FLOAT);
				primeiroFator.setLexema(varTempDois);

			} else if (primeiroFator.getToken().equals(TipoToken.VALOR_FLOAT) && segundoFator.getToken().equals(TipoToken.VALOR_INT) && operacao.equals(TipoToken.OP_ARITMETICO_DIVISAO)) {
				String varTemp = gerador.getNewVar();
				System.out.println(varTemp + "=(float)" + segundoFator.getLexema());
				String varTempDois = gerador.getNewVar();
				System.out.println(varTempDois + "=" + primeiroFator.getLexema() + "/" + varTemp);
				primeiroFator.setToken(TipoToken.VALOR_FLOAT);
				primeiroFator.setLexema(varTempDois);

			} else if (primeiroFator.getToken().equals(TipoToken.VALOR_INT) && segundoFator.getToken().equals(TipoToken.VALOR_FLOAT) && operacao.equals(TipoToken.OP_ARITMETICO_MULTIPLICACAO)) {
				String varTemp = gerador.getNewVar();
				System.out.println(varTemp + "=(float)" + primeiroFator.getLexema());
				String varTempDois = gerador.getNewVar();
				System.out.println(varTempDois + "=" + varTemp + "*" + segundoFator.getLexema());
				primeiroFator.setToken(TipoToken.VALOR_FLOAT);
				primeiroFator.setLexema(varTempDois);

			} else if (primeiroFator.getToken().equals(TipoToken.VALOR_INT) && segundoFator.getToken().equals(TipoToken.VALOR_FLOAT) && operacao.equals(TipoToken.OP_ARITMETICO_DIVISAO)) {
				String varTemp = gerador.getNewVar();
				System.out.println(varTemp + "=(float)" + primeiroFator.getLexema());
				String varTempDois = gerador.getNewVar();
				System.out.println(varTempDois + "=" + varTemp + "/" + segundoFator.getLexema());
				primeiroFator.setToken(TipoToken.VALOR_FLOAT);
				primeiroFator.setLexema(varTempDois);

			} else if (primeiroFator.getToken().equals(TipoToken.VALOR_INT) && segundoFator.getToken().equals(TipoToken.VALOR_INT) && operacao.equals(TipoToken.OP_ARITMETICO_MULTIPLICACAO)) {
				String tempVar = gerador.getNewVar();
				System.out.println(tempVar + "=" + primeiroFator.getLexema() + operacaoValor + segundoFator.getLexema());
				primeiroFator.setToken(TipoToken.VALOR_INT);
				primeiroFator.setLexema(tempVar);

			} else if (primeiroFator.getToken().equals(TipoToken.VALOR_INT) && segundoFator.getToken().equals(TipoToken.VALOR_INT) && operacao.equals(TipoToken.OP_ARITMETICO_DIVISAO)) {
				String tempVar = gerador.getNewVar();
				System.out.println(tempVar + "=" + primeiroFator.getLexema() + operacaoValor + segundoFator.getLexema());
				primeiroFator.setToken(TipoToken.VALOR_FLOAT);
				primeiroFator.setLexema(tempVar);

			} else if (primeiroFator.getToken().equals(TipoToken.VALOR_FLOAT) && segundoFator.getToken().equals(TipoToken.VALOR_FLOAT) && operacao.equals(TipoToken.OP_ARITMETICO_DIVISAO)) {
				String tempVar = gerador.getNewVar();
				System.out.println(tempVar + "=" + primeiroFator.getLexema() + operacaoValor + segundoFator.getLexema());
				primeiroFator.setToken(TipoToken.VALOR_FLOAT);
				primeiroFator.setLexema(tempVar);

			} else if (primeiroFator.getToken().equals(TipoToken.VALOR_FLOAT) && segundoFator.getToken().equals(TipoToken.VALOR_FLOAT) && operacao.equals(TipoToken.OP_ARITMETICO_MULTIPLICACAO)) {
				String tempVar = gerador.getNewVar();
				System.out.println(tempVar + "=" + primeiroFator.getLexema() + operacaoValor + segundoFator.getLexema());
				primeiroFator.setToken(TipoToken.VALOR_FLOAT);
				primeiroFator.setLexema(tempVar);

			} else if (primeiroFator.getToken().equals(TipoToken.VALOR_CHAR) && segundoFator.getToken().equals(TipoToken.VALOR_CHAR) && operacao.equals(TipoToken.OP_ARITMETICO_MULTIPLICACAO)) {
				String tempVar = gerador.getNewVar();
				System.out.println(tempVar + "=" + primeiroFator.getLexema() + operacaoValor + segundoFator.getLexema());
				primeiroFator.setToken(TipoToken.VALOR_CHAR);
				primeiroFator.setLexema(tempVar);

			} else if (primeiroFator.getToken().equals(TipoToken.VALOR_CHAR) && segundoFator.getToken().equals(TipoToken.VALOR_CHAR) && operacao.equals(TipoToken.OP_ARITMETICO_DIVISAO)) {
				String tempVar = gerador.getNewVar();
				System.out.println(tempVar + "=" + primeiroFator.getLexema() + operacaoValor + segundoFator.getLexema());
				primeiroFator.setToken(TipoToken.VALOR_CHAR);
				primeiroFator.setLexema(tempVar);
			} else {
				throw new ParserException("ERRO. Nao eh possivel realizar a operacao entre: " + primeiroFator + " e: " + segundoFator,this.scanner.getLinhaColuna());
			}
			this.token = scanner.scan();
		}
		return primeiroFator;
	}

	public Token analisarExpressao() throws IOException, ParserException, ScannerException {
		Token primeiroFator = analisarTermo();
		primeiroFator = analisarExpressaoAritmetrica(primeiroFator);
		return primeiroFator;
	}

	public Token analisarExpressaoAritmetrica(Token primeiroFator)throws IOException, ParserException, ScannerException {
		Token operacao = this.token;
		
		if (this.token.getToken().equals(TipoToken.OP_ARITMETICO_MAIS)|| (this.token.getToken().equals(TipoToken.OP_ARITMETICO_MENOS))) {
			this.token = scanner.scan();
			Token segundoFator = analisarTermo();
			
			if (primeiroFator.getToken().equals(TipoToken.VALOR_FLOAT) && segundoFator.getToken().equals(TipoToken.VALOR_INT)) {
				String varTempInicial = gerador.getNewVar();
				System.out.println(varTempInicial+"=(float)"+segundoFator.getLexema());
				String varTemp = gerador.getNewVar();
				System.out.println(varTemp+"="+primeiroFator.getLexema()+operacao.getLexema()+varTempInicial);
				primeiroFator.setToken(TipoToken.VALOR_FLOAT); 
				primeiroFator.setLexema(varTemp);
				
			} else if (primeiroFator.getToken().equals(TipoToken.VALOR_INT) && segundoFator.getToken().equals(TipoToken.VALOR_FLOAT)) {
				String varTempInicial = gerador.getNewVar();
				System.out.println(varTempInicial+"=(float)"+primeiroFator.getLexema());
				String varTemp = gerador.getNewVar();
				System.out.println(varTemp+"="+varTempInicial+operacao.getLexema()+segundoFator.getLexema());
				primeiroFator.setToken(TipoToken.VALOR_FLOAT); 
				primeiroFator.setLexema(varTemp);
				
			} else if (primeiroFator.getToken().equals(TipoToken.VALOR_INT) && segundoFator.getToken().equals(TipoToken.VALOR_INT)) {
				String varTemp = gerador.getNewVar();
				System.out.println(varTemp+"="+primeiroFator.getLexema()+operacao.getLexema()+segundoFator.getLexema());
				primeiroFator.setToken(TipoToken.VALOR_INT); 
				primeiroFator.setLexema(varTemp);
				
			} else if (primeiroFator.getToken().equals(TipoToken.VALOR_FLOAT) && segundoFator.getToken().equals(TipoToken.VALOR_FLOAT)) {
				String varTemp = gerador.getNewVar();
				System.out.println(varTemp+"="+primeiroFator.getLexema()+operacao.getLexema()+segundoFator.getLexema());
				primeiroFator.setToken(TipoToken.VALOR_FLOAT); 
				primeiroFator.setLexema(varTemp);
				
			} else if (primeiroFator.getToken().equals(TipoToken.VALOR_CHAR) && segundoFator.getToken().equals(TipoToken.VALOR_CHAR)) {
				String varTemp = gerador.getNewVar();
				System.out.println(varTemp+"="+primeiroFator.getLexema()+operacao.getLexema()+segundoFator.getLexema());
				primeiroFator.setToken(TipoToken.VALOR_CHAR); 
				primeiroFator.setLexema(varTemp);
				
			} else {
				throw new ParserException("ERRO. Nao eh possivel realizar a operacao entre: " + primeiroFator + " e: " + segundoFator,this.scanner.getLinhaColuna());
			}
			return analisarExpressaoAritmetrica(primeiroFator);
		}
		return primeiroFator;
	}

	public Token analisarOpRelacional() throws IOException, ParserException, ScannerException {
		if (this.token.getToken().equals(TipoToken.OP_RELACIONAL_MAIOR_IGUAL)) {
			return this.token;
			
		} else if (this.token.getToken().equals(TipoToken.OP_RELACIONAL_MENOR_IGUAL)) {
			return this.token;
			
		} else if (this.token.getToken().equals(TipoToken.OP_RELACIONAL_MAIOR)) {
			return this.token;
			
		} else if (this.token.getToken().equals(TipoToken.OP_RELACIONAL_MENOR)) {
			return this.token;
			
		} else if (this.token.getToken().equals(TipoToken.OP_RELACIONAL_DIFERENTE)) {
			return this.token;
			
		} else if (this.token.getToken().equals(TipoToken.OP_RELACIONAL_COMPARACAO)) {
			return this.token;
		}
		throw new ParserException("ERRO. Esperava-se um operador relacional. Encontrou um " + this.token.getLexema(),this.scanner.getLinhaColuna());
	}

	public Token analisarExpRelacional() throws IOException, ParserException, ScannerException {
		Token primeiroFator = analisarExpressao();
		Token relacional = analisarOpRelacional();
		this.token = scanner.scan();
		Token segundoFator = analisarExpressao();
		
		if (primeiroFator.getToken().equals(TipoToken.VALOR_FLOAT) && segundoFator.getToken().equals(TipoToken.VALOR_INT)) {
			String varInicial = gerador.getNewVar();
			System.out.println(varInicial+"=(float)"+segundoFator.getLexema());
			String varTemp = gerador.getNewVar();
			System.out.println(varTemp+"="+primeiroFator.getLexema()+relacional.getLexema()+varInicial);
			primeiroFator.setLexema(varTemp);
			return primeiroFator;
			
		} else if (primeiroFator.getToken().equals(TipoToken.VALOR_INT) && segundoFator.getToken().equals(TipoToken.VALOR_FLOAT)) {
			String varInicial = gerador.getNewVar();
			System.out.println(varInicial+"=(float)"+primeiroFator.getLexema());
			String varTemp = gerador.getNewVar();
			System.out.println(varTemp+"="+varInicial+relacional.getLexema()+segundoFator.getLexema());
			primeiroFator.setLexema(varTemp);
			return primeiroFator;
			
		} else if (primeiroFator.getToken().equals(TipoToken.VALOR_INT) && segundoFator.getToken().equals(TipoToken.VALOR_INT)) {
			String varTemp = gerador.getNewVar();
			System.out.println(varTemp+"="+primeiroFator.getLexema()+relacional.getLexema()+segundoFator.getLexema());
			primeiroFator.setLexema(varTemp);
			return primeiroFator;
			
		} else if (primeiroFator.getToken().equals(TipoToken.VALOR_FLOAT) && segundoFator.getToken().equals(TipoToken.VALOR_FLOAT)) {
			String varTemp = gerador.getNewVar();
			System.out.println(varTemp+"="+primeiroFator.getLexema()+relacional.getLexema()+segundoFator.getLexema());
			primeiroFator.setLexema(varTemp);
			return primeiroFator;
			
		} else if (primeiroFator.getToken().equals(TipoToken.VALOR_CHAR) && segundoFator.getToken().equals(TipoToken.VALOR_CHAR)) {
			String varTemp = gerador.getNewVar();
			System.out.println(varTemp+"="+primeiroFator.getLexema()+relacional.getLexema()+segundoFator.getLexema());
			primeiroFator.setLexema(varTemp);
			return primeiroFator;
			
		} else {
			throw new ParserException("ERRO. Nao eh possivel realizar a operacao entre: " + primeiroFator + " e: " + segundoFator, this.scanner.getLinhaColuna());
		}
	}

	public Boolean analisarAtribuicao() throws IOException, ParserException, ScannerException {
		if (this.token.getToken().equals(TipoToken.IDENTIFICADOR)) {
			
			Token primeiroFator = this.token;
			primeiroFator.setToken(analisador.retornaTipo(this.token.getLexema(), this.scanner));
			this.token = scanner.scan();

			if (this.token.getToken().equals(TipoToken.OP_ARITMETICO_IGUAL)) {
				this.token = scanner.scan();
				Token segundoFator = analisarExpressao();

				if (primeiroFator.getToken().equals(segundoFator.getToken())|| (primeiroFator.getToken().equals(TipoToken.VALOR_FLOAT) && segundoFator.getToken().equals(TipoToken.VALOR_INT))) {
					
					if (this.token.getToken().equals(TipoToken.ESP_PONTO_E_VIRGULA)) {

						if (primeiroFator.getToken().equals(TipoToken.VALOR_FLOAT) && segundoFator.getToken().equals(TipoToken.VALOR_INT)) {
							String varTemp = gerador.getNewVar();
							System.out.println(varTemp + "= (float)" + segundoFator.getLexema());
							System.out.println(primeiroFator.getLexema() + "=" + varTemp);
							return true;
							
						} else {
							System.out.println(primeiroFator.getLexema() + "=" + segundoFator.getLexema());
							return true;
						}

					} else {
						throw new ParserException("ERRO. Esperava-se ;. Encontrou um " + this.token.getLexema(),this.scanner.getLinhaColuna());
					}
				} else {
					throw new ParserException("ERRO. Tipo invalido. Esperava-se " + primeiroFator.getToken() + " Encontrou um " + segundoFator.getToken(),this.scanner.getLinhaColuna());
				}
			} else {
				throw new ParserException("ERRO. Esperava-se =. Encontrou um " + this.token.getLexema(),this.scanner.getLinhaColuna());
			}
		} else {
			throw new ParserException("ERRO. Esperava-se um identificador. Encontrou um " + this.token.getLexema(),this.scanner.getLinhaColuna());
		}
	}

	public Boolean analisarDeclaracaoVariavel() throws IOException, ParserException, ScannerException {
		TipoToken varTipo = this.token.getToken();
		if (analisarTipoDado()) {
			this.token = scanner.scan();
			ArrayList<String> varName = new ArrayList<>();
			if (this.token.getToken().equals(TipoToken.IDENTIFICADOR)) {
				varName.add(this.token.getLexema());
				this.token = scanner.scan();
				while (this.token.getToken().equals(TipoToken.ESP_VIRGULA)) {
					this.token = scanner.scan();
					if (!this.token.getToken().equals(TipoToken.IDENTIFICADOR)) {
						throw new ParserException(
								"ERRO. Esperava-se um identificador. Encontrou um " + this.token.getLexema(),
								this.scanner.getLinhaColuna());
					}
					varName.add(this.token.getLexema());
					this.token = scanner.scan();
				}
				if (this.token.getToken().equals(TipoToken.ESP_PONTO_E_VIRGULA)) {
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
				} else {
					throw new ParserException("ERRO. Esperava-se ;. Encontrou um " + this.token.getLexema(),
							this.scanner.getLinhaColuna());
				}
			}
		}
		return false;
	}

	public Boolean analisarTipoDado() throws IOException, ParserException, ScannerException {
		if (this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_INT)) {
			return true;
		} else if (this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_FLOAT)) {
			return true;
		} else if (this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_CHAR)) {
			return true;
		} else {
			throw new ParserException("ERRO. Esperava-se INT, FLOAT ou CHAR. Encontrou um " + this.token.getLexema(),
					this.scanner.getLinhaColuna());
		}
	}

	public void analisarComandoBasico() throws IOException, ParserException, ScannerException {
		if (this.token.getToken().equals(TipoToken.IDENTIFICADOR)) {
			analisarAtribuicao();
		} else if (this.token.getToken().equals(TipoToken.ESP_ABRE_CHAVES)) {
			analisarBloco();
		}
	}

	public void analisarComando() throws IOException, ParserException, ScannerException {
		if (this.token.getToken().equals(TipoToken.IDENTIFICADOR)|| this.token.getToken().equals(TipoToken.ESP_ABRE_CHAVES)) {
			analisarComandoBasico();
			
		} else if (this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_WHILE)|| this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_DO)) {
			analisarInteracao();
			
		} else if (this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_IF)) {
			this.token = scanner.scan();
			
			if (this.token.getToken().equals(TipoToken.ESP_ABRE_PARENTESES)) {
				this.token = scanner.scan();
				Token caso = analisarExpRelacional();
				
				if (this.token.getToken().equals(TipoToken.ESP_FECHA_PARENTESES)) {
					String pointerUm = gerador.getNewJump();
					System.out.println("IF(!"+caso.getLexema()+") GOTO " +pointerUm);  
					this.token = scanner.scan();
					analisarComando();
					
					if (this.elseAve == false) {
						this.token = scanner.scan();
					}
					
					if (this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_ELSE)) {
						String pointerDois = gerador.getNewJump();
						System.out.println("GOTO " +pointerDois);
						System.out.println(pointerUm+": ");
						this.token = scanner.scan();
						analisarComando();
						System.out.println(pointerDois+": ");
						this.elseAve = false;
						
					} else {
						System.out.println(pointerUm+": ");
						this.elseAve = true;
					}
				} else {
					throw new ParserException("ERRO. Esperava-se um ). Encontrou um " + this.token.getLexema(),this.scanner.getLinhaColuna());
				}
			} else {
				throw new ParserException("ERRO. Esperava-se um (. Encontrou um " + this.token.getLexema(),this.scanner.getLinhaColuna());
			}
		} else {
			throw new ParserException(
					"ERRO. Esperava-se um IDENTIFICADOR, ), WHILE, DO ou IF. Encontrou um " + this.token.getLexema(),
					this.scanner.getLinhaColuna());
		}
	}

	public void analisarInteracao() throws IOException, ParserException, ScannerException {
		if (this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_DO)) { // DO
			this.token = scanner.scan();
			String jumpUm = gerador.getNewJump();
			System.out.println(jumpUm+": ");
			analisarComando();
			this.token = scanner.scan();
			
			if (this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_WHILE)) {
				this.token = scanner.scan();
				
				if (this.token.getToken().equals(TipoToken.ESP_ABRE_PARENTESES)) {
					this.token = scanner.scan();
					Token relacional = analisarExpRelacional();
					
					if (this.token.getToken().equals(TipoToken.ESP_FECHA_PARENTESES)) {
						this.token = scanner.scan();
						
						if (!this.token.getToken().equals(TipoToken.ESP_PONTO_E_VIRGULA)) {
							throw new ParserException("ERRO. Esperava-se ; . Encontrou um " + this.token.getLexema(),
									this.scanner.getLinhaColuna());
						}
						String jumpDois = gerador.getNewJump();
						System.out.println("IF(!"+relacional.getLexema()+") GOTO "+jumpDois);
						System.out.println("GOTO "+jumpUm);
						System.out.println(jumpDois+":");
					} else {
						throw new ParserException("ERRO. Esperava-se ). Encontrou um " + this.token.getLexema(),
								this.scanner.getLinhaColuna());
					}
				} else {
					throw new ParserException("ERRO. Esperava-se (. Encontrou um " + this.token.getLexema(),
							this.scanner.getLinhaColuna());
				}
			} else {
				throw new ParserException("ERRO. Esperava-se WHILE. Encontrou um " + this.token.getLexema(),
						this.scanner.getLinhaColuna());
			}
		} else if (this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_WHILE)) { // WHILE
			this.token = scanner.scan();
			
			if (this.token.getToken().equals(TipoToken.ESP_ABRE_PARENTESES)) {
				this.token = scanner.scan();
				Token relacional = analisarExpRelacional();
				
				if (this.token.getToken().equals(TipoToken.ESP_FECHA_PARENTESES)) {
					String jumpUm = gerador.getNewJump();
					String jumpDois = gerador.getNewJump();
					System.out.println("IF(!" +relacional.getLexema()+") GOTO "+jumpDois);
					System.out.println(jumpUm+": ");
					this.token = scanner.scan();
					analisarComando();
					System.out.println("IF(!" +relacional.getLexema()+") GOTO "+jumpDois);
					System.out.println("GOTO "+jumpUm);
					System.out.println(jumpDois+": ");
					
				} else {
					throw new ParserException("ERRO. Esperava-se ). Encontrou um " + this.token.getLexema(),
							this.scanner.getLinhaColuna());
				}
			} else {
				throw new ParserException("ERRO. Esperava-se (. Encontrou um " + this.token.getLexema(),
						this.scanner.getLinhaColuna());
			}
		} else {
			throw new ParserException("ERRO. Esperava-se DO ou WHILE. Encontrou um " + this.token.getLexema(),this.scanner.getLinhaColuna());
		}
	}

	public void analisarBloco() throws IOException, ParserException, ScannerException {
		if (this.token.getToken().equals(TipoToken.ESP_ABRE_CHAVES)) {
			analisador.adicionarEscopo();
			this.token = scanner.scan();
			while (this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_INT)
					|| this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_FLOAT)
					|| this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_CHAR)) {
				analisarDeclaracaoVariavel();
				this.token = scanner.scan();
			}
			while (this.token.getToken().equals(TipoToken.IDENTIFICADOR)
					|| this.token.getToken().equals(TipoToken.ESP_ABRE_CHAVES)
					|| this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_DO)
					|| this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_WHILE)
					|| this.token.getToken().equals(TipoToken.PALAVRA_RESERVADA_IF)) {
				analisarComando();
				if (this.elseAve == false) {
					this.token = scanner.scan();
				} else {
					this.elseAve = false;
				}
			}
			if (!this.token.getToken().equals(TipoToken.ESP_FECHA_CHAVES)) {
				throw new ParserException("ERRO. Esperava-se }. Encontrou um " + this.token.getLexema(),
						this.scanner.getLinhaColuna());
			}
			analisador.removerEscopo();
		} else {
			throw new ParserException("ERRO. Esperava-se {. Encontrou um " + this.token.getLexema(),
					this.scanner.getLinhaColuna());
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
							throw new ParserException("ERRO. Nao pode haver tokens apos o fim do bloco. Encontrou um "
									+ this.token.getLexema(), this.scanner.getLinhaColuna());
						}
					} else {
						throw new ParserException("ERRO. Esperava-se um ). Encontrou um " + this.token.getLexema(),
								this.scanner.getLinhaColuna());
					}
				} else {
					throw new ParserException("ERRO. Esperava-se um (. Encontrou um " + this.token.getLexema(),
							this.scanner.getLinhaColuna());
				}
			} else {
				throw new ParserException("ERRO. Esperava-se um MAIN. Encontrou um " + this.token.getLexema(),
						this.scanner.getLinhaColuna());
			}
		} else {
			throw new ParserException("ERRO. Esperava-se um INT. Encontrou um " + this.token.getLexema(),
					this.scanner.getLinhaColuna());
		}

	}
}