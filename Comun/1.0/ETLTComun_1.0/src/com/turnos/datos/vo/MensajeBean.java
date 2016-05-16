package com.turnos.datos.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "mensaje")
@JsonRootName(value = "mensaje")
public class MensajeBean extends ETLTBean {
	private long idMensaje = -1;
	private UsuarioBean remitente;
	private UsuarioBean destinatario;
	private long idMsgOriginal = -1;
	private long idMsgOriginal = -1;
	private MensajeBean msgOriginal;
	private int numRespuestas = -1;
	private List<MensajeBean> respuestas;
	private Date hora;
	private String texto;
	private boolean leido;

	public long getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(long idMensaje) {
		this.idMensaje = idMensaje;
	}

	public UsuarioBean getRemitente() {
		return remitente;
	}

	public void setRemitente(UsuarioBean remitente) {
		this.remitente = remitente;
	}

	public UsuarioBean getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(UsuarioBean destinatario) {
		this.destinatario = destinatario;
	}

	public long getIdMsgOriginal() {
		return idMsgOriginal;
	}

	public void setIdMsgOriginal(long idMsgOriginal) {
		this.idMsgOriginal = idMsgOriginal;
	}

	public MensajeBean getMsgOriginal() {
		return msgOriginal;
	}

	public void setMsgOriginal(MensajeBean msgOriginal) {
		this.msgOriginal = msgOriginal;
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
