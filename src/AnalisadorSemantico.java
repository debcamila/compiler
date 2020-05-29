import java.util.ArrayList;

public class AnalisadorSemantico {
	private ArrayList<ItemTabela> tabela = new ArrayList<>();
	private int escopo = 0;
	
	public AnalisadorSemantico () {}
	
	public void adicionarItemTabela(TipoToken token, String lexema, Scanner scanner) throws ParserException, ScannerException {
		if(validaEscopo(lexema)) {
			TipoToken ultimo = null;
			if(token.equals(TipoToken.PALAVRA_RESERVADA_CHAR)) {
				ultimo = TipoToken.VALOR_CHAR;
			}else if(token.equals(TipoToken.PALAVRA_RESERVADA_INT)) {
				ultimo = TipoToken.VALOR_INT;
			}else if(token.equals(TipoToken.PALAVRA_RESERVADA_FLOAT)) {
				ultimo = TipoToken.VALOR_FLOAT;
			}
			ItemTabela novoItem = new ItemTabela(ultimo, this.escopo, lexema);
			tabela.add(novoItem);
		}else {
			throw new ParserException("ERRO. Nao poderia existir variaveis com nomes iguais no mesmo escopo.",scanner.getLinhaColuna());
		}
	}
	
	public void showAll() {
		tabela.forEach((e) -> System.out.println(e.toString()));
	}
	
	public boolean validaEscopo(String lexema) throws ParserException, ScannerException{
		for (ItemTabela item : tabela) {
			if (item.getEscopo() == escopo) {
				if(item.getLexema().equals(lexema)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void adicionarEscopo() {
		this.escopo++;
	}
	
	public void removerEscopo() {
		this.tabela.removeIf(i -> {
			return i.getEscopo() == this.escopo;
		});
		this.escopo--;
	}
	
	public TipoToken retornaTipo(String lexema, Scanner scanner) throws ParserException {
		for(ItemTabela item : tabela) {
			if(item.getLexema().equals(lexema) && item.getEscopo() <= this.escopo) {
				return item.getToken();
			}
		}
		throw new ParserException("ERRO. Variavel nao inicializada.",scanner.getLinhaColuna());
	}	
}
