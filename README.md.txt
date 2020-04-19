Para executar, utilize

javac *.java
java Main <path para seu arquivo de teste>

exemplo: java Main ../data.txt

Caso não encontre erros o código irá imprimir: fim de arquivo

Caso o código encontre erros irá mostrar aonde encontrou e qual era o erro, desta forma:
Exception in thread "main" ERRO. Float mal formado. Esperava um digito depois do Linha: 48 Coluna: 2
	at Scanner.scan(Scanner.java:177)
	at Main.main(Main.java:10)