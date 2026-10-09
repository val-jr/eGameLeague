package tech.w3ll.egameleague.repositores;

import tech.w3ll.egameleague.modelos.Usuario;
import tech.w3ll.egameleague.repositores.listas.UsuarioRepoList;

public interface UsuarioInterface {
    UsuarioRepoList.ResultadoCadastro fazerCadastro(String usuario, String email, String senha, String telefone);
    boolean removerUsuario(Usuario usuarioRemover);
    Usuario buscarUsuarioParaLogin(String email, String senha);
}
