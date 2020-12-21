package GestRobot;


import DataBase.*;
import Exceptions.*;

import java.util.*;

public class GestRobotFacade implements IGestRobotFacade {

	private Map<String, Vertice> vertices;
	private Map<String, Aresta> corredores;
	private Map<String, Robot> frota;



	public GestRobotFacade() {
		this.vertices = VerticeDAO.getInstance();
		this.corredores = ArestaDAO.getInstance();
		this.frota = RobotDAO.getInstance();

	}

	public String getRobotDisponivel() throws RobotsNaoDisponiveis {
		for (Robot r : frota.values()) {
			if (r.getEstado() == 0)
				return r.getCodRobot();
		}
		throw new RobotsNaoDisponiveis("Não existem robots disponiveis para transportar paletes.");
	}

	public void notifica_transporte(String codRobot, String codPalete, Vertice locP) {
		Robot r = frota.get(codRobot); // excecao robot nao existe
		r.notifica_transporte(codPalete,locP);
		frota.put(codRobot,r);
	}

	public void atualiza_estadoRobot(String codRobot, int estado) {
		Robot r = frota.get(codRobot);
		r.setEstado(estado);
		frota.put(codRobot,r);
	}


	public void indica_destino(String codRobot, String codDestino) {
		Vertice destino = vertices.get(codDestino);
		Robot r = frota.get(codRobot); // excecao robot nao existe
		r.indica_destino(destino);
		frota.put(codRobot,r);
	}

	/*
	0 = recolha
	1 = entrega
	 */
	public void calcula_rota(String codRobot, int EntregaOuRecolha) throws OrigemIgualDestino {
		Robot r = frota.get(codRobot); // excecao robot nao existe
		Rota rota = r.getRota();
		AlgoritmoDijkstra ad = new AlgoritmoDijkstra(vertices.values(),corredores.values());
		List<Vertice> c;
		Vertice origem;
		Vertice destino;
		if(EntregaOuRecolha==0){
			origem = r.getLocalizacao();
			destino = rota.getOrigem();

		}
		else{
			origem = rota.getOrigem();
			destino = rota.getDestino();
		}
		List<Aresta> res = new ArrayList<>();
		if (!origem.equals(destino)) {
			ad.executar(origem);
			c = ad.obterCaminhoMaisCurto(destino);
			for(int i = 0; i<c.size()-1; i++){
				for(Aresta a : corredores.values()){
					if((a.getVerticeInicial().equals(c.get(i)))&&(a.getVerticeFinal().equals(c.get(i+1)))) res.add(a);
				}
			}
		}
		else throw new OrigemIgualDestino("Robot já está no vertice pretendido.");
		rota.setCaminho(res);
		frota.put(r.getCodRobot(),r);
	}

	public void atualiza_LocalizacaoRobot(String codRobot, int od) throws RotaNull {
		Robot r = frota.get(codRobot);
		r.atualizaLocalizacao(od);
		frota.put(codRobot,r);
	}

	/**
	 * Método que verifica se há robots no sistema
	 * @return true se há robots registados
	 */

	public boolean haRobots() {
		return !this.frota.isEmpty();
	}

	public Vertice getVerticeZonaDescarga(){
		Vertice v = null;
		for(Vertice ve : vertices.values()){
			if(ve.getDesignacao().equals("Zona D")){
				v = ve;
			}
		}
		return v;
	}

	// vai buscar o robot que transporte a palete dada
	public String getRobotPalete(String codPalete) {
		String robot = "n/d";
		for (Robot r : frota.values()) {
			Rota rota = r.getRota();
			if( rota!= null && rota.getCodPalete().equals(codPalete)) {
				robot = r.getCodRobot();
				break;
			}
		}
		return robot;
	}

	public String getPaleteDoRobot(String codRobot) throws RotaNull{
		Robot r = frota.get(codRobot);
		Rota caminho = r.getRota();
		if (caminho!= null)
			return caminho.getCodPalete();
		else
			throw new RotaNull("Robot não está a efetuar nenhuma rota!!");
	}

	public void atualizaOcupacaoVertice(String codVertice, int e){
		Vertice v = vertices.get(codVertice);
		v.setOcupacao(e);
		vertices.put(v.getCodVertice(),v);
	}

	public void alteraRota(String codRobot, Rota rota){
		Robot r = frota.get(codRobot);
		r.setRota(rota);
		frota.put(codRobot,r);
	}

	public Vertice getLocalizacaoRobot(String codRobot){
		return frota.get(codRobot).getLocalizacao();
	}

	public Map<String,String> listagemPaletesInRobot(List<String> paletes) {
		Map<String,String> list = new HashMap<>();
		for (String p : paletes) {
			list.put(p,getRobotPalete(p));
		}
		return list;
	}

	public String getPrateleiraLivre() throws EspacoInsuficienteNoArmazem {
		for(Vertice v : vertices.values()){
			if((v.getDesignacao().charAt(0)=='P')&&(v.getOcupacao()==0)) return v.getCodVertice();
		}
		throw new EspacoInsuficienteNoArmazem("Não existem Prateleiras Livres!!");

	}
	public List<String> getCaminho(String codRobot){
		Robot r = frota.get(codRobot);
		Rota rota = r.getRota();
		Collection<Aresta> lista = rota.getCaminho();
		List<String> res = new ArrayList<>();
		Aresta b = null;
		for(Aresta a : lista){
			res.add(a.getVerticeInicial().getDesignacao());
			b = a;
		}
		if(b!=null)res.add(b.getVerticeFinal().getDesignacao());
		return res;
	}

	public boolean validaRobot(String codRobot) throws RobotNaoExiste{
		if(frota.containsKey(codRobot)) return true;
		throw new RobotNaoExiste("Robot não existe");

	}

}