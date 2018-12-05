package com.narmware.doormojo.pojo;

/**
 * Created by rohitsavant on 10/05/18.
 */

public class MainServiceResponse {
    String response;
    ServiceMain[] data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public ServiceMain[] getData() {
        return data;
    }

    public void setData(ServiceMain[] data) {
        this.data = data;
    }
}
