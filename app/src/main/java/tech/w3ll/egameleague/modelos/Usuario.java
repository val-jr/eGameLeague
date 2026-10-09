package tech.w3ll.egameleague.modelos;

import java.util.Scanner;

public class Usuario {
    Scanner scan = new Scanner(System.in);

    private String usuario;
    private String email;
    private String senha;
    private String telefone;

    public Usuario(String usuario, String email, String senha, String telefone) {
        this.usuario = usuario;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
    }

    public Usuario(){
        this.usuario = "Convidado";
        this.email = null;
        this.senha = null;
        this.telefone = null;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha() {
        System.out.print("Digite sua senha atual: ");
        String senhaAtual = scan.nextLine();
        if (senhaAtual.equals(this.senha)) {
            System.out.print("Digite a nova senha: ");
            senha = scan.nextLine();

            if (senha.equals(senhaAtual)) {
                do {
                    System.out.println("Digite uma senha diferente da atual!");
                    System.out.print("Digite a nova senha: ");
                    senha = scan.nextLine();
                } while (senha.equals(senhaAtual));
            }
        } else {
            System.out.println("Senha incorreta!");
        }
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String toString(){
        return "\nUsuário: " + usuario + "\nEmail: " + email + "\nSenha: "+ senha
        + "\nContato: " + telefone;
    }
}