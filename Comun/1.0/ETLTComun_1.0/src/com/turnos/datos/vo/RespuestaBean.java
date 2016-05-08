package com.turnos.datos.vo;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement(name = "respuesta")
@JsonRootName(value = "respuesta")
@JsonInclude(Include.NON_NULL)
public class RespuestaBean<T extends ETLTBean> {
	private List<T> listaResultados;
	private ErrorBean error;
	private int htmlStatus = Status.OK.getStatusCode();
	private int offset;
	private int limite;
	private long timestamp;

	public RespuestaBean() {
		this.listaResultados = null;
		this.error = null;
	}

	public RespuestaBean(T resultado) {
		if(resultado != null) {
			this.listaResultados = new ArrayList<T>(1);
			this.listaResultados.add(resultado);
		}
	}

	public RespuestaBean(ErrorBean error) {
		this.error = error;
	}

	public RespuestaBean(List<T> listaResultados) {
		this.listaResultados = listaResultados;
	}

	public List<T> getListaResultados() {
		return listaResultados;
	}

	public void setListaResultados(List<T> listaResultados) {
		this.listaResultados = listaResultados;
	}

	@JsonIgnore
	public T getResultado() {
		if(listaResultados != null && !listaResultados.isEmpty())
			return listaResultados.get(0);
		else return null;
	}

	@JsonIgnore
	public void setResultado(T resultado) {
		if(listaResultados == null) {
			this.listaResultados = new ArrayList<T>(1);
		}
		this.listaResultados.add(resultado);
	}

	public ErrorBean getError() {
		return error;
	}

	public void setError(ErrorBean error) {
		this.error = error;
	}

	public Status getHtmlStatus() {
		if (error != null && error.getHttpCode() != null) {
			return error.getHttpCode();
		} else {
			return Status.fromStatusCode(htmlStatus);
		}
	}

	public void setHtmlStatus(int htmlStatus) {
		this.htmlStatus = htmlStatus;
		if (this.error != null && this.error.getHttpCode() != null) {
			this.error.setHttpCode(htmlStatus);
		}
	}

	public void setHtmlStatus(Status status) {
		this.setHtmlStatus(status.getStatusCode());
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
