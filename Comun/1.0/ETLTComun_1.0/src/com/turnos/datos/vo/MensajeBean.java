package com.turnos.datos.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "mensaje")
@JsonRootName(value = "mensaje")
public class MensajeBean extends ETLTBean {

}
