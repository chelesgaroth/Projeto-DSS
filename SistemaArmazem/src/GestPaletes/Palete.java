package GestPaletes;
import GestRobot.Vertice;

public class Palete {

	private QRCode codQR;        /** Código QR */
	private String codPalete;	 /** Código da Palete */
	private int inRobot;		 /** 0 - Caso não esteja num Robot ; 1 - Caso esteja num Robot ; 2 - Reservada para um robot */
	private Vertice localizacao; /** Vértice onde a palete se encontra , podendo ser null , caso esteja num Robot */


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

	public int getInRobot() {
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