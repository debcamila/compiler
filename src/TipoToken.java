import java.io.IOException;

public enum TipoToken {
	//TIPOS DE DADOS 
	VALOR_INT,
	VALOR_FLOAT,
	VALOR_CHAR,
	
	IDENTIFICADOR,
	FIM_DE_ARQUIVO,
	
	//ESPECIAL
    ESP_FECHA_PARENTESES,
    ESP_ABRE_PARENTESES,
    ESP_ABRE_COLCHETES,
    ESP_FECHA_COLCHETES,
    ESP_ABRE_CHAVES,
    ESP_FECHA_CHAVES,
    ESP_VIRGULA,
    ESP_PONTO_E_VIRGULA,
    
    //OPERADORES ARITMÉTICOS
    OP_ARITMETICO_MENOS, 
    OP_ARITMETICO_MAIS, 
    OP_ARITMETICO_MULTIPLICACAO, 
    OP_ARITMETICO_DIVISAO,
    OP_ARITMETICO_IGUAL,
    
    //OPERADORES RELACIONAIS
    OP_RELACIONAL_MENOR,
    OP_RELACIONAL_MAIOR,
    OP_RELACIONAL_MENOR_IGUAL,
    OP_RELACIONAL_MAIOR_IGUAL,
    OP_RELACIONAL_COMPARACAO, // ==
    OP_RELACIONAL_DIFERENTE, //!=
    
    //PALAVRAS RESERVADAS
    PALAVRA_RESERVADA_IF, 
    PALAVRA_RESERVADA_ELSE, 
    PALAVRA_RESERVADA_WHILE,
    PALAVRA_RESERVADA_DO,
    PALAVRA_RESERVADA_FOR,
    PALAVRA_RESERVADA_INT, 
    PALAVRA_RESERVADA_FLOAT, 
    PALAVRA_RESERVADA_CHAR,
    PALAVRA_RESERVADA_MAIN;
	
	//VERIFICACAO
    public static Boolean verificaTipo(TipoToken t) {
		return t.equals(TipoToken.VALOR_INT) || t.equals(TipoToken.VALOR_FLOAT) || t.equals(TipoToken.VALOR_CHAR) || t.equals(TipoToken.IDENTIFICADOR);
	}
    
    public static TipoToken verificaEspeciais(char c) {
    	String lexema = "" + c;
    	switch(lexema) {
    	case "(":
    		return TipoToken.ESP_ABRE_COLCHETES;
    	case ")":
    		return TipoToken.ESP_FECHA_PARENTESES;
    	case "{":
    		return TipoToken.ESP_ABRE_COLCHETES;
    	case "}":
    		return TipoToken.ESP_FECHA_COLCHETES;
    	case ",":
    		return TipoToken.ESP_VIRGULA;
    	case ";": 
    		return TipoToken.ESP_PONTO_E_VIRGULA;
    	}
    	return null;
    }
    
    public static TipoToken verificaOperadores(char c) {
    	String lexema = "" + c;
    	switch(lexema) {
    	case "+":
    		return TipoToken.OP_ARITMETICO_MAIS;
    	case "-":
    		return TipoToken.OP_ARITMETICO_MENOS;
    	case "*":
    		return TipoToken.OP_ARITMETICO_MULTIPLICACAO;
    	case "/":
    		return TipoToken.OP_ARITMETICO_DIVISAO;
    	case "=":
    		return TipoToken.OP_ARITMETICO_IGUAL;
    	}
    	return null;
    }

    public static TipoToken verificaRelacionais(int atual, int lookAhead) throws ScannerException, IOException {
    	String lexema = "" + (char) atual;
    	switch(lexema) {
    	case ">":
    		if((char) lookAhead == '=') {
    			
    			return TipoToken.OP_RELACIONAL_MAIOR_IGUAL;
    		}else {
    			return TipoToken.OP_RELACIONAL_MAIOR;
    		}
    	case "<":
    		if((char) lookAhead == '=') {
    			return TipoToken.OP_RELACIONAL_MENOR_IGUAL;
    		}else {
    			return TipoToken.OP_RELACIONAL_MENOR;
    		}    		
    	case "!":
    		if((char) lookAhead == '=') {
    			return TipoToken.OP_RELACIONAL_DIFERENTE;
    		}
    	case "=":
    		if((char) lookAhead == '=') {
    			return TipoToken.OP_RELACIONAL_COMPARACAO;
    		}
    	}
    	return null;
    }
    
    
    
    public static Boolean verificaRelacional(TipoToken t) {
    	return t.equals(TipoToken.OP_RELACIONAL_MENOR) || t.equals(TipoToken.OP_RELACIONAL_MAIOR) || t.equals(TipoToken.OP_RELACIONAL_MENOR_IGUAL) ||
    			t.equals(TipoToken.OP_RELACIONAL_MAIOR_IGUAL) || t.equals(TipoToken.OP_RELACIONAL_DIFERENTE) || t.equals(TipoToken.OP_RELACIONAL_COMPARACAO);
    }
    
    public static TipoToken verificaPalavrasReservadas(String s) {
    	switch(s) {
    	case "int":
    		return TipoToken.PALAVRA_RESERVADA_INT;
    	case "float":
    		return TipoToken.PALAVRA_RESERVADA_FLOAT;
    	case "char":
    		return TipoToken.PALAVRA_RESERVADA_CHAR;
    	case "main":
    		return TipoToken.PALAVRA_RESERVADA_MAIN;
    	case "if":
    		return TipoToken.PALAVRA_RESERVADA_MAIN;
    	case "else":
    		return TipoToken.PALAVRA_RESERVADA_ELSE;
    	case "while":
    		return TipoToken.PALAVRA_RESERVADA_WHILE;
    	case "do":
    		return TipoToken.PALAVRA_RESERVADA_DO;
    	case "for":
    		return TipoToken.PALAVRA_RESERVADA_FOR;
    	}
    	return null;
    }
    
    //CONVERSAO
    public static String converteOperadores(TipoToken t) {
    	switch(t) {
    	case OP_ARITMETICO_MAIS:
    		return "+";
    	case OP_ARITMETICO_MENOS:
    		return "-";
    	case OP_ARITMETICO_DIVISAO:
    		return "/";
    	default:
    		return "*";
    	}
    }
    
    public static String converteRelacionais(TipoToken t) {
    	switch(t) {
    	case OP_RELACIONAL_MENOR:
    		return "<";
    	case OP_RELACIONAL_MAIOR:
    		return ">";
    	case OP_RELACIONAL_MENOR_IGUAL:
    		return "<=";
    	case OP_RELACIONAL_MAIOR_IGUAL:
    		return ">=";
    	case OP_RELACIONAL_COMPARACAO:
    		return "==";
    	case OP_RELACIONAL_DIFERENTE:
    		return "!=";
    	default:
    		return "";
    	}
    }
}
