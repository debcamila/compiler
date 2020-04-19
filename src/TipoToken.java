
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
    
    //OPERADORES ARITMETICOS
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
}
