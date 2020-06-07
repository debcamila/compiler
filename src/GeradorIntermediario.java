import java.util.HashMap;
import java.util.Map;

public class GeradorIntermediario {
	private int jumpTemporario = 0;
	private int varTemporaria = 0;
	
	Map<String,String> tabelaSimbolos = new HashMap<String,String>();
	
	public GeradorIntermediario() {}
	
	
	public String getNewJump() { //retorna novo label para jump temporario
		this.jumpTemporario++;
		return "L"+this.jumpTemporario;
	}
	
	public String getNewVar() { //retorna novo label para var temporaria
		this.varTemporaria++;
		return "T"+this.varTemporaria;
	}
	
	public String getTemporaria (String var) {
		if (tabelaSimbolos.containsKey(var)) {
			return tabelaSimbolos.get(var);
		}else {
			String newVar = this.getNewVar();
			tabelaSimbolos.put(var, newVar);
			return newVar;
		}
	}
}
