import java.util.*;

public class AlgoritmoAStar {

    public static void main(String[] args) {

        /*
         Representa um grafo não direcionado e uma matriz adjacente ponderada.
        Cada valor na matriz representa o custo de atravessar a aresta entre dois nós do grafo.
        O valor 0 indica que não há uma aresta entre os nós.
         */

        int[][] grafo = {
                {0,110,48,23,74,180,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {110,77,0,0,0,0,48,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {48,77,0,51,0,0,0,43,0,0,0,0,0,0,0,0,0,0,0,0},
                {23,0,51,0,51,0,0,0,44,0,0,0,0,0,0,0,0,0,0,0},
                {74,0,0,51,0,99,0,0,0,47,0,0,0,0,0,0,0,0,0,0},
                {180,0,0,0,99,0,0,0,0,0,50,0,0,0,0,0,0,0,0,0},
                {0,48,0,0,0,0,0,74,0,0,0,48,0,0,0,0,0,0,0,0},
                {0,0,43,0,0,0,74,0,50,0,0,0,0,44,0,0,0,0,0,0},
                {0,0,0,44,0,0,0,50,0,50,0,0,0,0,0,0,0,0,72,0},
                {0,0,0,0,47,0,0,0,50,0,76,0,0,0,30,0,0,0,0,0},
                {0,0,0,0,0,50,0,0,0,76,0,0,0,0,0,44,0,0,0,0},
                {0,0,0,0,0,0,48,0,0,0,0,0,18,0,0,0,37,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,18,0,43,0,0,42,0,0,0},
                {0,0,0,0,0,0,0,44,0,0,0,0,43,0,0,0,0,27,0,0},
                {0,0,0,0,0,0,0,0,0,30,0,0,0,0,0,38,0,0,0,49},
                {0,0,0,0,0,0,0,0,0,0,44,0,0,0,38,0,0,0,0,89},
                {0,0,0,0,0,0,0,0,0,0,0,37,27,0,0,0,0,42,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,27,0,0,42,0,47,0},
                {0,0,0,0,0,0,0,0,72,0,0,0,0,0,0,0,0,47,0,55},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,49,89,0,0,0,0}

        };

        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o ponto de partida: ");
        int noInicial = scanner.nextInt();

        System.out.print("Digite a quantidade de destinos: ");
        int numDestinos = scanner.nextInt();

        List<Integer> destinos = new ArrayList<>();
        for (int i = 0; i < numDestinos; i++) {
            System.out.print("Digite o destino " + (i + 1) + ": ");
            destinos.add(scanner.nextInt());
        }

        Rota resultado = aStar(grafo, noInicial, destinos);
        System.out.println("Rota completa: " + resultado.caminho);
        System.out.println("Custo total: " + resultado.custo);
    }
    /*
    Esta função é responsável por calcular a melhor rota a partir do nó inicial para um conjunto de destinos.
    Ela faz uso de permutações dos destinos para encontrar a melhor rota.
     */
    public static Rota aStar(int[][] grafo, int inicio, List<Integer> destinos) {
        List<Integer> melhorRota = null;
        int melhorCusto = Integer.MAX_VALUE;

        List<List<Integer>> permutacoes = calcularPermutacoes(destinos);
        for (List<Integer> permutacao : permutacoes) {
            List<Integer> rota = new ArrayList<>();
            rota.add(inicio);
            int custoTotal = 0;

            for (int i = 0; i < permutacao.size(); i++) {
                InfoRota infoRota = aStarCaminho(grafo, rota.get(rota.size() - 1), permutacao.get(i));
                rota.addAll(infoRota.caminho.subList(1, infoRota.caminho.size()));
                custoTotal += infoRota.custo;
            }

            if (custoTotal < melhorCusto) {
                melhorCusto = custoTotal;
                melhorRota = new ArrayList<>(rota);
            }
        }

        return new Rota(melhorRota, melhorCusto);
    }
    /*
    StarCaminho é chamada para calcular a rota mais curta de um nó de início para um nó de destino.
     */
    /*
    O algoritmo utiliza uma fila de prioridade conjuntoAberto para armazenar os nós a serem explorados,
    com base em seus valores fScore (um valor de custo estimado). Mapas veioDe e
    custoG são usados para rastrear o caminho e os custos acumulados.
     */

    private static InfoRota aStarCaminho(int[][] grafo, int inicio, int fim) {
        PriorityQueue<No> conjuntoAberto = new PriorityQueue<>(Comparator.comparingInt(no -> no.fScore));
        conjuntoAberto.add(new No(inicio, 0));

        Map<Integer, Integer> veioDe = new HashMap<>();
        Map<Integer, Integer> custoG = new HashMap<>();
        custoG.put(inicio, 0);

        //representado pela fila de prioridade
        while (!conjuntoAberto.isEmpty()) {
            No atual = conjuntoAberto.poll();
            //remove o nó com o menor valor de fScore da fila de prioridade.
            // Isso significa que o algoritmo está explorando o nó com o menor custo estimado
            if (atual.no == fim) {
                // Reconstruir o caminho
                List<Integer> caminho = new ArrayList<>();
                while (atual.no != null) {
                    caminho.add(0, atual.no);
                    atual.no = veioDe.get(atual.no);
                }
                return new InfoRota(caminho, custoG.get(fim));
            }

            for (int vizinho = 0; vizinho < grafo[atual.no].length; vizinho++) {
                int distancia = grafo[atual.no][vizinho];
                if (distancia > 0) {
                    int custoGTentativo = custoG.get(atual.no) + distancia;

                    if (!custoG.containsKey(vizinho) || custoGTentativo < custoG.get(vizinho)) {
                        custoG.put(vizinho, custoGTentativo);
                        int fScore = custoGTentativo + heuristica(fim, vizinho);
                        conjuntoAberto.add(new No(vizinho, fScore));
                        veioDe.put(vizinho, atual.no);
                    }
                }
            }
        }

        return new InfoRota(Collections.emptyList(), 0); // Sem caminho encontrado
        //Retorna lista sem elemento
    }
    /*
     Essa função é usada para calcular uma heurística que estima o custo restante
     para atingir o destino a partir de um determinado nó. Neste exemplo, a heurística
     é a distância absoluta entre os nós.
     */
    private static int heuristica(int a, int b) {

        return Math.abs(a - b);
    }
    /*
    A função calcularPermutacoes é usada para gerar todas as permutações possíveis dos destinos fornecidos.
     Isso permite que o algoritmo encontre a melhor ordem para visitar os destinos.
     */
    private static List<List<Integer>> calcularPermutacoes(List<Integer> destinos) {
        List<List<Integer>> permutacoes = new ArrayList<>();
        calcularPermutacoesAux(destinos, 0, permutacoes);
        return permutacoes;
    }

    private static void calcularPermutacoesAux(List<Integer> destinos, int inicio, List<List<Integer>> permutacoes) {
        if (inicio == destinos.size() - 1) {
            /*
            verifica se a variável inicio é igual ao índice do último elemento na lista destinos,
            e determina se a recursão deve continuar gerando permutações ou se deve parar,
             */
            permutacoes.add(new ArrayList<>(destinos));
            return;
        }

        for (int i = inicio; i < destinos.size(); i++) {
            Collections.swap(destinos, inicio, i);//Troca os elementos na posição inicio, mudando a ordem de dois destinos na lista.
            calcularPermutacoesAux(destinos, inicio + 1, permutacoes);
            Collections.swap(destinos, inicio, i);
        }
    }

    static class No {
        Integer no;
        int fScore; //custo estimado total: é usado para determinar a ordem de exploração dos nós

        public No(Integer no, int fScore) {
            this.no = no;
            this.fScore = fScore;
        }
    }
    //Representar informações sobre uma rota encontrada pelo algoritmo A*
    static class InfoRota {
        List<Integer> caminho;//caminho percorrido/ ou seja, os nós visitados na ordem em que foram visitados.
        int custo;//custo total da rota

        public InfoRota(List<Integer> caminho, int custo) {
            this.caminho = caminho;
            this.custo = custo;
        }
    }
    /*
    Rota é usada para representar informações sobre
    uma rota, incluindo o caminho percorrido e o custo total.
     */
    static class Rota {
        List<Integer> caminho;
        int custo;

        public Rota(List<Integer> caminho, int custo) {
            this.caminho = caminho;
            this.custo = custo;
        }
    }
}