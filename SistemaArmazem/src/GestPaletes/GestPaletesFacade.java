package GestPaletes;


import DataBase.PaleteDAO;
import DataBase.QRCodeDAO;
import GestRobot.Vertice;

import java.util.HashMap;
import java.util.Map;

public class GestPaletesFacade implements IGestPaletesFacade {

	private Map<String,Palete> paletes;
	private Map<String,QRCode> codigosQR;

	public GestPaletesFacade() {
		this.paletes = PaleteDAO.getInstance();
		this.codigosQR = QRCodeDAO.getInstance();
	}

	@Override
	public Map<String,Vertice> disponibiliza_listagem() {
		return null;
	}

	@Override
	public void registar_paletes(String codQR, String codPalete) {

	}

	@Override
	public void atualiza_localizacaoPalete(String codPalete, Vertice locPalete) {

	}

	@Override
	public void getLocalizacaoPalete(String codPalete) {

	}
}