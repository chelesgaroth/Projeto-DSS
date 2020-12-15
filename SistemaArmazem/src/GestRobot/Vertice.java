package GestRobot;

public class Vertice {

	private Robot localizacao;
	private String codVertice;
	private String designacao;
	private int ocupacao;

	public Vertice(Robot localizacao, String codVertice, String designacao, int ocupacao) {
		this.localizacao = localizacao;
		this.codVertice = codVertice;
		this.designacao = designacao;
		this.ocupacao = ocupacao;
	}


	public Robot getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(Robot localizacao) {
		this.localizacao = localizacao;
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
				" " + localizacao.toString() +
				" " + designacao +
				" " + ocupacao +
				" " + codVertice;
	}
}