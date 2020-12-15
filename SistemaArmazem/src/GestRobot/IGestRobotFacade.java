package GestRobot;


import java.util.List;

public interface IGestRobotFacade {

	String getRobotDisponivel();

	/**
	 * 
	 * @param codRobot
	 * @param codPalete
	 */
	void notifica_transporte(String codRobot, String codPalete);

	/**
	 * 
	 * @param codRobot
	 * @param estado
	 */
	void atualiza_estadoRobot(String codRobot, int estado);

	/**
	 * 
	 * @param codRobot
	 * @param destino
	 */
	void indica_destino(String codRobot, Vertice destino);

	/**
	 * 
	 * @param codRobot
	 */
	List<Aresta> calcula_rota(String codRobot);

}