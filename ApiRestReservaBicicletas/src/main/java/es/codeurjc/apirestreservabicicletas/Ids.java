package es.codeurjc.apirestreservabicicletas;

public class Ids {

	public long id_bici;
	public long id_estacion;
	public long id_usuario;
	
	public Ids(long id_bici, long id_estacion, long id_usuario) {
		this.id_bici=id_bici;
		this.id_estacion=id_estacion;
		this.id_usuario=id_usuario;
	}
	public long getId_bici() {
		return id_bici;
	}
	public long getId_estacion() {
		return id_estacion;
	}
	public long getId_usuario() {
		return id_usuario;
	}
	
}
