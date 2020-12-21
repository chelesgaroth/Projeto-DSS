package GestRobot;

import java.lang.reflect.Array;
import java.util.*;

public class AlgoritmoDijkstra {
    private List<Vertice> vertices;
    private List<Aresta> corredores;
    private Set<Vertice> visitados;
    private Set<Vertice> naoVisitados;
    private Map<Vertice, Vertice> precedentes;
    private Map<Vertice, Float> distancia;

    public AlgoritmoDijkstra(Collection<Vertice> vertices, Collection<Aresta> corredores) {
        this.vertices = new ArrayList<>(vertices);
        this.corredores = new ArrayList<>(corredores);
        this.visitados = new HashSet<>();
        this.naoVisitados = new HashSet<>();
        this.distancia = new HashMap<>();
        this.precedentes = new HashMap<>();
    }


    public void executar(Vertice origem) {
        this.distancia.put(origem, (float)0);
        this.naoVisitados.add(origem);

        while (this.naoVisitados.size() > 0) {
            Vertice v = obterMinimo(this.naoVisitados);
            this.visitados.add(v);
            this.naoVisitados.remove(v);
            obterDistanciasMinimas(v);

        }
    }


    public void obterDistanciasMinimas(Vertice v) {
        List<Vertice> verticesAdjacentes = obterVizinhos(v);
        for (Vertice v1 : verticesAdjacentes) {
            if (obterDistanciaMaisCurta(v1) > obterDistanciaMaisCurta(v)
                    + obterDistancia(v, v1)) {
                this.distancia.put(v1, obterDistanciaMaisCurta(v)
                        + obterDistancia(v, v1));
                this.precedentes.put(v1, v);
                this.naoVisitados.add(v1);
            }
        }
    }

    public float obterDistancia(Vertice v, Vertice v1) {
        for (Aresta a : this.corredores) {
            if (a.getVerticeInicial().equals(v)
                    && a.getVerticeFinal().equals(v1)) {
                return a.getDist();
            }
        }
        throw new RuntimeException("Erro");
    }


    public List<Vertice> obterVizinhos(Vertice node) {
        List<Vertice> vizinhos = new ArrayList<Vertice>();
        for (Aresta a : this.corredores) {
            if (a.getVerticeInicial().equals(node)
                    && !foiVisitado(a.getVerticeFinal())) {
                vizinhos.add(a.getVerticeFinal());
            }
        }
        return vizinhos;
    }


    public boolean foiVisitado(Vertice v) {
        return this.visitados.contains(v);
    }


    public Vertice obterMinimo(Set<Vertice> vertices) {
        Vertice minimo = null;
        for (Vertice v : vertices) {
            if (minimo == null) {
                minimo = v;
            }
            else {
                if (obterDistanciaMaisCurta(v) < obterDistanciaMaisCurta(minimo)) {
                    minimo = v;
                }
            }
        }
        return minimo;
    }


    public float obterDistanciaMaisCurta(Vertice destino) {
        Float d = this.distancia.get(destino);
        if (d == null) {
            return Integer.MAX_VALUE;
        }
        else {
            return d;
        }
    }


    public List<Vertice> obterCaminhoMaisCurto(Vertice destino) {
        List<Vertice> caminho = new ArrayList<>();
        Vertice aux = destino;

        // Verificar que existe caminho
        if (this.precedentes.get(aux) == null) {
            return null;
        }
        caminho.add(aux);
        while (this.precedentes.get(aux) != null) {
            aux = this.precedentes.get(aux);
            caminho.add(aux);
        }
        // Por a ordem do caminho correta
        Collections.reverse(caminho);
        return caminho;
    }

}
