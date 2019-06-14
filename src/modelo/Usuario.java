package modelo;

public class Usuario {
	
	private int role;
	private String usuario;
	private String senha;
	
	public Usuario(int role, String usuario, String senha) {
		this.role = role;
		this.usuario = usuario;
		this.senha = senha;
	}
	
	
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}
