package GestRobot;

public class Vertice {

	private String codVertice;
	private String designacao;
	private int ocupacao;

	public Vertice(String codVertice, String designacao, int ocupacao) {

		this.codVertice = codVertice;
		this.designacao = designacao;
		this.ocupacao = ocupacao;
	}


	public String getCodVertice() {
		return codVertice;
	}

	public void setCodVertice(String codVertice) {
		this.codVertice = codVertice;
	}

	public String getDesignacao() {
		return designacao;
	}

	public void setDesignacao(String designacao) {
		this.designacao = designacao;
	}

	public int getOcupacao() {
		return ocupacao;
	}

	public void setOcupacao(int ocupacao) {
		this.ocupacao = ocupacao;
	}

	@Override
	public String toString() {
		return "Vertice:" +
				" " + designacao +
				" " + ocupacao +
				" " + codVertice;
	}


}