package GestPaletes;
import GestRobot.Vertice;

public class Palete {

	private QRCode codQR;
	private String codPalete;
	private String codRobot;
	private Vertice localizacao;


	public Palete(QRCode codQR, String codPalete, String codRobot, Vertice localizacao) {
		this.codQR = codQR;
		this.codPalete = codPalete;
		this.codRobot = codRobot;
		this.localizacao = localizacao;
	}

	public QRCode getCodQR() {
		return codQR;
	}

	public void setCodQR(QRCode codQR) {
		this.codQR = codQR;
	}

	public String getCodPalete() {
		return codPalete;
	}

	public void setCodPalete(String codPalete) {
		this.codPalete = codPalete;
	}

	public String getCodRobot() {
		return codRobot;
	}

	public void setCodRobot(String codRobot) {
		this.codRobot = codRobot;
	}

	public Vertice getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(Vertice localizacao) {
		this.localizacao = localizacao;
	}


	@Override
	public String toString() {
		return "Palete:" +
				" " + codQR +
				" " + codPalete +
				" " + codRobot +
				" " + localizacao;
	}
}