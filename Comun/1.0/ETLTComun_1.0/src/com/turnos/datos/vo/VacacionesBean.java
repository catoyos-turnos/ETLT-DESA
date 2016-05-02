package com.turnos.datos.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement(name = "vacaciones")
@JsonRootName(value = "vacaciones")
@JsonInclude(Include.NON_NULL)
public class VacacionesBean {

}
