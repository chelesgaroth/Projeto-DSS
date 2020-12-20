package GestPaletes;

import Exceptions.QRCodeInvalido;
import GestRobot.Vertice;

import java.util.List;
import java.util.Map;

public interface IGestPaletesFacade {

	Map<String,String> disponibiliza_listagem();


	void registaPalete(String codQR, String codPalete , Vertice loc) throws QRCodeInvalido;

	/**
	 * 
	 * @param codPalete
	 * @param locPalete
	 */
	void atualiza_localizacaoPalete(String codPalete, Vertice locPalete, int inRobot);

	/**
	 * 
	 * @param codPalete
	 */
	Vertice getLocalizacaoPalete(String codPalete);

	boolean haPaletes();
	boolean existeQRcode(String qr);
	List<String> getPaletesEmRobots();
	String paleteZonaD();
}