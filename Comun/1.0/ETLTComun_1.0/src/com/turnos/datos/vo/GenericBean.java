package com.turnos.datos.vo;

import java.text.DateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "objeto")
@JsonRootName(value = "objeto")
public class GenericBean extends ETLTBean {

	private Object objeto = null;
	private String tipo = "";
	
	public GenericBean() {
		super(GenericBean.class);
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Object getObjeto() {
		return objeto;
	}

	public void setObjeto(Object objeto) {
		if(objeto != null) {
			this.objeto = objeto;
			this.tipo = objeto.getClass().getCanonicalName();
		}
	}


	
	public void setObjeto(String objeto) {
		this.objeto = objeto;
		this.tipo = String.class.getCanonicalName();
	}
	
	@JsonIgnoreProperties
	public String getString() {
		if(objeto != null)
			return String.valueOf(objeto);
		else
			return null;
	}

	public void setObjeto(Integer objeto) {
		this.objeto = objeto;
		this.tipo = Integer.class.getCanonicalName();
	}

	@JsonIgnoreProperties
	public int getInteger() {
		if(objeto != null )
			try {
				int t = 0;
				if (objeto instanceof Integer) {
					t = ((Integer) objeto).intValue();
				} else {
					t = Integer.getInteger(objeto.toString(), 0).intValue();
				}
				return t;
			} catch (Exception e) {
				return 0;
			}
		else
			return 0;
	}

	public void setObjeto(Double objeto) {
		this.objeto = objeto;
		this.tipo = Double.class.getCanonicalName();
	}

	@JsonIgnoreProperties
	public double getDouble() {
		if(objeto != null)
			try {
				double t = 0;
				if (objeto instanceof Double) {
					t = ((Double) objeto).doubleValue();
				} else {
					t = Double.parseDouble(objeto.toString());
				}
				return t;
			} catch (Exception e) {
				return 0;
			}
		else
			return 0;
	}

	public void setObjeto(Boolean objeto) {
		this.objeto = objeto;
		this.tipo = Boolean.class.getCanonicalName();
	}

	@JsonIgnoreProperties
	public boolean getBoolean() {
		if(objeto != null)
			try {
				boolean b = false;
				if (objeto instanceof Boolean) {
					b = ((Boolean) objeto).booleanValue();
				} else {
					b = Boolean.parseBoolean(objeto.toString());
				}
				return b;
			} catch (Exception e) {
				return false;
			}
		else
			return false;
	}

	public void setObjeto(Date objeto) {
		this.objeto = objeto;
		this.tipo = Date.class.getCanonicalName();
	}

	@JsonIgnoreProperties
	public Date getDate() {
		if(objeto != null)
			try {
				Date d = null;
				if (objeto instanceof Date) {
					d = ((Date) objeto);
				} else {
					d = (DateFormat.getDateInstance()).parse(objeto.toString());
				}
				return d;
			} catch (Exception e) {
				return null;
			}
		else
			return null;
	}
	
	
}
