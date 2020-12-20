package GestRobot;


import Exceptions.EspacoInsuficienteNoArmazem;
import Exceptions.RobotsNaoDisponiveis;

import java.util.List;
import java.util.Map;

public interface IGestRobotFacade {

	String getRobotDisponivel() throws RobotsNaoDisponiveis;

	/**
	 * 
	 * @param codRobot
	 * @param codPalete
	 */
	void notifica_transporte(String codRobot, String codPalete, Vertice locP);

	/**
	 * 
	 * @param codRobot
	 * @param estado
	 */
	void atualiza_estadoRobot(String codRobot, int estado);


	void indica_destino(String codRobot, String codDestino);

	/**
	 * 
	 * @param codRobot
	 */
	void calcula_rota(String codRobot, int EntregaOuRecolha);
	void atualiza_LocalizacaoRobot(String codRobot, int od);

	boolean haRobots();
	Vertice getVerticeZonaDescarga();
	String getRobotPalete(String codPalete);
	String getPaleteDoRobot(String codRobot);
	void atualizaOcupacaoVertice(Vertice v, int e);
	void alteraRota(String codRobot, Rota rota);
	Vertice getLocalizacaoRobot(String codRobot);
	Map<String,String> listagemPaletesInRobot(List<String> paletes);
	String getPrateleiraLivre() throws EspacoInsuficienteNoArmazem;
}