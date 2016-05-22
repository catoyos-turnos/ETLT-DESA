package com.turnos.datos.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "mensaje")
@JsonRootName(value = "mensaje")
public class MensajeBean extends ETLTBean {
	private long id_mensaje = -1;
	private long id_remitente;
	private UsuarioBean remitente;
	private long id_destinatario;
	private UsuarioBean destinatario;
	private long id_msg_original = -1;
	private MensajeBean msgOriginal;
	private int numRespuestas = -1;
	private List<MensajeBean> respuestas;
	private Date hora;
	private String texto;
	private boolean leido;
	
	public MensajeBean() {
		super(MensajeBean.class);
	}

	public long getId_mensaje() {
		return id_mensaje;
	}

	public void setId_mensaje(long id_mensaje) {
		this.id_mensaje = id_mensaje;
	}
	
	public long getId_remitente() {
		return id_remitente;
	}

	public void setId_remitente(long id_remitente) {
		this.id_remitente = id_remitente;
	}

	public UsuarioBean getRemitente() {
		return remitente;
	}

	public void setRemitente(UsuarioBean remitente) {
		this.remitente = remitente;
	}

	public long getId_destinatario() {
		return id_destinatario;
	}

	public void setId_destinatario(long id_destinatario) {
		this.id_destinatario = id_destinatario;
	}

	public UsuarioBean getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(UsuarioBean destinatario) {
		this.destinatario = destinatario;
	}

	public long getId_msg_original() {
		return id_msg_original;
	}

	public void setIdMsgOriginal(long id_msg_original) {
		this.id_msg_original = id_msg_original;
	}

	public MensajeBean getMsgOriginal() {
		return msgOriginal;
	}

	public void setMsgOriginal(MensajeBean msgOriginal) {
		this.msgOriginal = msgOriginal;
	}

	public int getNumRespuestas() {
		return numRespuestas;
	}

	public void setNumRespuestas(int numRespuestas) {
		this.numRespuestas = numRespuestas;
	}

	public List<MensajeBean> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<MensajeBean> respuestas) {
		this.respuestas = respuestas;
	}

	public void addRespuesta(MensajeBean respuesta) {
		if(this.respuestas == null)
			this.respuestas = new ArrayList<MensajeBean>();
		this.respuestas.add(respuesta);
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public boolean isLeido() {
		return leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}

}
