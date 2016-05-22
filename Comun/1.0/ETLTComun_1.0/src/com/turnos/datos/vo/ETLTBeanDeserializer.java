package com.turnos.datos.vo;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ETLTBeanDeserializer extends JsonDeserializer<ETLTBean> {

	public static final ETLTBeanMapperProvider mapperProvider = new ETLTBeanMapperProvider();
	@Override
	public ETLTBean deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		ObjectCodec oc = p.getCodec();
		JsonNode node = oc.readTree(p);
		String className = node.findValue("tipoBean").asText();
		Class<? extends ETLTBean> clz = null;
		try {
			if(ctxt.findClass(className) != null && ctxt.findClass(className) instanceof Class<?>)
				clz = (Class<? extends ETLTBean>) ctxt.findClass(className);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(clz != null) {
			return mapperProvider.getContext().treeToValue(node, clz);
		} else return null;
	}

}
