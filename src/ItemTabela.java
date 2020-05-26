
public class ItemTabela {
	private TipoToken token;
	private int escopo;
	private String lexema;
	
	public ItemTabela(TipoToken token, int escopo, String lexema) {
		this.token = token;
		this.escopo = escopo;
		this.lexema = lexema;
	}

	public TipoToken getToken() {
		return token;
	}

	public void setToken(TipoToken token) {
		this.token = token;
	}

	public int getEscopo() {
		return escopo;
	}

	public void setEscopo(int escopo) {
		this.escopo = escopo;
	}

	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}

	@Override
	public String toString() {
		return "ItemTabela [token=" + token + ", escopo=" + escopo + ", lexema=" + lexema + "]";
	}
	
}
