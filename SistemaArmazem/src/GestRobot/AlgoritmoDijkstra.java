package GestRobot;

import java.lang.reflect.Array;
import java.util.*;

public class AlgoritmoDijkstra {
    private List<Vertice> vertices;
    private final List<Aresta> corredores;
    private Set<Vertice> visitados;
    private Set<Vertice> naoVisitados;
    private Map<String, Vertice> precedentes;
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
     //   System.out.println("ORIGEM "+origem);
        this.distancia.put(origem, (float)0);
     //   System.out.println("Distancia "+distancia);
        this.naoVisitados.add(origem);
     //   System.out.println("NV "+naoVisitados);
        while (this.naoVisitados.size() > 0) {
            Vertice v = obterMinimo(this.naoVisitados);
        //    System.out.println("VERTICE V: " + v);
            this.visitados.add(v);
      //      System.out.println("Vis "+visitados);
            this.naoVisitados.remove(v);
      //      System.out.println("NV222 "+naoVisitados);
            obterDistanciasMinimas(v);

        }
    }


    public void obterDistanciasMinimas(Vertice v) {
        List<Vertice> verticesAdjacentes = obterVizinhos(v);
       // System.out.println("OLAAAAAAAAAAAAAAAAAAAAAAAAA");
        for (Vertice v1 : verticesAdjacentes) {
            System.out.println("OLAAAAAAAAAAAAAAAAAAAAAAAAA222");
            System.out.println((obterDistanciaMaisCurta(v1)));
            System.out.println((obterDistanciaMaisCurta(v)+ obterDistancia(v, v1)));

            if (obterDistanciaMaisCurta(v1) > (obterDistanciaMaisCurta(v)
                    + obterDistancia(v, v1))) {
                System.out.println("OLAAAAAAAAAAAAAAAAAAAAAAAAA3333");
                this.distancia.put(v1, (obterDistanciaMaisCurta(v) + obterDistancia(v, v1)));
                this.precedentes.put(v1.getCodVertice(), v);
                this.naoVisitados.add(v1);
                System.out.println("NV333333 "+naoVisitados);
            }
        }
        System.out.println("PREEE  " + precedentes);
    }

    public float obterDistancia(Vertice v, Vertice v1) {
        for (Aresta a : this.corredores) {
            if (a.getVerticeInicial().getCodVertice().equals(v.getCodVertice())
                    && a.getVerticeFinal().getCodVertice().equals(v1.getCodVertice())) {
                return a.getDist();
            }
        }
        throw new RuntimeException("Erro");
    }


    public List<Vertice> obterVizinhos(Vertice node) {
        List<Vertice> vizinhos = new ArrayList<Vertice>();
       // System.out.println("ENTROU");
      //  System.out.println("CORREDORES " + this.corredores);
        for (Aresta a : this.corredores) {
            //System.out.println("VIZINHOS");
            if (a.getVerticeInicial().getCodVertice().equals(node.getCodVertice())
                    && !foiVisitado(a.getVerticeFinal())) {
                System.out.println("TRUEEEEEEEEEEEEEEEE");
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
        System.out.println("ADEUSSS  " + precedentes);
        List<Vertice> caminho = new ArrayList<>();
        Vertice aux = destino;
        System.out.println("Destino:  " + destino);
        // Verificar que existe caminho
        System.out.println("VERTICE: "+this.precedentes.get(destino.getCodVertice()));
        if (this.precedentes.get(destino.getCodVertice()) == null) {
            System.out.println("NAO TEM AUX!!!!!");
            return null;
        }
        caminho.add(aux);
        while (this.precedentes.get(aux.getCodVertice()) != null) {
            aux = this.precedentes.get(aux.getCodVertice());
            caminho.add(aux);
        }
        System.out.println("CAMINHO " +caminho);
        // Por a ordem do caminho correta
        Collections.reverse(caminho);
        return caminho;
    }

}
