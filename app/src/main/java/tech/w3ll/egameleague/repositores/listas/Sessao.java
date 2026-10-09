package tech.w3ll.egameleague.repositores.listas;

import tech.w3ll.egameleague.modelos.Usuario;

public class Sessao {
    private static Usuario usuarioLogado;

    public Usuario getUsuarioLogado(){
        return usuarioLogado;
    }

    public static void setUsuarioLogado(String email, String senha){
        usuarioLogado = UsuarioRepoList.getInstace().buscarUsuarioParaLogin(email, senha);
    }

    public static void setUsuarioLogado(Usuario usuarioLogado) {
        Sessao.usuarioLogado = usuarioLogado;
    }
}
