package tech.w3ll.egameleague.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import tech.w3ll.egameleague.repositores.listas.UsuarioRepoList;



public class CadastroController {
    private UsuarioRepoList usuarioRepoList;

    @FXML
    private TextField emailTextField;
    @FXML
    private TextField usuarioTextField;
    @FXML
    private PasswordField senhaTextField;
    @FXML
    private TextField senhaVisivelTextField;
    @FXML
    private Button botaoOlhoAberto;
    @FXML
    private Button botaoOlhoFechado;
    @FXML
    private TextField telefoneTextField;

    @FXML
    private Button botaoCadastrar;

    public String getEmailTextField() {
        return emailTextField.getText();
    }

    public String getUsuarioTextField() {
        return usuarioTextField.getText();
    }

    public String getSenhaTextField() {
        return senhaTextField.isVisible() ? senhaTextField.getText() : senhaVisivelTextField.getText();
    }

    public String getTelefoneTextField() {
        return telefoneTextField.getText();
    }

    public void fazerCadastro() {
        usuarioRepoList = UsuarioRepoList.getInstace();
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
        switch (usuarioRepoList.fazerCadastro(getUsuarioTextField(), getEmailTextField(), getSenhaTextField(), getTelefoneTextField())) {
            case SUCESSO:
                botaoCadastrar.setText("Usuário cadastrado com sucesso!");
                pauseTransition.setOnFinished(event -> botaoCadastrar.setText("Cadastrar"));
                pauseTransition.play();
                break;

            case EMAIL_JA_CADASTRADO:
                botaoCadastrar.setText("Email já cadastrado!");
                pauseTransition.setOnFinished(event -> botaoCadastrar.setText("Cadastrar"));
                pauseTransition.play();
                break;

            case USUARIO_JA_CADASTRADO:
                botaoCadastrar.setText("Usuário já cadastrado!");
                pauseTransition.setOnFinished(event -> botaoCadastrar.setText("Cadastrar"));
                pauseTransition.play();
                break;

            case EMAIL_INVALIDO:
                botaoCadastrar.setText("Email inválido!");
                pauseTransition.setOnFinished(event -> botaoCadastrar.setText("Cadastrar"));
                pauseTransition.play();
                break;

            case USUARIO_INVALIDO:
                botaoCadastrar.setText("Usuário inválido!");
                pauseTransition.setOnFinished(event -> botaoCadastrar.setText("Cadastrar"));
                pauseTransition.play();
                break;

            case SENHA_INVALIDA:
                botaoCadastrar.setText("Senha inválida!");
                pauseTransition.setOnFinished(event -> botaoCadastrar.setText("Cadastrar"));
                pauseTransition.play();
                break;

            case TELEFONE_INVALIDO:
                botaoCadastrar.setText("Número de telefone inválido!");
                pauseTransition.setOnFinished(event -> botaoCadastrar.setText("Cadastrar"));
                pauseTransition.play();
                break;
        }
    }

    @FXML
    private void mostrarSenhaCadastro() {
        senhaVisivelTextField.setText(senhaTextField.getText());
        senhaTextField.setVisible(false);
        senhaTextField.setManaged(false);
        senhaVisivelTextField.setVisible(true);
        senhaVisivelTextField.setManaged(true);
        botaoOlhoAberto.setVisible(false);
        botaoOlhoAberto.setManaged(false);
        botaoOlhoFechado.setVisible(true);
        botaoOlhoFechado.setManaged(true);
    }

    @FXML
    private void esconderSenhaCadastro() {
        senhaTextField.setText(senhaVisivelTextField.getText());
        senhaVisivelTextField.setVisible(false);
        senhaVisivelTextField.setManaged(false);
        senhaTextField.setVisible(true);
        senhaTextField.setManaged(true);
        botaoOlhoFechado.setVisible(false);
        botaoOlhoFechado.setManaged(false);
        botaoOlhoAberto.setVisible(true);
        botaoOlhoAberto.setManaged(true);
    }
}