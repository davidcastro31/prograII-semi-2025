package com.ugb.miprimeraaplicacion;

import java.util.Base64;

public class utilidades {
    static String url_consulta = "http://192.168.81.31:5984/david/_design/gribel/_view/gribel";
    static String url_mto = "http://192.168.81.31:5984/david";
    static String user = "admin";
    static String passwd = "guilledavid31";
    static String credencialesCodificadas = Base64.getEncoder().encodeToString((user + ":" + passwd).getBytes());
    public String generarUnicoId(){
        return java.util.UUID.randomUUID().toString();
    }
}
