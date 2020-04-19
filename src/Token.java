
public class Token {
	private TipoToken tipoToken;
	private String lexema;
	private LinhaColuna linhaColuna;
	
	public Token (TipoToken t, String l, LinhaColuna linhaColuna) { //construtor
		this.tipoToken = t;
		this.lexema = l;
		this.linhaColuna = linhaColuna;
	}
	
	public void setToken(TipoToken t) {
		this.tipoToken = t;
	}
	
	public TipoToken getToken() {
		return tipoToken;
	}
	
	public void setLexema(String l) {
		this.lexema = l;
	}
	
	public String getLexema() {
		return lexema;
	}

	@Override
	public String toString() {
		return "Linha: " +this.linhaColuna.getLinha() + " Coluna: "+this.linhaColuna.getColuna();
	}
}
