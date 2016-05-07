package com.turnos.datos.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "vacaciones")
@JsonRootName(value = "vacaciones")
public class VacacionesBean extends ETLTBean {

}
