package GestRobot;

public class Aresta {

	private Vertice verticeFinal;
	private Vertice verticeInicial;
	private String codAresta;
	private float dist;

	public Aresta(Vertice verticeFinal, Vertice verticeInicial, String codAresta, float dist) {
		this.verticeFinal = verticeFinal;
		this.verticeInicial = verticeInicial;
		this.codAresta = codAresta;
		this.dist = dist;
	}

	public Vertice getVerticeFinal() {
		return verticeFinal;
	}

	public void setVerticeFinal(Vertice verticeFinal) {
		this.verticeFinal = verticeFinal;
	}

	public Vertice getVerticeInicial() {
		return verticeInicial;
	}

	public void setVerticeInicial(Vertice verticeInicial) {
		this.verticeInicial = verticeInicial;
	}

	public String getCodAresta() {
		return codAresta;
	}

	public void setCodAresta(String codAresta) {
		this.codAresta = codAresta;
	}

	public float getDist() {
		return dist;
	}

	public void setDist(float dist) {
		this.dist = dist;
	}

	public String toString() {
		return "Aresta:" +
				verticeFinal + " " +
				verticeInicial + " " +
				codAresta + " " +
				dist;
	}
}