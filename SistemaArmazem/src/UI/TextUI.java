package UI;

import Exceptions.*;
import GestPaletes.GestPaletesFacade;
import GestPaletes.IGestPaletesFacade;

import GestRobot.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class TextUI {
    // O model tem a 'lógica de negócio'.
    private IGestPaletesFacade modelP;
    private IGestRobotFacade modelR;

    // Scanner para leitura
    private Scanner scin;



    /**
     * Construtor.
     *
     * Cria os menus e a camada de negócio.
     */
    public TextUI() {

        this.modelP = new GestPaletesFacade();
        this.modelR = new GestRobotFacade();
        scin = new Scanner(System.in);
    }

    /**
     * Executa o menu principal e invoca o método correspondente à opção seleccionada.
     */
    public void run() {
        System.out.println("Bem vindo ao Sistema do Armazém!");
        this.menuPrincipal();
        System.out.println("Adeus!");
    }

    // Métodos auxiliares - Estados da UI

    /**
     * Estado - Menu Principal
     */
    private void menuPrincipal() {
        Menu menu = new Menu(new String[]{
                "Aceder à Gestão de Paletes",
                "Notificações de Robots"});

    // Registar pré-condições das transições
        //menu.setPreCondition(3, ()->this.modelP.haPaletes() && this.modelR.haRobots());

    // Registar os handlers
        menu.setHandler(1, ()->gestaoDePaletes());
        menu.setHandler(2, ()->gestaoDeRobots());
        //menu.setHandler(3, ()->comunicaOrdemTransporte());

        menu.run();
    }

    /**
     *  Estado - Gestão de Paletes
     */
    private void gestaoDePaletes() {
        Menu menu = new Menu(new String[]{
                "Registar nova palete",
                "Consultar listagem de localizações"
        });

        // Registar pré-condições das transições
        menu.setPreCondition(2, ()->this.modelP.haPaletes() && this.modelR.haRobots());

        // Registar os handlers
        menu.setHandler(1, this::comunicaCodigoQR);
        menu.setHandler(2, this::consultaListagem);

        menu.run();
    }

    /**
     *  Estado - Gestão de Robots
     */
    private void gestaoDeRobots() {
        Menu menu = new Menu(new String[]{
                "Notificar Recolha de Palete",
                "Notificar Entrega de Palete"
        });

        // Registar os handlers
        menu.setHandler(1, this::notificaRecolha);
        menu.setHandler(2, this::notificaEntrega);

        menu.run();
    }

    /**
     *  Estado - Comunica Código QR para o registo de uma nova palete
     */
    void comunicaCodigoQR(){
        try{
            System.out.println("Código QR lido: ");
            String qrCode = scin.nextLine();
            System.out.println("Inserir Código da Palete: ");
            String codPalete = scin.nextLine();
            try {
                this.modelP.registaPalete(qrCode,codPalete, modelR.getVerticeZonaDescarga());
                System.out.println("Registo Concluído!!!");
                System.out.println("Requisitando transporte...");
                comunicaOrdemTransporte(codPalete);
            }
            catch (QRCodeInvalido qr) {
                System.out.println(qr.getMessage());
            }

        }
        catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     *  Estado - Comunica Ordem de Transporte
     */

    void comunicaOrdemTransporte(String codPalete){
        //considerando que a origem é onde a palete está
        // temos de calcular a rota até à origem (locRobot -> origem) e depois dentro do metodo calcula rota chamamos o
        // alteraRota para dar a rota ao robot
        try {
            try {
                String codRobot = modelR.getRobotDisponivel();
                try {
                    String destino = modelR.getPrateleiraLivre();
                    modelR.notifica_transporte(codRobot,codPalete,modelP.getLocalizacaoPalete(codPalete));
                    modelR.indica_destino(codRobot,destino);
                    System.out.println("O Robot " + codRobot + " foi notificado!! Em breve irá começar o seu percurso.");
                    modelR.calcula_rota(codRobot,0);

                    System.out.println(codRobot+ ": A Iniciar Percurso...");

                }
                catch (EspacoInsuficienteNoArmazem e){
                    System.out.println(e.getMessage());
                }
            }
            catch (RobotsNaoDisponiveis e){
                System.out.println(e.getMessage());
            }

       } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     *  Estado - Notifica Recolha da Palete da sua origem
     */

    void notificaRecolha(){
        try{
            System.out.println("Código Robot: ");
            String codRobot = scin.nextLine();
            modelR.atualiza_LocalizacaoRobot(codRobot,0); //atualiza a localizacao para a origem (sitio da palete)
            modelP.atualiza_localizacaoPalete(modelR.getPaleteDoRobot(codRobot),null,1);
            System.out.println(codRobot+ ": Recolhi a Palete.");
            modelR.calcula_rota(codRobot,1);
            System.out.println(codRobot+ ": A Iniciar Percurso...");
        }
        catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     *  Estado - Notifica Entrega da Palete ao destino
     */

    void notificaEntrega(){
        try{
            System.out.println("Código Robot: ");
            String codRobot = scin.nextLine();
            System.out.println(codRobot+ ": Entreguei a Palete.");
            //Atualiza Estado do Robot
            modelR.atualiza_estadoRobot(codRobot,0);
            //Atualiza Localização do Robot
            modelR.atualiza_LocalizacaoRobot(codRobot,1);
            //Atualiza Localizacao da Palete
            String codPalete = modelR.getPaleteDoRobot(codRobot);
            modelP.atualiza_localizacaoPalete(codPalete,modelR.getLocalizacaoRobot(codRobot),-1);
            //Atualiza Estado do Vertice
            modelR.atualizaOcupacaoVertice(modelP.getLocalizacaoPalete(codPalete),1);
            //Retira Rota do Robot
            modelR.alteraRota(codRobot,null);
            System.out.println(codRobot+ ": Estou Disponível.");
            String paleteZonaD = modelP.paleteZonaD();
            if(paleteZonaD==null) System.out.println("Não há paletes por armazenar!");
            else{
                System.out.println("Ainda existem paletes na Zona de Descarga!");
                System.out.println(codRobot+ ": Nova Tarefa.");
                comunicaOrdemTransporte(paleteZonaD);
            }

        }
        catch (NullPointerException e){
            System.out.println(e.getMessage() + " wtf");
        }
    }

    /**
     *  Estado - Consulta Listagem da Localização das Paletes
     */

    void consultaListagem(){
        try {
            Map<String,String> resP = this.modelP.disponibiliza_listagem();
            Map<String,String> resPR = modelR.listagemPaletesInRobot(modelP.getPaletesEmRobots()); //lista das paletes que estão nos robots
            Tabela tabela = new Tabela("Listagem das Localizações das Paletes",new String[]{
                    "Código da Palete", "Localização"},2,resP);
            tabela.printaLinha("");
            tabela.printaTitulo();
            tabela.printaLinha("");
            tabela.printaSubtitulos();
            tabela.printaLinha("");
            tabela.printRes();
            tabela.setRes(resPR);
            tabela.printRes();
            tabela.printaLinha("");
            tabela.menu(0);
            tabela.run();

        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage()+ " ola");
        }
    }
}
