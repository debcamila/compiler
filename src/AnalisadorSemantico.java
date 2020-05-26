import java.util.ArrayList;

public class AnalisadorSemantico {
	private ArrayList<ItemTabela> tabela = new ArrayList<>();
	private int escopo = 0;
	
	public AnalisadorSemantico () {}
	
	public void adicionarItemTabela(TipoToken token, String lexema, Scanner scanner) throws ParserException, ScannerException {
		if(validaEscopo(lexema)) {
			ItemTabela novoItem = new ItemTabela(token, this.escopo, lexema);
			tabela.add(novoItem);
		}else {
			throw new ParserException("ERRO. Nao poderia existir variaveis com nomes iguais no mesmo escopo.  ",scanner.getLinhaColuna());
		}
	}
	
	public void showAll() {
		System.out.println("todos");
		tabela.forEach((e) -> System.out.println(e.toString()));
	}
	
	public boolean validaEscopo(String lexema) throws ParserException, ScannerException{
		for (ItemTabela item : tabela) {
			if(item.getLexema().equals(lexema)) {
				return false;
			}
		}
		return true;
	}
	
	public static void removerEscopo(ItemTabela tabela, int escopo) {}
	
	public static Token verificaSeDeclarada(ItemTabela tabela, Token token) throws ParserException, ScannerException{}
	
	public static void verificaExpRelacional(Token token, int registrador, Token relacional) throws ParserException, ScannerException{}
	
	public static boolean isInt(String input) {
		boolean result = true;
		try {
			Integer.parseInt(input);
		}catch (NumberFormatException e) {
			result = false;
		}
		return result;
	}
	
	public static boolean isFloat(String input) {
		boolean result = true;
		try {
			Float.parseFloat(input);
		}catch (NumberFormatException e) {
			result = false;
		}
		return result;
	}
	
}

// { adicionar novo escopo
// } remover o último escopo
//testes para o tipo
