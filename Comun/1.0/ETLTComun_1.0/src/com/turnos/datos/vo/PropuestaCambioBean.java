package com.turnos.datos.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "propuestaCambio")
@JsonRootName(value = "propuestaCambio")
public class PropuestaCambioBean extends ETLTBean {
	private static final long serialVersionUID = 74L;
	public static enum EstadoPCS {
		PROPUESTO, ACEPTADO, RECHAZADO, CONFIRMADO, ANULADO, CADUCADO;
		public static EstadoPCS safeValueOf(String arg) {
			try {
				return valueOf(arg);
			} catch (Exception e) {
				return null;
			}
		}
	};

	private long id_propuesta = -1;
	private String codTrabProponente;
	private TrabajadorBean trabajadorProponente;
	private String codTrabPropuesto;
	private TrabajadorBean trabajadorPropuesto;
	private String codResidencia;
	private Date dia;
	private EstadoPCS estado;
	private String mensaje;
	private int numComentarios = -1;
	private List<ComentarioBean> comentarios;
	private Date hora_propuesta;
	private Date hora_actualizada;
	
	public PropuestaCambioBean() {
		super(PropuestaCambioBean.class);
	}

	public long getId_propuesta() {
		return id_propuesta;
	}

	public void setId_propuesta(long id_propuesta) {
		this.id_propuesta = id_propuesta;
	}

	public String getCodTrabProponente() {
		return codTrabProponente;
	}

	public void setCodTrabProponente(String codTrabProponente) {
		this.codTrabProponente = codTrabProponente;
	}

	public TrabajadorBean getTrabajadorProponente() {
		return trabajadorProponente;
	}

	public void setTrabajadorProponente(TrabajadorBean trabajadorProponente) {
		this.trabajadorProponente = trabajadorProponente;
	}

	public String getCodTrabPropuesto() {
		return codTrabPropuesto;
	}

	public void setCodTrabPropuesto(String codTrabPropuesto) {
		this.codTrabPropuesto = codTrabPropuesto;
	}

	public TrabajadorBean getTrabajadorPropuesto() {
		return trabajadorPropuesto;
	}

	public void setTrabajador(TrabajadorBean trabajadorPropuesto) {
		this.trabajadorPropuesto = trabajadorPropuesto;
	}

	public String getCodResidencia() {
		return codResidencia;
	}

	public void setCodResidencia(String codResidencia) {
		this.codResidencia = codResidencia;
	}


	public Date getDia() {
		return dia;
	}

	public void setDia(Date dia) {
		this.dia = dia;
	}

	@ApiModelProperty(allowableValues = "PROPUESTO,ACEPTADO,RECHAZADO,CONFIRMADO,ANULADO,CADUCADO")
	public String getEstado() {
		if (estado == null) return null;
		return estado.name();
	}

	@ApiModelProperty(allowableValues = "PROPUESTO,ACEPTADO,RECHAZADO,CONFIRMADO,ANULADO,CADUCADO")
	public void setEstado(String estado) {
		this.estado = EstadoPCS.safeValueOf(estado);
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getNumComentarios() {
		return numComentarios;
	}

	public void setNumComentarios(int numComentarios) {
		this.numComentarios = numComentarios;
	}

	public List<ComentarioBean> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<ComentarioBean> comentarios) {
		this.comentarios = comentarios;
	}

	public Date getHora_propuesta() {
		return hora_propuesta;
	}

	public void setHora_propuesta(Date hora_propuesta) {
		this.hora_propuesta = hora_propuesta;
	}

	public Date getHora_actualizada() {
		return hora_actualizada;
	}

	public void setHora_actualizada(Date hora_actualizada) {
		this.hora_actualizada = hora_actualizada;
	}

}
