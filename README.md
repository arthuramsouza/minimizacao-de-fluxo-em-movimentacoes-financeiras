# Minimização de Fluxo em Movimentações Financeiras

A partir de várias transações financeiras entre diversos correntistas, minimizar o fluxo monetário envolvido, a fim de minimizar o imposto total cobrado sobre as movimentações financeiras envolvidas no processo.

## O Problema

O problema consiste em eliminar o "repasse" do mesmo dinheiro. Por exemplo, se o correntista <i>a</i> transfere 500 reais para o <i>b</i>, que passa 250 para o <i>c</i>, então é obtido um fluxo total de 750 reais movimentados. Porém, como pode-se notar, como o <i>b</i> recebeu 500 e transferiu 250, ele obteve um lucro de 250 reais, o mesmo lucro de <i>c</i>, o qual apenas recebeu 250. Sendo assim, é evidente que <i>b</i> e <i>c</i> receberam 250 reais cada. Logo, a fim de minimizar a quantia em fluxo, é possível reformular a relação de movimentações financeiras entre correntistas, contanto que <i>a</i> transfira o lucro de 250 reais para <i>b</i>, e transfira o lucro de 250 reais para <i>c</i>. O resultado disso é que, ao invés de movimentar um quantia total de 750 reais, apenas 500 reais são movimentado com o modelo simplificado, mantendo o efeito desejado.

## Estrutura de Dados

Para a resolução deste problema, foi utilizada uma represenação de grafos. Um grafo é um conjunto de vértices e arestas que conectam vértices. Será utilizado um vértice para representar cada correntista e uma aresta entre dois vértices para representar cada movimentação financeira. O peso de uma aresta inidica o valor transferido e a direção da aresta indica de qual correntista partiu o dinheiro e para qual correntista o dinheiro partiu.
