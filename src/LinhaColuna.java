
public class LinhaColuna {
	int linha;
	int coluna;
	
	public LinhaColuna() {
        this.linha = 1; 
        this.coluna = -1;
    }
	
    public int getLinha() {
        return linha;
    }
    
    public void setLinha(int linha) {
        this.linha = linha;
    }
    
    public int getColuna() {
        return coluna;
    }
    
    public void setColuna(int coluna) {
        this.coluna = coluna;
    } 
    
    public void incrementeColuna(int coluna) {
    	this.coluna+=coluna;
    }
    
    public void incrementeLinha(int linha) {
    	this.linha+=linha;
    }
   
}
