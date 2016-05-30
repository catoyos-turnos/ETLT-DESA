package com.turnos.datos.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;

@XmlRootElement(name = "objeto")
@JsonRootName(value = "objeto")
@JsonInclude(Include.NON_NULL)
public abstract class ETLTBean implements Serializable {
	private static final long serialVersionUID = 74L;
	
	private String tipoBean;

	public ETLTBean(Class<?> clz) {
		this.tipoBean = clz.getCanonicalName();
	}

	public String getTipoBean() {
		return tipoBean;
	}
	
	public String toJSONString() {
		ObjectWriter ow = ETLTBeanDeserializer.mapperProvider.getContext().writer();
		try {
			return ow.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
}
