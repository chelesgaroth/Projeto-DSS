package UI;//import dos sistemas onde estao os facades

import GestPaletes.GestPaletesFacade;
import GestPaletes.IGestPaletesFacade;
import GestRobot.GestRobotFacade;
import GestRobot.IGestRobotFacade;

import java.util.Scanner;

/**
 * Exemplo de interface em modo texto.
 *
 * @author Grupo19
 */
public class TextUI {
    // O model tem a 'lógica de negócio'.
    private IGestRobotFacade modelR;
    private IGestPaletesFacade modelP;

    // Menus da aplicação
    private Menu menu;

    // Scanner para leitura
    private Scanner scin;

    /**
     * Construtor.
     * <p>
     * Cria os menus e a camada de negócio.
     */
    public TextUI() {
        // Criar o menu
        String[] opcoes = {
                "Comunicar código QR",
                "Comunicar ordem de transporte",
                "Notificar recolha de paletes",
                "Notificar entrega de paletes",
                "Consultar listagem de localizações"};
        this.menu = new Menu(opcoes);
        this.modelR = new GestRobotFacade();
        this.modelP = new GestPaletesFacade();
        scin = new Scanner(System.in);
    }

    /**
     * Executa o menu principal e invoca o método correspondente à opção seleccionada.
     */
    public void run() {
        do {
            menu.executa();
            switch (menu.getOpcao()) {
                case 1:
                    comunicaCodigoQR();
                    break;
                case 2:
                    comunicaOrdemTransporte();
                    break;
                case 3:
                    notificaRecolha();
                    break;
                case 4:
                    notificaEntrega();
                    break;
                case 5:
                    consultaListagem();
            }
        } while (menu.getOpcao() != 0); // A opção 0 é usada para sair do menu.
        System.out.println("Adeus!");
    }

    void comunicaCodigoQR(){}
    void comunicaOrdemTransporte(){}
    void notificaRecolha(){}
    void notificaEntrega(){}
    void consultaListagem(){}

}
