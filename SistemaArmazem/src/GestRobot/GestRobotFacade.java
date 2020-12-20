package GestRobot;


import DataBase.*;
import Exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestRobotFacade implements IGestRobotFacade {

	private Map<String, Vertice> vertices;
	private Map<String, Aresta> corredores;
	private Map<String, Robot> frota;

	private Map<Integer, Aresta> arestas;
	private Map<Integer, Vertice> vs;


	public GestRobotFacade() {
		this.vertices = VerticeDAO.getInstance();
		this.corredores = ArestaDAO.getInstance();
		this.frota = RobotDAO.getInstance();
		this.arestas = new HashMap<>();
		this.vs = new HashMap<>();

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
		r.setEstado(1);
		Rota rota = new Rota("R"+codRobot,locP,null,codPalete,null);
		r.setRota(rota);
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
		Rota rota = r.getRota();
		rota.setDestino(destino);
		frota.put(codRobot,r);
	}

	/*
	0 = recolha
	1 = entrega
	 */
	public void calcula_rota(String codRobot, int EntregaOuRecolha) {

		Vertice v1 = new Vertice("v1","Zona D",0);
		Vertice v2 = new Vertice("v2","Canto A",0);
		Vertice v3 = new Vertice("v3","Canto B",0);
		Vertice v4 = new Vertice("v4","Canto C",0);
		Vertice v5 = new Vertice("v5","Canto D",0);
		Vertice v6 = new Vertice("v6","Prateleira 1",0);
		Vertice v7 = new Vertice("v7","Prateleira 2",0);
		Vertice v8 = new Vertice("v8","Prateleira 3",0);
		Vertice v9 = new Vertice("v9","Prateleira 4",0);
		Vertice v10 = new Vertice("v10","Prateleira 5",0);
		Vertice v11 = new Vertice("v11","Prateleira 6",0);
		Vertice v12 = new Vertice("v12","Prateleira 7",0);
		Vertice v13 = new Vertice("v13","Prateleira 8",0);
		Vertice v14 = new Vertice("v14","Prateleira 9",0);
		Vertice v15 = new Vertice("v15","Prateleira 10",0);

		Aresta a1 = new Aresta(v2,v1,"a1",2);
		Aresta a2 = new Aresta(v6,v2,"a2",2);
		Aresta a3 = new Aresta(v7,v6,"a3",4);
		Aresta a4 = new Aresta(v8,v7,"a4",4);
		Aresta a5 = new Aresta(v9,v8,"a5",4);
		Aresta a6 = new Aresta(v10,v9,"a6",4);
		Aresta a7 = new Aresta(v4,v10,"a7",2);
		Aresta a8 = new Aresta(v5,v4,"a8",5);
		Aresta a9 = new Aresta(v15,v5,"a9",2);
		Aresta a10 = new Aresta(v14,v15,"a10",4);
		Aresta a11 = new Aresta(v13,v14,"a11",4);
		Aresta a12 = new Aresta(v12,v13,"a12",4);
		Aresta a13 = new Aresta(v11,v12,"a13",4);
		Aresta a14 = new Aresta(v3,v11,"a14",2);
		Aresta a15 = new Aresta(v2,v3,"a15",5);
		Aresta a1a = new Aresta(v1,v2,"a1a",2);
		Aresta a2a = new Aresta(v2,v6,"a2a",2);
		Aresta a3a = new Aresta(v6,v7,"a3a",4);
		Aresta a4a = new Aresta(v7,v8,"a4a",4);
		Aresta a5a = new Aresta(v8,v9,"a5a",4);
		Aresta a6a = new Aresta(v9,v10,"a6a",4);
		Aresta a7a = new Aresta(v10,v4,"a7a",2);
		Aresta a8a = new Aresta(v4,v5,"a8a",5);
		Aresta a9a = new Aresta(v5,v15,"a9a",2);
		Aresta a10a = new Aresta(v15,v14,"a10a",4);
		Aresta a11a = new Aresta(v14,v13,"a11a",4);
		Aresta a12a = new Aresta(v13,v12,"a12a",4);
		Aresta a13a = new Aresta(v12,v11,"a13a",4);
		Aresta a14a = new Aresta(v11,v3,"a14a",2);
		Aresta a15a = new Aresta(v3,v2,"a15a",5);


		arestas.put(1,a1);
		arestas.put(2,a2);
		arestas.put(3,a3);
		arestas.put(4,a4);
		arestas.put(11,a11);
		arestas.put(12,a12);
		arestas.put(5,a5);
		arestas.put(6,a6);
		arestas.put(7,a7);
		arestas.put(8,a8);
		arestas.put(9,a9);
		arestas.put(10,a10);
		arestas.put(22,a7a);
		arestas.put(23,a8a);
		arestas.put(24,a9a);
		arestas.put(25,a10a);
		arestas.put(16,a1a);
		arestas.put(17,a2a);
		arestas.put(18,a3a);
		arestas.put(19,a4a);
		arestas.put(20,a5a);
		arestas.put(21,a6a);
		arestas.put(13,a13);
		arestas.put(14,a14);
		arestas.put(15,a15);
		arestas.put(26,a11a);
		arestas.put(27,a12a);
		arestas.put(28,a13a);
		arestas.put(29,a14a);
		arestas.put(30,a15a);

		vs.put(3,v3);
		vs.put(4,v4);
		vs.put(5,v5);
		vs.put(6,v6);
		vs.put(14,v14);
		vs.put(15,v15);
		vs.put(7,v7);
		vs.put(10,v10);
		vs.put(11,v11);
		vs.put(1,v1);
		vs.put(2,v2);
		vs.put(8,v8);
		vs.put(9,v9);
		vs.put(12,v12);
		vs.put(13,v13);


		Robot r = frota.get(codRobot); // excecao robot nao existe
		Rota rota = r.getRota();
		AlgoritmoDijkstra ad = new AlgoritmoDijkstra(vertices.values(),corredores.values());
		System.out.println("A palete está aqui! " + rota.getOrigem().getDesignacao());
		System.out.println("Destino da Palete: " + rota.getDestino().getDesignacao());
		System.out.println("O Robot está aqui: " + r.getLocalizacao());
		if(EntregaOuRecolha==0){
			ad.executar(r.getLocalizacao());
			List<Vertice> c = ad.obterCaminhoMaisCurto(rota.getOrigem());
			System.out.println("CAMINHO: ");
			for (Vertice re : c) {
				System.out.println(re.toString());
			}
			for (Aresta a : corredores.values()){
				System.out.println(a);
			}
		}
		else{
			/*ad.executar(rota.getOrigem());
			List<Vertice> c = ad.obterCaminhoMaisCurto(rota.getDestino());*/
		}
		List<Aresta> res = new ArrayList<>();
		/*for(int i = 0; i<c.size(); i++){
			for(Aresta a : corredores.values()){
				if((a.getVerticeInicial().equals(c.get(i)))&&(a.getVerticeInicial().equals(c.get(i+1)))) res.add(a);
			}
		}*/
		/*res.add(corredores.get("1"));
		res.add(corredores.get("2"));
		rota.setCaminho(res);
		frota.put(r.getCodRobot(),r);*/

	}

	public void atualiza_LocalizacaoRobot(String codRobot, int od){
		Robot r = frota.get(codRobot);
		Rota rota = r.getRota();
		Vertice loc;
		if(od==0) { // ou seja queremos atualizar para a origem do caminho
			loc = rota.getOrigem();
		}
		else{
			loc = rota.getDestino();
		}
		r.setLocalizacao(loc);


		//System.out.println("Caminho : " + rota.getCaminho().toString());
		frota.put(codRobot,r);

		//exception rota == null
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

	public String getPaleteDoRobot(String codRobot) {
		Robot r = frota.get(codRobot);
		Rota caminho = r.getRota();
		return caminho.getCodPalete();
	}

	public void atualizaOcupacaoVertice(Vertice v, int e){
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

	public String getPrateleiraLivre() throws EspacoInsuficienteNoArmazem{
		for(Vertice v : vertices.values()){
			if((v.getDesignacao().charAt(0)=='P')&&(v.getOcupacao()==0)) return v.getCodVertice();
		}
		throw new EspacoInsuficienteNoArmazem("Não existem Prateleiras Livres");

	}

}