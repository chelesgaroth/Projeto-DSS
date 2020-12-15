package GestRobot;

public class Robot {

	private Rota rota;
	private String codRobot;
	private int estado;

	public Robot(Rota rota, String codRobot, int estado) {
		this.rota = rota;
		this.codRobot = codRobot;
		this.estado = estado;
	}

	public Rota getRota() {
		return rota;
	}

	public void setRota(Rota rota) {
		this.rota = rota;
	}

	public String getCodRobot() {
		return codRobot;
	}

	public void setCodRobot(String codRobot) {
		this.codRobot = codRobot;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Robot:" +
				" " +  codRobot +
				" " + rota.toString() +
				" " + estado;
	}
}