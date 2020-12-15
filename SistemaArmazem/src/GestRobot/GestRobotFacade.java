package GestRobot;


import DataBase.ArestaDAO;
import DataBase.RobotDAO;
import DataBase.VerticeDAO;

import java.util.List;

public class GestRobotFacade implements IGestRobotFacade {

	private ArestaDAO corredores;
	private VerticeDAO vertices;
	private RobotDAO frota;


	@Override
	public String getRobotDisponivel() {
		return null;
	}

	@Override
	public void notifica_transporte(String codRobot, String codPalete) {

	}

	@Override
	public void atualiza_estadoRobot(String codRobot, int estado) {

	}

	@Override
	public void indica_destino(String codRobot, Vertice destino) {

	}

	@Override
	public List<Aresta> calcula_rota(String codRobot) {
		return null;
	}
}