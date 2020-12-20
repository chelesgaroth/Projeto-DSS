package GestRobot;

import DataBase.ArestaDAO;

import java.util.*;

public class Rota {

	private String codRota;
	private Collection<Aresta> caminho;
	private Vertice origem;
	private Vertice destino;
	private String codPalete;


	public Rota(String codRota, Vertice origem, Vertice destino, String codPalete, Collection<Aresta> caminho) {
		this.caminho = caminho;
		this.origem = origem;
		this.destino = destino;
		this.codRota = codRota;
		this.codPalete = codPalete;
	}

	public Rota(String codRota, Vertice origem, Vertice destino, String codPalete) {
		this.codRota = codRota;
		this.origem = origem;
		this.destino = destino;
		this.codPalete = codPalete;
		this.caminho = null;
	}

	public Collection<Aresta> getCaminho() {
		return caminho;
	}

	public void setCaminho(Collection<Aresta> caminho) {
		this.caminho = caminho;
	}

	public Vertice getOrigem() {
		return origem;
	}

	public void setOrigem(Vertice origem) {
		this.origem = origem;
	}

	public Vertice getDestino() {
		return destino;
	}

	public void setDestino(Vertice destino) {
		this.destino = destino;
	}

	public String getCodRota() {
		return codRota;
	}

	public void setCodRota(String codRota) {
		this.codRota = codRota;
	}

	public String getCodPalete() {
		return codPalete;
	}

	public void setCodPalete(String codPalete) {
		this.codPalete = codPalete;
	}

	@Override
	public String toString() {
		return "Rota:" +
				" " + caminho +
				" " + origem +
				" " + destino +
				" " + codRota +
				" " + codPalete;
	}

}