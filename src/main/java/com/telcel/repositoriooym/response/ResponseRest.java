package com.telcel.repositoriooym.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author marcos.hernandez
 */

public class ResponseRest {

    /**
     * Metadata del response
     */
    private ArrayList<HashMap<String, String>> metadata = new ArrayList<>();

    /**
     * Nuevo response
     */
    private ArrayList<Map<String, String>> metaList = new ArrayList<>();

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

    /**
     * Nuevo get de MetaList
     * @return
     */
    public List<Map<String, String>> getMetaList() {
        return this.metaList;
    }

    /**
     * Nuevo set de MetaList
     * @param metaList
     */
    public void setMetaList(ArrayList<Map<String, String>> metaList) {
        this.metaList = metaList;
    }
}
