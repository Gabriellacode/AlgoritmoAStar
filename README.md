# **PROBLEMA DE ROTERIZACAO**
Atividade avaliativa desenvolvida no âmbito da disciplina de computação inteligente, no Instituto Federal de Educação, Ciência e Tecnologia (IFS). O objetivo da atividade era Mapear uma região com a distância real entre os pontos, elaborar um grafo e implementar o algoritmo A* para  percorrer todos os pontos/endereços (vértices) especificados em uma única rota, visando alcançar o menor custo possível, sem levar em conta a ordem em que os pedidos foram solicitados.


### **ELABORAÇÃO DO GRAFO**
A região escolhida para mapear foi o conjunto Pedro Valadares, localizado na cidade de Simão Dias - SE, conforme o mapa da figura abaixo:

![Captura de tela 2024-02-12 235932](https://github.com/Gabriellacode/AlgoritmoAStar/assets/108696464/6a8aaecc-6fb7-4a22-bea5-ebd2a165db06)

A região delimitada em vermelho representa o conjunto Pedro Valadares. 

Posteriormente, criou-se manualmente um grafo da área, usando as distâncias entre as ruas como pesos nas arestas, gerando assim um grafo ponderado não direcionado. Conforme ilustrado na figura abaixo:

![Captura de tela 2024-02-13 000109](https://github.com/Gabriellacode/AlgoritmoAStar/assets/108696464/fb65f699-595f-4509-93f7-1d238c43e427)

Os caracteres destacados em vermelho correspondem aos nomes das ruas, os números em cinza indicam suas posições no índice da matriz a ser gerada, e os caracteres em roxo representam as distâncias de cada rua. Resultando na matriz de adjacência, conforme a figura abaixo:

![Captura de tela 2024-02-13 000550](https://github.com/Gabriellacode/AlgoritmoAStar/assets/108696464/ffd9168e-f88d-4c34-bbba-ea3130587cb0)

A escolha em representar o grafo em matriz ocorreu devido às vantagens que poderia proporcionar, permitindo acesso direto aos pesos das arestas e possuindo o melhor desempenho para grafos densos. Desconsiderando a desvantagem relacionada ao uso de memória em grafos esparsos.
