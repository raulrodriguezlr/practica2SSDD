package es.codeurjc.apirestgestionusuarios;

public class MostrarUsuario {
	
		public long id;
		public String login;
		public String name;
		public String fechaAlta;
		public String estado;
		public double saldo;	
		public MostrarUsuario() {}
		
		public MostrarUsuario(long id,String login,String name,String fecha,String estado,double saldo) {
			this.id=id;
			this.login=login;
			this.name = name; 
			this.fechaAlta = fecha;
			this.estado=estado;
			this.saldo=saldo;
					
		}
}
