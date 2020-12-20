package GestPaletes;
import GestRobot.Vertice;

public class Palete {

	private QRCode codQR;
	private String codPalete;
	private int inRobot;
	private Vertice localizacao;


	public Palete(QRCode codQR, String codPalete, int inRobot, Vertice localizacao) {
		this.codQR = codQR;
		this.codPalete = codPalete;
		this.inRobot = inRobot;
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

	public int isInRobot() {
		return inRobot;
	}

	public void setInRobot(int inRobot) {
		this.inRobot = inRobot;
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
				" " + inRobot +
				" " + localizacao;
	}
}