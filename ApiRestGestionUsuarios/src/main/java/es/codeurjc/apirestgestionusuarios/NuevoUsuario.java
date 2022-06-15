package es.codeurjc.apirestgestionusuarios;

public class NuevoUsuario {

	public String login;
	public String name;
	public String contraseña;
	public double saldo;
	
	
	public NuevoUsuario() {}
	
	public NuevoUsuario(String login,String name,String contraseña,double saldo) {
		this.login=login;
		this.name = name; 
		this.saldo=saldo;
		this.contraseña=contraseña;
				
	}
	public String getName() {
		return name;
	}
	public String getLogin() {
		return login;
	}
	public String getContraseña() {
		return contraseña;
	}
	public double getSaldo() {
		return saldo;
	}
}
