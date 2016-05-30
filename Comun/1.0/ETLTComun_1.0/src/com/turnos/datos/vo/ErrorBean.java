package com.turnos.datos.vo;

import java.io.Serializable;

import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement(name = "error")
@JsonRootName(value = "error")
@JsonInclude(Include.NON_NULL)
public class ErrorBean implements Serializable {
	private static final long serialVersionUID = 74L;
	private Status httpCode;
	private String errorCode;
	private String msg;

	public ErrorBean() {
		this(Status.OK, "", "");
	}

	public ErrorBean(int httpCode, String errorCode, String msg) {
		this(Status.fromStatusCode(httpCode), errorCode, msg);
	}
	
	public ErrorBean(Status httpCode, String errorCode, String msg) {
		this.httpCode = httpCode;
		this.errorCode = errorCode;
		this.msg = msg;
	}

	public Status getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(Status status) {
		this.httpCode = status;
	}

	public void setHttpCodeNum(int status) {
		this.httpCode = Status.fromStatusCode(status);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void updateErrorCode(String errorCode) {
		if(this.errorCode == null || "".equals(this.errorCode)) {
			this.errorCode = errorCode;
		} else {
			this.errorCode += "," + errorCode;
		}
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void updateMsg(String msg) {
		if(this.msg == null || "".equals(this.msg)) {
			this.msg = msg;
		} else {
			this.msg += "\n-->\n" + msg;
		}
	}

}
