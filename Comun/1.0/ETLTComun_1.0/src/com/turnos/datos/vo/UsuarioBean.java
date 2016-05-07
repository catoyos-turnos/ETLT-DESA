package com.turnos.datos.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "usuario")
@JsonRootName(value = "usuario")
public class UsuarioBean extends ETLTBean {

}
