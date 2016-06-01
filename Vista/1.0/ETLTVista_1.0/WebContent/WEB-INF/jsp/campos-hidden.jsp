<input type="hidden" id="VAL_RAIZ" name="VAL_RAIZ" value="${_raiz}">
<input type="hidden" id="VAL_ID_PAG" name="VAL_ID_PAG" value="${_id_pag}">
<input type="hidden" id="VAL_USER_ID" name="VAL_USER_ID" value="${usuario.idUsuario}">
<input type="hidden" id="VAL_CODRES" name="VAL_CODRES" value="${(not empty residencia)?residencia.codigo:''}">
<input type="hidden" id="VAL_CODTRAB" name="VAL_CODTRAB" value="${(not empty trabajador)?trabajador.codigo:''}">
<input type="hidden" id="VAL_HOY" name="VAL_HOY" value="${(not empty hoy)?sdfIn.format(hoy):''}}">