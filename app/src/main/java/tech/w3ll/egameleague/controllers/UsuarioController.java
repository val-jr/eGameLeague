package tech.w3ll.egameleague.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import tech.w3ll.egameleague.repositores.listas.Sessao;
import javafx.fxml.Initializable;
import tech.w3ll.egameleague.repositores.listas.UsuarioRepoList;

import java.net.URL;
import java.util.ResourceBundle;

public class UsuarioController implements Initializable {
    private final Sessao sessao = new Sessao();

    @FXML
    private Label username;
    @FXML
    private Label usernamePerfil;
    @FXML
    private Label userEmail;
    @FXML
    private Label userTelefone;
    @FXML
    private TextField alterarUsuario;
    @FXML
    private Button btnAlterarUsuario;
    @FXML
    private TextField alterarTelefone;
    @FXML
    private Button btnAlterarTelefone;
    @FXML
    private PasswordField senhaAtual;
    @FXML
    private PasswordField alterarSenha;
    @FXML
    private Button btnAlterarSenha;
    @FXML
    private ImageView fotoDePerfil;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            username.setText(sessao.getUsuarioLogado().getUsuario());
            usernamePerfil.setText(sessao.getUsuarioLogado().getUsuario());
            userEmail.setText(sessao.getUsuarioLogado().getEmail());
            userTelefone.setText(sessao.getUsuarioLogado().getTelefone());
        } catch(Exception e){
            username.setText("Convidado");
            usernamePerfil.setText("Convidado");
            userEmail.setText("");
            userTelefone.setText("");
        }
    }

    public void alterarUsuario(){
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
        if(UsuarioRepoList.getInstace().buscarUsuarioPorUsuario(alterarUsuario.getText()) == null){

           UsuarioRepoList.getInstace().getListaUsuarios().remove
                   (UsuarioRepoList.getInstace().buscarUsuarioPorUsuario(sessao.getUsuarioLogado().getUsuario()));
           sessao.getUsuarioLogado().setUsuario(alterarUsuario.getText());
           UsuarioRepoList.getInstace().getListaUsuarios().add(sessao.getUsuarioLogado());
            System.out.println("usuario adicionado: " + UsuarioRepoList.getInstace().getListaUsuarios().getLast());

            username.setText(sessao.getUsuarioLogado().getUsuario());
            btnAlterarUsuario.setText("Alterado!");
            pauseTransition.setOnFinished(event -> btnAlterarUsuario.setText("Alterar"));
            pauseTransition.play();
        }
        else{
            btnAlterarUsuario.setText("Usuário já existe!");
            pauseTransition.setOnFinished(event -> btnAlterarUsuario.setText("Alterar"));
            pauseTransition.play();
        }
    }

    public void alterarTelefone(){
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
        if(UsuarioRepoList.getInstace().validarTelefone(alterarTelefone.getText())) {

            UsuarioRepoList.getInstace().getListaUsuarios().remove
                    (UsuarioRepoList.getInstace().buscarUsuarioPorUsuario(sessao.getUsuarioLogado().getUsuario()));
            sessao.getUsuarioLogado().setTelefone(alterarTelefone.getText());
            UsuarioRepoList.getInstace().getListaUsuarios().add(sessao.getUsuarioLogado());
            System.out.println("usuario adicionado: " + UsuarioRepoList.getInstace().getListaUsuarios().getLast());

            userTelefone.setText(alterarTelefone.getText());
            btnAlterarTelefone.setText("Alterado!");
        }
        else{
            btnAlterarTelefone.setText("Telefone inválido!");
        }
        pauseTransition.setOnFinished(event -> btnAlterarTelefone.setText("Alterar"));
        pauseTransition.play();
    }

    public void alterarSenha(){
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
        if(alterarSenha.getText().equals(senhaAtual.getText())){

            UsuarioRepoList.getInstace().getListaUsuarios().remove
                    (UsuarioRepoList.getInstace().buscarUsuarioPorUsuario(sessao.getUsuarioLogado().getUsuario()));
            sessao.getUsuarioLogado().setSenha(alterarTelefone.getText());
            UsuarioRepoList.getInstace().getListaUsuarios().add(sessao.getUsuarioLogado());
            System.out.println("usuario adicionado: " + UsuarioRepoList.getInstace().getListaUsuarios().getLast());

            sessao.getUsuarioLogado().setSenha(alterarSenha.getText());
            btnAlterarSenha.setText("Alterado!");
            pauseTransition.setOnFinished(event -> btnAlterarSenha.setText("Alterar"));
        }
        else{
            btnAlterarSenha.setText("Senhas diferem!");
        }
        pauseTransition.setOnFinished(event -> btnAlterarSenha.setText("Alterar"));
        pauseTransition.play();

    }
}
