package GestPaletes;


import DataBase.PaleteDAO;
import DataBase.QRCodeDAO;
import Exceptions.QRCodeInvalido;
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
			if (p.isInRobot() == 0)
				res.put(p.getCodPalete(),p.getLocalizacao().getDesignacao());
		}
		return  res;
	}

	public void registaPalete(String codQR, String codPalete , Vertice loc) throws QRCodeInvalido {
		if (!existeQRcode(codQR)) throw new QRCodeInvalido("Produto não faz parte do armazém!");
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
            if (p.isInRobot() == 1) res.add(p.getCodPalete());
        }
        return res;
    }

    public String paleteZonaD(){
		String res = null;
		for (Palete p : paletes.values()) {
			if (p.getLocalizacao().getCodVertice().equals("1")) res = p.getCodPalete();
		}
		return res;
	}

}