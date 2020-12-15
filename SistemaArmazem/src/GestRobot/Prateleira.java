package GestRobot;

public class Prateleira {

	private String codPrateleira;
	private int disponibilidade;

	public Prateleira(String codPrateleira, int disponibilidade) {
		this.codPrateleira = codPrateleira;
		this.disponibilidade = disponibilidade;
	}

	public String getCodPrateleira() {
		return codPrateleira;
	}

	public void setCodPrateleira(String codPrateleira) {
		this.codPrateleira = codPrateleira;
	}

	public int getDisponibilidade() {
		return disponibilidade;
	}

	public void setDisponibilidade(int disponibilidade) {
		this.disponibilidade = disponibilidade;
	}

	@Override
	public String toString() {
		return "Prateleira" +
				" " + codPrateleira +
				" " + disponibilidade;
	}

}