package tech.w3ll.egameleague.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tech.w3ll.egameleague.HelloApplication;
import tech.w3ll.egameleague.modelos.Partida;
import tech.w3ll.egameleague.modelos.Time;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


public class SelecionarVencedoresController {

    @FXML
    private VBox containerDePartidas;
    @FXML
    private Button botaoEmbaralhar;

    private List<Partida> partidasDaRodada;
    private TabelaController tabelaController;
    private boolean isEditingMode = false;

    public void carregarPartidas(List<Partida> partidas, TabelaController controller, boolean isEditing) {
        this.partidasDaRodada = partidas;
        this.tabelaController = controller;
        this.isEditingMode = isEditing;
        this.atualizarCards();
        this.verificarEstadoDoEmbaralhamento();
    }

    private void atualizarCards() {
        this.containerDePartidas.getChildren().clear();
        for (Partida partidaAtual : this.partidasDaRodada) {
            try {
                URL fxmlLocal = HelloApplication.class.getResource("/tech/w3ll/egameleague/torneio/partida-card.fxml");
                if (fxmlLocal == null) {
                    continue;
                }

                FXMLLoader carregador = new FXMLLoader(fxmlLocal);
                Parent noDoCard = carregador.load();

                PartidaCardController controladorDoCard = carregador.getController();
                controladorDoCard.initData(partidaAtual, this, this.isEditingMode);

                noDoCard.setUserData(controladorDoCard);
                this.containerDePartidas.getChildren().add(noDoCard);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void verificarEstadoDoEmbaralhamento() {
        boolean algumVencedorDefinido = this.partidasDaRodada.stream()
                .anyMatch(p -> p.getVencedor() != null);
        this.botaoEmbaralhar.setDisable(algumVencedorDefinido);
    }

    public void onVencedorSelecionado(Partida partida, Time vencedor) {
        partida.definirVencedor(vencedor);
        this.verificarEstadoDoEmbaralhamento();

        if (this.tabelaController != null) {
            this.tabelaController.habilitarBotaoDeEdicao();
            this.tabelaController.atualizarVisualDaTabelaAtiva();
        }

        for (javafx.scene.Node no : this.containerDePartidas.getChildren()) {
            if (no.getUserData() instanceof PartidaCardController) {
                final PartidaCardController controladorDoCard = (PartidaCardController) no.getUserData();
                if (controladorDoCard.getPartida().equals(partida)) {
                    Platform.runLater(controladorDoCard::atualizarVisual);
                    break;
                }
            }
        }

        if (!this.isEditingMode) {
            boolean todasAsPartidasDefinidas = this.partidasDaRodada.stream()
                    .allMatch(p -> p.getVencedor() != null || p.getTime2() == null);

            if (todasAsPartidasDefinidas) {
                if (this.tabelaController != null) {
                    this.tabelaController.rodadaConcluida();
                }
                Stage janela = (Stage) this.containerDePartidas.getScene().getWindow();
                janela.close();
            }
        }
    }

    @FXML
    void aoClicarEmbaralhar() {
        List<Time> timesNaRodada = new ArrayList<>();
        for (Partida p : this.partidasDaRodada) {
            if (p.getTime1() != null) timesNaRodada.add(p.getTime1());
            if (p.getTime2() != null) timesNaRodada.add(p.getTime2());
        }

        Collections.shuffle(timesNaRodada);

        List<Partida> novasPartidas = new ArrayList<>();
        for (int i = 0; i < timesNaRodada.size(); i += 2) {
            Time time1 = timesNaRodada.get(i);
            Time time2 = (i + 1 < timesNaRodada.size()) ? timesNaRodada.get(i + 1) : null;
            novasPartidas.add(new Partida(time1, time2));
        }

        this.partidasDaRodada.clear();
        this.partidasDaRodada.addAll(novasPartidas);

        this.atualizarCards();
        if (this.tabelaController != null) {
            this.tabelaController.atualizarVisualDaTabelaAtiva();
        }
    }
}