package com.turnos.datos.handlers;

public class GeoHandler extends GenericHandler {
	private static final String QUERY_GET_MUNICIPIO =
			"SELECT muni.id_municipio as municipioCod, muni.nombre as municipioNombre, "
				+ "prov.id_provincia as provinciaCod, prov.provincia as provinciaNombre, "
				+ "pais.cod_pais as paisCod, pais.pais as paisNombre, prov.tz as tz "
			+ "FROM geo_municipio muni "
				+ "INNER JOIN geo_provincia prov ON muni.id_provincia=prov.id_provincia "
				+ "INNER JOIN geo_pais pais ON muni.cod_pais=pais.cod_pais "
			+ "WHERE muni.id_municipio=?";
	
	private static final String QUERY_GET_MUNICIPIOS_PROV =
			"SELECT muni.id_municipio as municipioCod, muni.nombre as municipioNombre, "
				+ "prov.id_provincia as provinciaCod, prov.provincia as provinciaNombre, "
				+ "pais.cod_pais as paisCod, pais.pais as paisNombre, prov.tz as tz "
			+ "FROM geo_municipio muni "
				+ "INNER JOIN geo_provincia prov ON muni.id_provincia=prov.id_provincia "
				+ "INNER JOIN geo_pais pais ON muni.cod_pais=pais.cod_pais "
			+ "WHERE prov.id_provincia=?";
	
	private static final String QUERY_GET_PROVINCIA =
			"SELECT prov.id_provincia as provinciaCod, prov.provincia as provinciaNombre, "
				+ "pais.cod_pais as paisCod, pais.pais as paisNombre, prov.tz as tz "
			+ "FROM geo_provincia prov "
				+ "INNER JOIN geo_pais pais ON prov.cod_pais=pais.cod_pais "
			+ "WHERE prov.id_provincia=?";
	
	private static final String QUERY_GET_PROVINCIAS_PAIS =
			"SELECT prov.id_provincia as provinciaCod, prov.provincia as provinciaNombre, "
				+ "pais.cod_pais as paisCod, pais.pais as paisNombre, prov.tz as tz "
			+ "FROM geo_provincia prov "
				+ "INNER JOIN geo_pais pais ON prov.cod_pais=pais.cod_pais "
			+ "WHERE pais.cod_pais=?";
	
	private static final String QUERY_GET_PAIS =
			"SELECT pais.cod_pais as paisCod, pais.pais as paisNombre, pais.tz_estandar as tz "
			+ "FROM geo_pais pais WHERE pais.cod_pais=?";
	
	private static final String QUERY_GET_PAISES =
			"SELECT pais.cod_pais as paisCod, pais.pais as paisNombre, pais.tz_estandar as tz "
			+ "FROM geo_pais pais";
	
}
