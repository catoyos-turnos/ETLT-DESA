package com.turnos.datos.fabricas;

import javax.ws.rs.core.Response.Status;

import com.turnos.datos.vo.ErrorBean;

public class ErrorBeanFabrica {
	
	public static ErrorBean generaErrorBean(Status httpCode, String prefijo, int[] localizadores, String msg) {
		return generaErrorBean(null, httpCode, prefijo, localizadores, msg, null);
	}

	public static ErrorBean generaErrorBean(ErrorBean base, Status httpCode, String prefijo, int[] localizadores, String msg) {
		return generaErrorBean(base, httpCode, prefijo, localizadores, msg, null);
	}

	public static ErrorBean generaErrorBean(Status httpCode, String errorCode, String msg) {
		return generaErrorBean(null, httpCode, errorCode, msg, null);
	}

	public static ErrorBean generaErrorBean(ErrorBean base, Status httpCode, String errorCode, String msg) {
		return generaErrorBean(base, httpCode, errorCode, msg, null);
	}

	public static ErrorBean generaErrorBean(Status httpCode, String prefijo, int[] localizadores, String msg, String[] params) {
		return generaErrorBean(null, httpCode, prefijo, localizadores, msg);
	}

	public static ErrorBean generaErrorBean(ErrorBean base, Status httpCode, String prefijo, int[] localizadores, String msg, String[] params) {
		return generaErrorBean(base, httpCode, generaErrorCode(prefijo, localizadores), msg);
	}

	public static ErrorBean generaErrorBean(Status httpCode, String errorCode, String msg, String[] params) {
		return generaErrorBean(null, httpCode, errorCode, msg);
	}

	public static ErrorBean generaErrorBean(ErrorBean base, Status httpCode, String errorCode, String msg, String[] params) {
		if(base == null) base = new ErrorBean();
		
		switch (httpCode) {
		case ACCEPTED:
			generaACCEPTEDErrorBean(base, errorCode, msg, params);
			break;
		case BAD_REQUEST:
			generaBAD_REQUESTErrorBean(base, errorCode, msg, params);
			break;
		case CONFLICT:
			generaCONFLICTErrorBean(base, errorCode, msg, params);
			break;
		case CREATED:
			generaCREATEDErrorBean(base, errorCode, msg, params);
			break;
		case FORBIDDEN:
			generaFORBIDDENErrorBean(base, errorCode, msg, params);
			break;
		case INTERNAL_SERVER_ERROR:
			generaINTERNAL_SERVER_ERRORErrorBean(base, errorCode, msg, params);
			break;
		case NOT_FOUND:
			generaNOT_FOUNDErrorBean(base, errorCode, msg, params);
			break;
		case NOT_IMPLEMENTED:
			generaNOT_IMPLEMENTEDErrorBean(base, errorCode, msg, params);
			break;
		case NOT_MODIFIED:
			generaNOT_MODIFIEDErrorBean(base, errorCode, msg, params);
			break;
		case NO_CONTENT:
			generaNO_CONTENTErrorBean(base, errorCode, msg, params);
			break;
		case OK:
			generaOKErrorBean(base, errorCode, msg, params);
			break;
		case UNAUTHORIZED:
			generaUNAUTHORIZEDErrorBean(base, errorCode, msg, params);
			break;
			
		case BAD_GATEWAY:
		case EXPECTATION_FAILED:
		case FOUND:
		case GATEWAY_TIMEOUT:
		case GONE:
		case HTTP_VERSION_NOT_SUPPORTED:
		case LENGTH_REQUIRED:
		case METHOD_NOT_ALLOWED:
		case MOVED_PERMANENTLY:
		case NOT_ACCEPTABLE:
		case PARTIAL_CONTENT:
		case PAYMENT_REQUIRED:
		case PRECONDITION_FAILED:
		case PROXY_AUTHENTICATION_REQUIRED:
		case REQUESTED_RANGE_NOT_SATISFIABLE:
		case REQUEST_ENTITY_TOO_LARGE:
		case REQUEST_TIMEOUT:
		case REQUEST_URI_TOO_LONG:
		case RESET_CONTENT:
		case SEE_OTHER:
		case SERVICE_UNAVAILABLE:
		case TEMPORARY_REDIRECT:
		case UNSUPPORTED_MEDIA_TYPE:
		case USE_PROXY:
		default:
			generaDEFAULTErrorBean(base, httpCode, errorCode, msg, params);
			break;
		}
		
		return base;
	}
	
	public static String generaErrorCode(String prefijo, int[] localizadores) {
		String errorCode = prefijo==null?"":prefijo;
		for (int i = 0; i < localizadores.length; i++) {
			errorCode += String.format("%05d", localizadores[i]);
		}
		return errorCode;
	}

	public static ErrorBean generaACCEPTEDErrorBean(ErrorBean base, String errorCode, String msg, String[] params) {
		generaDEFAULTErrorBean(base, Status.ACCEPTED, errorCode, msg, params);
		// TODO Auto-generated method stub

		return base;
	}

	public static ErrorBean generaBAD_REQUESTErrorBean(ErrorBean base, String errorCode, String msg, String[] params) {
		generaDEFAULTErrorBean(base, Status.BAD_REQUEST, errorCode, msg, params);
		// TODO Auto-generated method stub

		return base;
	}

	public static ErrorBean generaCONFLICTErrorBean(ErrorBean base, String errorCode, String msg, String[] params) {
		generaDEFAULTErrorBean(base, Status.CONFLICT, errorCode, msg, params);
		// TODO Auto-generated method stub

		return base;
	}

	public static ErrorBean generaCREATEDErrorBean(ErrorBean base, String errorCode, String msg, String[] params) {
		generaDEFAULTErrorBean(base, Status.CREATED, errorCode, msg, params);
		// TODO Auto-generated method stub

		return base;
	}

	public static ErrorBean generaFORBIDDENErrorBean(ErrorBean base, String errorCode, String msg, String[] params) {
		generaDEFAULTErrorBean(base, Status.FORBIDDEN, errorCode, msg, params);
		// TODO Auto-generated method stub

		return base;
	}

	public static ErrorBean generaINTERNAL_SERVER_ERRORErrorBean(ErrorBean base, String errorCode, String msg, String[] params) {
		generaDEFAULTErrorBean(base, Status.INTERNAL_SERVER_ERROR, errorCode, msg, params);
		// TODO Auto-generated method stub

		return base;
	}

	public static ErrorBean generaNOT_FOUNDErrorBean(ErrorBean base, String errorCode, String msg, String[] params) {
		generaDEFAULTErrorBean(base, Status.NOT_FOUND, errorCode, msg, params);
		// TODO Auto-generated method stub

		return base;
	}

	public static ErrorBean generaNOT_IMPLEMENTEDErrorBean(ErrorBean base, String errorCode, String msg, String[] params) {
		generaDEFAULTErrorBean(base, Status.NOT_IMPLEMENTED, errorCode, msg, params);
		// TODO Auto-generated method stub

		return base;
	}

	public static ErrorBean generaNOT_MODIFIEDErrorBean(ErrorBean base, String errorCode, String msg, String[] params) {
		generaDEFAULTErrorBean(base, Status.NOT_MODIFIED, errorCode, msg, params);
		// TODO Auto-generated method stub

		return base;
	}

	public static ErrorBean generaNO_CONTENTErrorBean(ErrorBean base, String errorCode, String msg, String[] params) {
		generaDEFAULTErrorBean(base, Status.NO_CONTENT, errorCode, msg, params);
		// TODO Auto-generated method stub

		return base;
	}

	public static ErrorBean generaOKErrorBean(ErrorBean base, String errorCode, String msg, String[] params) {
		generaDEFAULTErrorBean(base, Status.OK, errorCode, msg, params);
		// TODO Auto-generated method stub

		return base;
	}

	public static ErrorBean generaUNAUTHORIZEDErrorBean(ErrorBean base, String errorCode, String msg, String[] params) {
		generaDEFAULTErrorBean(base, Status.UNAUTHORIZED, errorCode, msg, params);
		// TODO Auto-generated method stub
		
		return base;
	}

	public static ErrorBean generaDEFAULTErrorBean(ErrorBean base, Status httpCode,
			String errorCode, String msg, String[] params) {
		
		if(msg == null || "".equals(msg)){
			msg = "ERROR";
			if (params != null && params.length > 0) {
				msg += ": ";
				for (int i = 0; i < params.length - 1; i++) {
					msg += "%s, ";
				}
				msg += "%s";
			}
		}
		if (params != null && params.length > 0) {
			msg = String.format(msg, (Object[]) params);
		}

		base.setHttpCode(httpCode);
		base.updateErrorCode(errorCode);
		base.updateMsg(msg);
		
		return base;
	}
	
	
	
}
