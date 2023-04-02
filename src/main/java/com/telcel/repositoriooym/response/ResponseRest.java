package com.telcel.repositoriooym.response;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author marcos.hernandez
 */

public class ResponseRest {

    /**
     * Metadata del response
     */
    private ArrayList<HashMap<String, String>> metadata = new ArrayList<>();

    /**
     * Metodo que obtiene la metadata del response
     * @return la metadata del response
     */
    public ArrayList<HashMap<String, String>> getMetadata() {
        return this.metadata;
    }

    /**
     * Metodo que setea los valores del response
     * @param type tipo del response
     * @param code codigo del response
     * @param data data del response
     */
    public void setMetadata(String type, String code, String data) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", type);
        map.put("code", code);
        map.put("data", data);

        metadata.add(map);
    }

}
