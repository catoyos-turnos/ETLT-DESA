package com.turnos.datos.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@XmlRootElement(name = "objeto")
@JsonRootName(value = "objeto")
@JsonInclude(Include.NON_NULL)
public abstract class ETLTBean {
	public ETLTBean() { }
	
	public String toJSONString() {
		ObjectWriter ow = new ObjectMapper().writer();
		try {
			return ow.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
}
