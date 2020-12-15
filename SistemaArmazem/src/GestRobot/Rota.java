package GestRobot;

import DataBase.ArestaDAO;

import java.util.*;

public class Rota {

	private Collection<ArestaDAO> caminho;
	private Vertice origem;
	private Vertice destino;
	private String codRota;
	private String codPalete;


	public Rota(Collection<ArestaDAO> caminho, Vertice origem, Vertice destino, String codRota, String codPalete) {
		this.caminho = caminho;
		this.origem = origem;
		this.destino = destino;
		this.codRota = codRota;
		this.codPalete = codPalete;
	}

	public Collection<ArestaDAO> getCaminho() {
		return caminho;
	}

	public void setCaminho(Collection<ArestaDAO> caminho) {
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