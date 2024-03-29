## Especificação:

### Parte I: Analisador Léxico (SCANNER)

Escrever, em C/C++ ou Java, a função SCAN para a linguagem abaixo:

_Observação 1: o arquivo a ser compilado será passado ao seu compilador via argumento da linha de comando._

_Observação 2: Imprimir apenas mensagens de erro._

_Observação 3: A mensagem deve ser clara e específica de erro, sempre que for o caso, e em qualquer fase do compilador. Formato: "ERRO na linha n, coluna m, ultimo token lido t: mensagem específica do erro."_

**Símbolos:**

```
letra ::= [a-z]
dígito ::= [0-9]
id ::= (letra | "_") (letra | "_" | dígito)*
oprelacional ::= < | > | <= | >= | == | !=
oparitmético ::= "+" | "-" | "*" | "/" | "="
especial ::= ")" | "(" | "{" | "}" | "," | ";"
palreservada ::= main | if | else | while | do | for | int | float | char
inteiro ::= dígito+
float ::= dígito*.dígito+
char ::= 'letra' | 'dígito' // Uma constante do tipo char (entre aspas simples)
```

**Aspectos Gerais da Linguagem**

-   Linguagem de formato livre.
-   Linguagem é case sensitive, ou seja “WHILE” != “while”
-   As palavras reservadas são delimitadas, no programa fonte, por brancos, operadores aritméticos ou símbolos especiais;
-   Os comentários são delimitados por
    -   “//” - indicando comentário até o final da linha
    -   "/\*" e "\*/"

**Atribuições do Scanner**

-   Retornar dois resultados: classificação e lexema.
-   No caso de o token ser um identificador ou uma constante devem ser retornados classificação e lexema.
-   Se for uma palavra reservada, operador, caracter especial ou delimitador: classificação apenas.
-   Emitir mensagem clara e específica de erro, sempre que for o caso, e em qualquer fase do compilador. Formato: "ERRO na linha n, coluna m, ultimo token lido t: mensagem"
    -   Considere o TAB como equivalente a 4 colunas.

**Procedimento:**

-   Adotar uma representação interna para cada tipo de token.
    - Lembre-se que, por exemplo, "+" é diferente de "-", portanto não podem ter mesma representação.
-   Organizar a tabela de palavras reservadas.
-   Construir um diagrama de estados, com ações semânticas, para a identificação dos símbolos.
-   Escrever a função SCAN.

**Erros do Scanner neste projeto:**

-   Caracter inválido.
-   Valor float mal formado.
-   Valor char mal formado.
-   EOF em comentário multilinha.
-   Exclamação (‘!’) não seguida de ‘=’

_OBS: não são regras gerais para todos os compiladores!_

### Parte II: Analisador Sintático (PARSER)

Implemente um parser descendente preditivo recursivo (com procedimentos recursivos), para a linguagem fonte descrita abaixo.

_Observação 1: o arquivo a ser compilado será passado ao seu compilador via argumento da linha de comando_

_Observação 2: Imprimir apenas mensagens de erro._

_Observação 3: A mensagem deve ser clara e específica de erro, sempre que for o caso, e em qualquer fase do compilador._

_Formato: "ERRO na linha n , coluna m : mensagem específica do erro"_

_Opcionalmente, pode-se imprimir o ultimo token lido (se estiver disponivel)_

_Observação 4: Após o fechamento do bloco do programa (main) não pode haver mais tokens, ou seja, o proximo retorno do scanner deve ser fim_de_arquivo._

**1. Introdução**

A linguagem tem uma estrutura de blocos tipo C. Sera descrita como uma GLC e com o auxílio da notação EBNF e expressões regulares.

**2. Declaracões**

Não teremos declaracões de procedimentos nem funções, apenas de variáveis. As declarações devem ser agrupadas no início do bloco, e devem aparecer numa sequência bem definida de modo a facilitar a compilação.

As variáveis podem ser do tipo int, float ou char, e as declarações devem ter o seguinte formato:

```
<decl_var> ::= <tipo> <id> {,<id>}\* ";"
<tipo> ::= int | float | char
```

**3. Expressões**

Em geral, uma expressão é uma arvore de valores. Em sua forma mais simples, ela é um único valor de um tipo primitivo.

As produções para expressões obedecem à seguinte ordem de precedência:

1.  \*, /
2.  +, -
3.  ==, !=, <, >, <=, >=

O aluno deve modificar as produções de modo a eliminar a recursão à esquerda

_OBS: Expressões apenas com os operadores \*, /, +, - são expressões aritméticas. Expressões com os operadores de comparação (==, !>, <, ...) são expressões relacionais. Não podemos ter mais de um operador relacional em um expressão. Podemos ter expressões aritméticas de qualquer lado de um operador relacional. Mas, não podemos ter expressões relacionais em comandos de atribuição._

**4. Programa e Comandos**

Um programa é um bloco (como em C). Podemos ter blocos dentro de blocos. Dentro de um bloco as declaracões devem preceder os comandos.
O significado de if, if-else, while e do-while é como na linguagem C padrão.

**5. Sintaxe**

```
<programa> ::= int main"("")" <bloco>
<bloco> ::= “{“ {<decl_var>}_ {<comando>}_ “}”
<comando> ::= <comando_básico> | <iteração> | if "("<expr_relacional>")" <comando> {else <comando>}?
<comando_básico> ::= <atribuição> | <bloco>
<iteração> ::= while "("<expr_relacional>")" <comando> | do <comando> while "("<expr_relacional>")"";"
<atribuição> ::= <id> "=" <expr_arit> ";"
<expr_relacional> ::= <expr_arit> <op_relacional> <expr_arit>
<expr_arit> ::= <expr_arit> "+" <termo> | <expr_arit> "-" <termo> | <termo>
<termo> ::= <termo> "\*" <fator> | <termo> “/” <fator> | <fator>
<fator> ::= “(“ <expr_arit> “)” | <id> | <float> | <inteiro> | <char>
```

_Nota: os símbolos abre e fecha chaves, quando entre aspas, são terminais_

**6. Tabela de Símbolos**

Para as variáveis, sugere-se que a tabela de símbolos seja uma lista encadeada onde os nós serão registros com os atributos das variáveis: lexema e tipo. O aluno pode modificar a tabela se encontrar utilidade para outro tipo de atributo ou se achar necessário incluir constantes com seus tipos e valores.

Como em toda lista encadeada, precisamos de um nó que aponta para a "cabeça" da lista. Chamemos este nó de "tabela". Na ativação de um bloco, guarde o conteúdo de "tabela" e adicione as novas variáveis no inicio da tabela de símbolos. Na desativação, restaure o valor de "tabela", eliminando assim todas as variáveis declaradas nesse bloco. Lembre de desalocar todos os nós com as variáveis do bloco sendo desativado. A busca a partir de "tabela" sempre encontrará o identificador mais recentemente declarado, por isso as variáveis devem ser incluídas no início da tabela de símbolos.

### Parte III: Analisador Semântico

**1. Introdução**

Complemente seu parser com um analisador semântico. Como a linguagem é simples, haverá basicamente checagem de tipos.

Sugere-se que o sintático não seja um código à parte, mas que esteja embutido no parser.

_Observação 1: o arquivo a ser compilado será passado ao seu compilador via argumento da linha de comando_

_Observação 2: Imprimir apenas mensagens de erro._

_Observação 3: A mensagem deve ser clara e específica de erro, sempre que for o caso, e em qualquer fase do compilador. Formato: "ERRO na linha n, coluna m, ultimo token lido t: mensagem específica do erro"_

**2. Regras**

Qualquer comando que relacionar duas ou mais entidades (como variáveis e constantes) deverá verificar a compatibilidade de seus tipos.

O tipo char (constantes char) é compatível apenas com ele mesmo. Seu compilador deve aceitar expressões aritméticas e relacionais com variáveis e literais do tipo char. Ou seja, qualquer operação entre operandos char, resulta no tipo char.

Os tipos numéricos float e int são compatíveis, porém não se pode atribuir um float a um int. Além disso, dividindo-se dois inteiros (variáveis ou constantes) o tipo resultante é float

Variáveis devem ter sido declaradas antes de ser usadas, e só podem ser usadas observando-se as regras padrão de escopo. Não podem haver variáveis com o mesmo nome no mesmo escopo, mas em escopos diferentes (e.g., sub-blocos) são permitidas.

A tabela de símbolos deve ser utilizada para pesquisa da existência da variável e seu tipo, e deve dar suporte ao mecanismo de escopo explicado no projeto do parser.

### Parte IV: Gerador de Código Intermediário

**1. Introdução**

Seu compilador deve gerar código de 3 endereços correspondente ao programa fonte.

_Observação 1: o arquivo a ser compilado será passado ao seu compilador via argumento da linha de comando_

_Observação 2: Imprimir apenas mensagens de erro._

_Observação 3: A mensagem deve ser clara e específica de erro, sempre que for o caso, e em qualquer fase do compilador. Formato: "ERRO na linha n, coluna m, ultimo token lido t: mensagem específica do erro"_

**2. Regras**

O código de 3 endereços deve ser semelhante ao visto nos slides. Apenas 1 instrução por linha. Apenas uma operação por instrução (conversão de tipo é uma operação).

O seu compilador deve escrever o código gerado na saída padrão.

Deve-se fazer uso de variáveis temporárias e labels gerados automaticamente para os goto's. Todos os comandos iterativos no programa fonte, deverão ser traduzidos para um bloco de comandos com goto's e if's. Utilize uma convenção para a semântica do if, de modo a não ficar ambígua quanto ao teste de condições.

Deve-se fazer conversão de tipos quando houver presença de entidades de tipos diferentes, porém compatíveis, no mesmo comando do programa fonte. Nenhuma instrução do código de 3 endereços pode fazer operações com tipos diferentes.
