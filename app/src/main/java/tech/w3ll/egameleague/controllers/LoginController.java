package tech.w3ll.egameleague.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import tech.w3ll.egameleague.modelos.Usuario;
import tech.w3ll.egameleague.repositores.listas.LoginRepoList;
import tech.w3ll.egameleague.repositores.listas.UsuarioRepoList;


public class LoginController {
    private LoginRepoList loginRepoList = new LoginRepoList(UsuarioRepoList.getInstace());

    @FXML
    private TextField emailLoginTextField;
    @FXML
    private PasswordField senhaLoginTextField;
    @FXML
    private TextField senhaLoginVisivelTextField;
    @FXML
    private Button botaoOlhoAbertoLogin;
    @FXML
    private Button botaoOlhoFechadoLogin;
    @FXML
    private Button botaoFazerLogin;

    public TextField getSenhaLoginTextField() {
        return senhaLoginTextField.isVisible() ? senhaLoginTextField : senhaLoginVisivelTextField;
    }

    public TextField getEmailLoginTextField() {
        return emailLoginTextField;
    }

    public Usuario fazerLogin() {
        String email = getEmailLoginTextField().getText();
        String senha = getSenhaLoginTextField().getText();
        Usuario usuario = loginRepoList.fazerLogin(email, senha);

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));

        if(usuario == null){
            botaoFazerLogin.setText("E-mail ou senha incorretos!");
            pauseTransition.setOnFinished(event -> botaoFazerLogin.setText("Fazer login"));
            pauseTransition.play();
            return null;
        } else{
            botaoFazerLogin.setText("Login efetuado com sucesso!");
            pauseTransition.setOnFinished(event -> botaoFazerLogin.setText("Fazer login"));
            pauseTransition.play();
            return usuario;
        }
    }

    @FXML
    private void mostrarSenhaLogin() {
        senhaLoginVisivelTextField.setText(senhaLoginTextField.getText());
        senhaLoginTextField.setVisible(false);
        senhaLoginTextField.setManaged(false);
        senhaLoginVisivelTextField.setVisible(true);
        senhaLoginVisivelTextField.setManaged(true);
        botaoOlhoAbertoLogin.setVisible(false);
        botaoOlhoAbertoLogin.setManaged(false);
        botaoOlhoFechadoLogin.setVisible(true);
        botaoOlhoFechadoLogin.setManaged(true);
    }

    @FXML
    private void esconderSenhaLogin() {
        senhaLoginTextField.setText(senhaLoginVisivelTextField.getText());
        senhaLoginVisivelTextField.setVisible(false);
        senhaLoginVisivelTextField.setManaged(false);
        senhaLoginTextField.setVisible(true);
        senhaLoginTextField.setManaged(true);
        botaoOlhoFechadoLogin.setVisible(false);
        botaoOlhoFechadoLogin.setManaged(false);
        botaoOlhoAbertoLogin.setVisible(true);
        botaoOlhoAbertoLogin.setManaged(true);
    }
}
