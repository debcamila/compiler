
public class ScannerException extends Exception{
	LinhaColuna lc;
	
	private final String mensagem;
		
	public ScannerException (String message, LinhaColuna linhaColuna) {
		super(message);
		this.lc = linhaColuna;
		this.mensagem = message;
	}
	
	@Override
	public String toString() {
		return this.mensagem+ " Linha: " +lc.getLinha()+ " Coluna: " +lc.getColuna();
	}	
}
