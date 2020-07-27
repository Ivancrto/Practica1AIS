package es.codeurjc.ais.tictactoe;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ChatBD {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String nombre;
	private String mensaje;
	private String fechaYHora;
	
	public ChatBD() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getFechaYHora() {
		return fechaYHora;
	}
	public void setFechaYHora(String fechaYHora) {
		this.fechaYHora = fechaYHora;
	}	
	
	
}
