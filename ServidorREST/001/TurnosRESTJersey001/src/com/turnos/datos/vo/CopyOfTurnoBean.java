package com.turnos.datos.vo;

/*
@XmlRootElement(name = "turno")
@JsonRootName(value = "turno")
*/
public class CopyOfTurnoBean {
	private String codTrab;
	private String nomTrab;
	private String apeTrab;
	private String codTurno;
	private String codServicio;
	private String horaPresServ;
	private String horaRetServ;
	private String descServ;

	public String getCodTrab() {
		return codTrab;
	}

	public void setCodTrab(String codTrab) {
		this.codTrab = codTrab;
	}

	public String getNomTrab() {
		return nomTrab;
	}

	public void setNomTrab(String nomTrab) {
		this.nomTrab = nomTrab;
	}

	public String getApeTrab() {
		return apeTrab;
	}

	public void setApeTrab(String apeTrab) {
		this.apeTrab = apeTrab;
	}

	public String getCodTurno() {
		return codTurno;
	}

	public void setCodTurno(String codTurno) {
		this.codTurno = codTurno;
	}

	public String getCodServicio() {
		return codServicio;
	}

	public void setCodServicio(String codServicio) {
		this.codServicio = codServicio;
	}

	public String getHoraPresServ() {
		return horaPresServ;
	}

	public void setHoraPresServ(String horaPresServ) {
		this.horaPresServ = horaPresServ;
	}

	public String getHoraRetServ() {
		return horaRetServ;
	}

	public void setHoraRetServ(String horaRetServ) {
		this.horaRetServ = horaRetServ;
	}

	public String getDescServ() {
		return descServ;
	}

	public void setDescServ(String descServ) {
		this.descServ = descServ;
	}

	@Override
	public String toString() {
		return "TurnoBean [codTrab=" + codTrab + ", nomTrab=" + nomTrab + ", apeTrab=" + apeTrab
				+ ", codTurno=" + codTurno + ", codServicio=" + codServicio
				+ ", horaPresServ=" + horaPresServ + ", horaRetServ=" + horaRetServ
				+ ", descServ=" + descServ + "]";
	}

}
