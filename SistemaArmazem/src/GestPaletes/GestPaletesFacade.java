package GestPaletes;


import DataBase.PaleteDAO;
import DataBase.QRCodeDAO;
import Exceptions.NaoLevantouPalete;
import Exceptions.PaleteJaExiste;
import Exceptions.QRCodeInvalido;
import Exceptions.SemPaletesParaTransportar;
import GestRobot.Vertice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestPaletesFacade implements IGestPaletesFacade {

	private Map<String,QRCode> codigosQR;
	private Map<String,Palete> paletes;

	public GestPaletesFacade() {
		this.codigosQR = QRCodeDAO.getInstance();
		this.paletes = PaleteDAO.getInstance();

	}

	public Map<String,String> disponibiliza_listagem() {
		Map<String,String> res = new HashMap<>();
		for (Palete p : paletes.values()){
			if (p.getInRobot() == 0 || p.getInRobot() == 2)
				res.put(p.getCodPalete(),p.getLocalizacao().getDesignacao());
		}
		return  res;
	}

	public void registaPalete(String codQR, String codPalete , Vertice loc) throws QRCodeInvalido, PaleteJaExiste {
		if (!existeQRcode(codQR)) throw new QRCodeInvalido("Produto não faz parte do armazém!");
		if (paletes.containsKey(codPalete)) throw new PaleteJaExiste("Código da palete já existe no armazem!");
		else {
			this.paletes.put(codPalete, new Palete(codigosQR.get(codQR), codPalete, 0, loc));
		}
	}

	public void atualiza_localizacaoPalete(String codPalete, Vertice locPalete, int inRobot) {
		Palete p = paletes.get(codPalete);
		if(inRobot==-1) { // se for -1 atualiza apenas o vertice
			p.setLocalizacao(locPalete);
			p.setInRobot(0); //quando atualizamos um vertice nunca está num robot
		}
		else { // se for 0 ou 1 ou 2 atualiza o estado
			p.setInRobot(inRobot);
		}
		paletes.put(codPalete,p);
	}


	public Vertice getLocalizacaoPalete(String codPalete) {
		Palete p = paletes.get(codPalete);
		return p.getLocalizacao();

	}

	/**
	 * Método que verifica se há paletes no sistema
	 * @return true se há paletes registadas
	 */
	@Override
	public boolean haPaletes() {
		return !this.paletes.isEmpty();
	}

	public boolean existeQRcode(String qr){
		return codigosQR.containsKey(qr);
	}

	public List<String> getPaletesEmRobots(){
        List<String> res = new ArrayList<>();
        for (Palete p : paletes.values()) {
            if (p.getInRobot() == 1) res.add(p.getCodPalete());
        }
        return res;
    }

    public String paleteZonaD() throws SemPaletesParaTransportar {
		for (Palete p : paletes.values()) {
			if (p.getInRobot() == 0 && p.getLocalizacao().getCodVertice().equals("1")) return p.getCodPalete();
		}
		throw new SemPaletesParaTransportar("Não há paletes por atribuir.");
	}

	public boolean validaEstadoInRobot(String codPalete, int estado) throws NaoLevantouPalete {
		if(paletes.get(codPalete).getInRobot()==estado) return true;
		throw new NaoLevantouPalete("Ainda não recolheu a palete! O robot não tem Palete.");
	}

}