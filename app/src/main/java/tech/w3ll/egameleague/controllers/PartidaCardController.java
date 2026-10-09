package tech.w3ll.egameleague.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import tech.w3ll.egameleague.modelos.Partida;
import tech.w3ll.egameleague.modelos.Time;

public class PartidaCardController {

    @FXML
    private VBox containerCard;
    @FXML
    private Label rotuloTime1;
    @FXML
    private Label rotuloTime2;
    @FXML
    private Label rotuloStatus;

    private Partida partida;
    private SelecionarVencedoresController parentController;
    private boolean isEditingMode = false;

    public Partida getPartida() {
        return this.partida;
    }

    public void initData(Partida partida, SelecionarVencedoresController controller, boolean isEditing) {
        this.partida = partida;
        this.parentController = controller;
        this.isEditingMode = isEditing;
        this.atualizarVisual();
    }

    public void atualizarVisual() {
        this.rotuloTime1.getStyleClass().removeAll("winner", "loser", "selected-team");
        this.rotuloTime2.getStyleClass().removeAll("winner", "loser", "selected-team");

        this.rotuloTime1.setText(this.partida.getTime1().getNome());
        if (this.partida.getTime2() != null) {
            this.rotuloTime2.setText(this.partida.getTime2().getNome());
            this.rotuloTime2.setVisible(true);
        } else {
            this.rotuloTime2.setText("");
            this.rotuloTime2.setVisible(false);
        }

        this.rotuloStatus.getStyleClass().removeAll("status-live", "status-finished");

        if (this.partida.getVencedor() != null) {
            this.rotuloStatus.setText("Encerrada");
            this.rotuloStatus.getStyleClass().add("status-finished");

            if (this.partida.getVencedor().equals(this.partida.getTime1())) {
                this.rotuloTime1.getStyleClass().add("winner");
                this.rotuloTime2.getStyleClass().add("loser");
            } else {
                this.rotuloTime2.getStyleClass().add("winner");
                this.rotuloTime1.getStyleClass().add("loser");
            }

        } else if (this.partida.getTime2() == null) {
            this.rotuloStatus.setText("AVANÇOU");
            this.rotuloStatus.getStyleClass().add("status-live");
        } else {
            this.rotuloStatus.setText("Aguardando");
        }
    }

    private void tentarSelecionarVencedor(Time time) {
        boolean podeSelecionar = false;

        if (isEditingMode) {
            if (partida.getVencedor() != null) {
                podeSelecionar = true;
            }
        } else {
            if (partida.getVencedor() == null) {
                podeSelecionar = true;
            }
        }

        if (podeSelecionar) {
            this.parentController.onVencedorSelecionado(this.partida, time);
        }
    }

    @FXML
    void aoClicarTime1(MouseEvent event) {
        tentarSelecionarVencedor(this.partida.getTime1());
    }

    @FXML
    void aoClicarTime2(MouseEvent event) {
        if (this.partida.getTime2() != null) {
            tentarSelecionarVencedor(this.partida.getTime2());
        }
    }
}