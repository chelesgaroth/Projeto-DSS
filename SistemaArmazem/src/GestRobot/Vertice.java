package GestRobot;

import java.util.Objects;

public class Vertice {

	private String codVertice;
	private String designacao;
	private int ocupacao; // se for -1 prateleira foi reservada para receber palete

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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Vertice vertice = (Vertice) o;
		return codVertice.equals(vertice.codVertice);
	}



	@Override
	public int hashCode() {
		return Objects.hash(codVertice);
	}

	@Override
	public String toString() {
		return "Vertice:" +
				" " + designacao +
				" " + ocupacao +
				" " + codVertice;
	}
}