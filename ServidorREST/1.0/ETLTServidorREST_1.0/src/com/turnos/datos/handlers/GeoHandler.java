package com.turnos.datos.handlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.core.Response.Status;

import com.turnos.datos.vo.ErrorBean;
import com.turnos.datos.vo.MunicipioBean;
import com.turnos.datos.vo.PaisBean;
import com.turnos.datos.vo.ProvinciaBean;

public class GeoHandler extends GenericHandler {

	private static final int LOC_H = 60;
	
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

	public static ArrayList<PaisBean> listPaises(Connection conexion, int limite, int offset, ErrorBean errorBean) {
		int LOC_M = 1;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		ArrayList<PaisBean> listaPaises = new ArrayList<PaisBean>();
		PreparedStatement ps = null;
		ResultSet rs;
		try {
			ps = nconexion.prepareStatement(QUERY_GET_PAISES);
			rs = ps.executeQuery();
			PaisBean pais;
			while (rs.next()) {
				pais = new PaisBean();
				pais.setPaisCod(rs.getString("paisCod"));
				pais.setPaisNombre(rs.getString("paisNombre"));
				pais.setTz_estandar(rs.getString("tz"));
				listaPaises.add(pais);
			}
		} catch (SQLException e) {
			errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
			errorBean.updateErrorCode("69600100");
			errorBean.updateMsg(e.getMessage());
			e.printStackTrace();
		} finally {
			terminaOperacion(nconexion, cierraConexion);

		}
		return listaPaises;
	}

	public static PaisBean getPais(Connection conexion, String codPais, ErrorBean errorBean) {
		int LOC_M = 2;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		PaisBean pais = null;
		if (codPais != null && !"".equals(codPais)) {			
			PreparedStatement ps = null;
			ResultSet rs;
			try {
				ps = nconexion.prepareStatement(QUERY_GET_PAIS);
				ps.setString(1, codPais);
				rs = ps.executeQuery();
				if (rs.next()) {
					pais = new PaisBean();
					pais.setPaisCod(rs.getString("paisCod"));
					pais.setPaisNombre(rs.getString("paisNombre"));
					pais.setTz_estandar(rs.getString("tz"));
				} else {
					errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
					errorBean.updateErrorCode("69600202");
					errorBean.updateMsg("no encontrado pais con codigo " + codPais);
				}
			} catch (SQLException e) {
				errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
				errorBean.updateErrorCode("69600201");
				errorBean.updateMsg(e.getMessage());
				e.printStackTrace();
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
		} else {
			errorBean.setHttpCode(Status.BAD_REQUEST);
			errorBean.updateErrorCode("69600200");
			errorBean.updateMsg("debe incluir codigo");
		}
		return pais;
	}

	public static ArrayList<ProvinciaBean> listProvincias(Connection conexion, String codPais, int limite, int offset, ErrorBean errorBean) {
		int LOC_M = 3;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		ArrayList<ProvinciaBean> listaProvs = new ArrayList<ProvinciaBean>();
		PreparedStatement ps = null;
		ResultSet rs;
		try {
			ps = nconexion.prepareStatement(QUERY_GET_PROVINCIAS_PAIS);
			ps.setString(1, codPais);
			rs = ps.executeQuery();
			ProvinciaBean prov;
			while (rs.next()) {
				prov = new ProvinciaBean();
				prov.setProvinciaCod(rs.getString("provinciaCod"));
				prov.setProvinciaNombre(rs.getString("provinciaNombre"));
				prov.setPaisCod(rs.getString("paisCod"));
				prov.setPaisNombre(rs.getString("paisNombre"));
				prov.setTz(rs.getString("tz"));
				listaProvs.add(prov);
			}
		} catch (SQLException e) {
			errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
			errorBean.updateErrorCode("69600300");
			errorBean.updateMsg(e.getMessage());
			e.printStackTrace();
		} finally {
			terminaOperacion(nconexion, cierraConexion);

		}
		return listaProvs;
	}

	public static ProvinciaBean getProvincia(Connection conexion, String codProvincia, ErrorBean errorBean) {
		int LOC_M = 4;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		ProvinciaBean prov = null;
		if (codProvincia != null && !"".equals(codProvincia)) {			
			PreparedStatement ps = null;
			ResultSet rs;
			try {
				ps = nconexion.prepareStatement(QUERY_GET_PROVINCIA);
				ps.setString(1, codProvincia);
				rs = ps.executeQuery();
				if (rs.next()) {
					prov = new ProvinciaBean();
					prov.setProvinciaCod(rs.getString("provinciaCod"));
					prov.setProvinciaNombre(rs.getString("provinciaNombre"));
					prov.setPaisCod(rs.getString("paisCod"));
					prov.setPaisNombre(rs.getString("paisNombre"));
					prov.setTz(rs.getString("tz"));
				} else {
					errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
					errorBean.updateErrorCode("69600402");
					errorBean.updateMsg("no encontrada provincia con codigo " + codProvincia);
				}
			} catch (SQLException e) {
				errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
				errorBean.updateErrorCode("69600401");
				errorBean.updateMsg(e.getMessage());
				e.printStackTrace();
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
		} else {
			errorBean.setHttpCode(Status.BAD_REQUEST);
			errorBean.updateErrorCode("69600400");
			errorBean.updateMsg("debe incluir codigo");
		}
		return prov;
	}

	public static ArrayList<MunicipioBean> listMunicipios(Connection conexion, String codProv, int limite, int offset, ErrorBean errorBean) {
		int LOC_M = 5;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		ArrayList<MunicipioBean> listaMunicipios = new ArrayList<MunicipioBean>();
		PreparedStatement ps = null;
		ResultSet rs;
		try {
			ps = nconexion.prepareStatement(QUERY_GET_MUNICIPIOS_PROV);
			ps.setString(1, codProv);
			rs = ps.executeQuery();
			MunicipioBean muni;
			while (rs.next()) {
				muni = new MunicipioBean();
				muni.setMunicipioCod(rs.getString("municipioCod"));
				muni.setMunicipioNombre(rs.getString("municipioNombre"));
				muni.setProvinciaCod(rs.getString("provinciaCod"));
				muni.setProvinciaNombre(rs.getString("provinciaNombre"));
				muni.setPaisCod(rs.getString("paisCod"));
				muni.setPaisNombre(rs.getString("paisNombre"));
				muni.setTz(rs.getString("tz"));
				listaMunicipios.add(muni);
			}
		} catch (SQLException e) {
			errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
			errorBean.updateErrorCode("69600500");
			errorBean.updateMsg(e.getMessage());
			e.printStackTrace();
		} finally {
			terminaOperacion(nconexion, cierraConexion);

		}
		return listaMunicipios;
	}

	public static MunicipioBean getMunicipio(Connection conexion, String codMunicipio, ErrorBean errorBean) {
		int LOC_M = 6;
		Connection nconexion = aseguraConexion(conexion);
		boolean cierraConexion = (conexion == null) || (conexion != nconexion);

		MunicipioBean muni = null;
		if (codMunicipio != null && !"".equals(codMunicipio)) {			
			PreparedStatement ps = null;
			ResultSet rs;
			try {
				ps = nconexion.prepareStatement(QUERY_GET_MUNICIPIO);
				ps.setString(1, codMunicipio);
				rs = ps.executeQuery();
				if (rs.next()) {
					muni = new MunicipioBean();
					muni.setMunicipioCod(rs.getString("municipioCod"));
					muni.setMunicipioNombre(rs.getString("municipioNombre"));
					muni.setProvinciaCod(rs.getString("provinciaCod"));
					muni.setProvinciaNombre(rs.getString("provinciaNombre"));
					muni.setPaisCod(rs.getString("paisCod"));
					muni.setPaisNombre(rs.getString("paisNombre"));
					muni.setTz(rs.getString("tz"));
				} else {
					errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
					errorBean.updateErrorCode("69600602");
					errorBean.updateMsg("no encontrado municipio con codigo " + codMunicipio);
				}
			} catch (SQLException e) {
				errorBean.setHttpCode(Status.INTERNAL_SERVER_ERROR);
				errorBean.updateErrorCode("69600601");
				errorBean.updateMsg(e.getMessage());
				e.printStackTrace();
			} finally {
				terminaOperacion(nconexion, cierraConexion);
			}
		} else {
			errorBean.setHttpCode(Status.BAD_REQUEST);
			errorBean.updateErrorCode("69600600");
			errorBean.updateMsg("debe incluir codigo");
		}
		return muni;
	}
}
