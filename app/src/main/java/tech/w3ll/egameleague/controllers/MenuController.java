package tech.w3ll.egameleague.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private void onLoginClicked(ActionEvent event) {
        openWindow("telas/cadastro/InterfaceGraficaLogin.fxml", "Login");
    }

    // Chamado ao clicar em “Usuários”
    @FXML
    private void onUsuariosClicked(ActionEvent e) {
        openWindow("telas/cadastro/interfaceGraficaUsuario.fxml", "Usuários");
    }

    @FXML
    private void onCadastroClicked(ActionEvent e) {
        openWindow("telas/cadastro/interfaceGraficaCadastro.fxml", "Cadastro");
    }

    @FXML
    private void onTorneiosClicked(ActionEvent e) {
        openWindow("telas/TelaTorneios.fxml", "Torneios");
    }

    @FXML
    private void onStatsClicked(ActionEvent e) {
        openWindow("telas/Estatisticas.fxml", "Estatísticas");
    }

    @FXML
    private void onGerenciamentoClicked(ActionEvent e) {
        openWindow("telas/Gerenciamento.fxml", "Gerenciamento");
    }
    @FXML
    private void onStatsCS2(ActionEvent e) {
        openWindow("telas/EstatisticasCS2.fxml", "Estatísticas CS2");
    }

    @FXML
    private void onStatsFIFA(ActionEvent e) {
        openWindow("telas/EstatisticasFIFA.fxml", "Estatísticas FIFA");
    }

    @FXML
    private void onStatsVALORANT(ActionEvent e) {
        openWindow("telas/EstatisticasValorant.fxml", "Estatísticas Valorant");
    }

    @FXML
    private void onSairClicked(ActionEvent e) {
        // Fecha a aplicação
        Stage s = (Stage)((javafx.scene.Node)e.getSource()).getScene().getWindow();
        s.close();
    }

    // Helper que carrega qualquer FXML em uma janela nova
    private void openWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/tech/w3ll/egameleague/" + fxmlPath)
            );
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("eGameLeague – " + title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            // opcional: mostrar um Alert com o erro
        }
    }
}
