package com.turnos.datos.handlers;

public class GeoHandler extends GenericHandler {
	private static final String QUERY_GET_GEO_INFO =
		"SELECT muni.id_municipio as municipioCod, muni.nombre as municipioNombre, "
			+ "prov.id_provincia as provinciaCod, prov.provincia as provinciaNombre, "
			+ "pais.cod_pais as paisCod, pais.pais as paisNombre, prov.tz as tz "
		+ "FROM geo_municipio muni, geo_provincia prov, geo_pais pais "
		+ "WHERE muni.id_municipio=? AND muni.id_provincia=prov.id_provincia AND muni.cod_pais=pais.cod_pais";
	
}
