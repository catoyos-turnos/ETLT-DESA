package com.turnos.restservice.filtros;

import com.turnos.datos.handlers.AutenticacionHandler;
import com.turnos.datos.vo.UsuarioBean;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import static jdk.nashorn.tools.ShellFunctions.input;

public class AutenticacionFilltro implements ContainerRequestFilter {
    private static final long MARGEN_LOGIN = 3600000;
    private static final long MARGEN_SESION = 16000000;
    
    @Override
    public void filter(ContainerRequestContext requestContext)
            throws IOException {

        List<String> publicKeyL = requestContext.getHeaders().get("publicKey");
        List<String> tokenSesionL = requestContext.getHeaders().get("tokenSesion");
        List<String> tokenLoginL = requestContext.getHeaders().get("tokenLogin");
        String publicKey = null;
        String tokenSesion = null;
        String tokenLogin = null;
        String secretKey = null;
        String servidorKey = null;
        long ahora = System.currentTimeMillis();

        if (publicKeyL != null && !publicKeyL.isEmpty()) {
            publicKey = publicKeyL.get(0);
            if (tokenSesionL != null && !tokenSesionL.isEmpty()) {
                tokenSesion = tokenSesionL.get(0);
            }
            if (tokenLoginL != null && !tokenLoginL.isEmpty()) {
                tokenLogin = tokenLoginL.get(0);
            }
            if (tokenSesion != null || tokenLogin != null) {
                String[] keys = AutenticacionHandler.getAuthKeys(publicKey);
                if (keys != null && keys.length >= 2) {
                    secretKey = keys[0];
                    servidorKey = keys[1];
                }
            }
        }

        /*
        requestContext.abortWith(Response
                .status(Response.Status.UNAUTHORIZED)
                .entity("User cannot access the resource.")
                .build());
         */
    }

    private static UsuarioBean usuarioDesdeLogin(String tokenLogin, String secretKey, String servidorKey, long ahora) {
        try{
            String desncr = desencripta(tokenLogin, secretKey);
            String[] fields = desncr.split("|");
            long time = Long.parseLong(fields[0]);
            if((ahora-time)<0){
                
            }else if((ahora-time)>MARGEN_LOGIN){
                
            } else {
                String user = fields[1];
                String passHash = fields[2];
                return AutenticacionHandler.getUsuarioPorUserPass(user, passHash);
            }
        } catch (Exception e) {
            ;
        }
        
        return null;
    }
    
    private static UsuarioBean usuarioDesdeSesion(String tokenSesion, String secretKey, String servidorKey, long ahora) {
        try{
            String desncrA = desencripta(tokenSesion, servidorKey);
            String[] fields = desncrA.split("|");
            long time = Long.parseLong(fields[0]);
            if((ahora-time)<0) {
                
            } else if((ahora-time)>MARGEN_SESION) {
                
            } else {
                String desncrB = desencripta(tokenSesion, servidorKey);
                String[] fieldsB = desncrB.split(":");
                UsuarioBean res = new UsuarioBean();
                res.setIdUsuario(ahora);
                res.setUser(desncrB);
                res.setCodTrab(desncrB);
                res.setCodRes(desncrB);
                res.setNombre(desncrB);
                res.setNivel(desncrB);
                res.setActivado(desncrB);
                res.
                return AutenticacionHandler.getUsuarioPorUserPass(user, passHash);
            }
        } catch (Exception e) {
            ;
        }
        
        return null;
    }

    public static String encripta(String str, String key) {
        try {
            byte[] strBytes = str.getBytes("utf-8");
            byte[] keyBytes = key.getBytes("utf-8");
            SecretKeySpec sks = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, sks);

            byte[] cipherText = new byte[cipher.getOutputSize(strBytes.length)];
            int ctLength = cipher.update(strBytes, 0, strBytes.length, cipherText, 0);
            ctLength += cipher.doFinal(cipherText, ctLength);
            String cipherTextStr = new String(cipherText);
            System.out.println(cipherTextStr);
            System.out.println(ctLength);
            
            return cipherTextStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String desencripta(String str, String key) {
        try {
            byte[] strBytes = str.getBytes("utf-8");
            byte[] keyBytes = key.getBytes("utf-8");
            SecretKeySpec sks = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
            
            int ctLength = str.length(); //???
            System.out.println(ctLength);
            
            cipher.init(Cipher.DECRYPT_MODE, sks);
            byte[] plainText = new byte[cipher.getOutputSize(ctLength)];
            int ptLength = cipher.update(strBytes, 0, ctLength, plainText, 0);
            ptLength += cipher.doFinal(plainText, ptLength);
            String plainTextStr = new String(plainText);
            System.out.println(plainTextStr);
            System.out.println(ptLength);
            
            return plainTextStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
