# Minimização de Fluxo em Movimentações Financeiras

[![Java](https://img.shields.io/badge/java-8-blue.svg)](https://www.oracle.com/technetwork/java/javase/8u-relnotes-2225394.html)
[![License](https://img.shields.io/github/license/mashape/apistatus.svg)](https://opensource.org/licenses/MIT)

A partir de várias transações financeiras entre diversos correntistas, o objetivo é minimizar o fluxo monetário envolvido, a fim de minimizar o imposto total cobrado sobre as movimentações financeiras envolvidas no processo.

## O Problema

O problema consiste em eliminar o "repasse" do mesmo dinheiro. Por exemplo, se o correntista *a* transfere 500 reais para o *b*, que passa 250 para o *c*, então é obtido um fluxo total de 750 reais movimentados. Porém, como pode-se notar, como o *b* recebeu 500 e transferiu 250, ele obteve um lucro de 250 reais, o mesmo lucro de *c*, o qual apenas recebeu 250. Sendo assim, é evidente que *b* e *c* receberam 250 reais cada. Logo, a fim de minimizar a quantia em fluxo, é possível reformular a relação de movimentações financeiras entre correntistas, contanto que *a* transfira o lucro de 250 reais para *b*, e transfira o lucro de 250 reais para *c*. O resultado disso é que, ao invés de movimentar um quantia total de 750 reais, apenas 500 reais são movimentado com o modelo simplificado, mantendo o efeito desejado.

## Estrutura de Dados

Para a resolução deste problema, foi utilizada uma represenação de grafos. Um grafo é um conjunto de vértices e arestas que conectam vértices. Será utilizado um vértice para representar cada correntista e uma aresta entre dois vértices para representar cada movimentação financeira. O peso de uma aresta inidica o valor transferido e a direção da aresta indica de qual correntista partiu o dinheiro e para qual correntista o dinheiro partiu.

## Casos de Teste

Abaixo são apresentados os resultados dos casos de teste, detalhando a quantidade total de dinheiro antes e depois da minimização. Além da quantidade economizada pela redução de impostos adquirida através da realização do processo.

|  #  | Inicial | Final  | Economia |
| --: | ------: | -----: | -------: |
|  1  |  684106 | 151996 |  5321.10 |
|  2  |  568965 | 161205 |  4077.60 |
|  3  |  415632 | 167800 |  2478.32 |
|  4  |  360943 | 138389 |  2225.54 |
|  5  |  262339 | 116130 |  1462.09 |
|  6  |  439454 | 147321 |  2921.33 |
|  7  |  197202 |  93899 |  1033.03 |
|  8  |  603655 | 191202 |  4124.53 |
|  9  |  438886 | 159721 |  2791.65 |
| 10  |  368191 | 125324 |  2428.67 |
