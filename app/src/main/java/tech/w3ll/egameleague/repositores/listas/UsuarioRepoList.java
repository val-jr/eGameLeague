package tech.w3ll.egameleague.repositores.listas;

import tech.w3ll.egameleague.modelos.Usuario;
import tech.w3ll.egameleague.repositores.UsuarioInterface;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepoList implements UsuarioInterface {
    public static UsuarioRepoList usuarioRepoList;
     List<Usuario> listaUsuarios = new ArrayList<>();

    public static UsuarioRepoList getInstace(){
        if(usuarioRepoList == null){
            usuarioRepoList = new UsuarioRepoList();
        }
        return usuarioRepoList;
    }

    public List<Usuario> getListaUsuarios(){
        return listaUsuarios;
    }

    public enum ResultadoCadastro {
        SUCESSO,
        USUARIO_JA_CADASTRADO,
        EMAIL_JA_CADASTRADO,
        EMAIL_INVALIDO,
        USUARIO_INVALIDO,
        SENHA_INVALIDA,
        TELEFONE_INVALIDO
    }

    public ResultadoCadastro fazerCadastro(String usuario, String email, String senha, String telefone){
        if(buscarUsuarioPorUsuario(usuario) != null){
            return ResultadoCadastro.USUARIO_JA_CADASTRADO;
        }
        if(buscarUsuarioPorEmail(email) != null){
            return ResultadoCadastro.EMAIL_JA_CADASTRADO;
        }
        if(email.isBlank() || !validarEmail(email)){
            return ResultadoCadastro.EMAIL_INVALIDO;
        }
        if(usuario.isBlank()){
            return ResultadoCadastro.USUARIO_INVALIDO;
        }
        if(senha.isBlank()){
            return ResultadoCadastro.SENHA_INVALIDA;
        }
        if(telefone.isEmpty() || !validarTelefone(telefone)){
            return ResultadoCadastro.TELEFONE_INVALIDO;
        }
        Usuario usuarioCadastrado = new Usuario(usuario, email, senha, telefone);
        listaUsuarios.add(usuarioCadastrado);
        Sessao.setUsuarioLogado(usuarioCadastrado);
        return ResultadoCadastro.SUCESSO;
    }

    public boolean removerUsuario(Usuario usuarioRemover){
        boolean usuarioRemovido;
        try {
            listaUsuarios.removeIf(usuario -> usuarioRemover == usuario);
            return usuarioRemovido = true;
        } catch(Exception e){
            return usuarioRemovido = false;
        }
    }

    public Usuario buscarUsuarioPorUsuario(String usuario){
        for(Usuario usuarioBuscar : getListaUsuarios()){
            if(usuarioBuscar.getUsuario().equals(usuario)){
                return usuarioBuscar;
            }
        }
        return null;
    }

    public Usuario buscarUsuarioPorEmail(String email){
        for(Usuario usuarioBuscar : listaUsuarios){
            if(usuarioBuscar.getEmail().equals(email)){
                return usuarioBuscar;
            }
        }
        return null;
    }

    public Usuario buscarUsuarioParaLogin(String email, String senha){
        for(Usuario usuarioBuscar : listaUsuarios){
            if(usuarioBuscar.getEmail().equals(email) && usuarioBuscar.getSenha().equals(senha)){
                return usuarioBuscar;
            }
        }
        return null;
    }

    public boolean validarEmail(String email) {
        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    public boolean validarTelefone(String telefone) {
        return telefone.matches("^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$");
    }
}
