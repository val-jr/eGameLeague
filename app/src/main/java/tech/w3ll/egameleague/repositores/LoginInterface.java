package tech.w3ll.egameleague.repositores;

import tech.w3ll.egameleague.modelos.Usuario;

public interface LoginInterface {
    public Usuario fazerLogin(String email, String senha);
}
