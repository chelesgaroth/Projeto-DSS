package GestPaletes;

import GestRobot.Vertice;

import java.util.Map;

public interface IGestPaletesFacade {

	Map<String, Vertice> disponibiliza_listagem();

	/**
	 * 
	 * @param codQR
	 * @param codPalete
	 */
	void registar_paletes(String codQR, String codPalete);

	/**
	 * 
	 * @param codPalete
	 * @param locPalete
	 */
	void atualiza_localizacaoPalete(String codPalete, Vertice locPalete);

	/**
	 * 
	 * @param codPalete
	 */
	void getLocalizacaoPalete(String codPalete);

}