package tech.w3ll.egameleague.modelos;

public class Login{
    private String email;
    private String senha;

    public Login(String email, String senha){
        this.email = email;
        this.senha = senha;
    }

    public String getEmail(){
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

