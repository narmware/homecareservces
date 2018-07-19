package com.narmware.homecare.pojo;

/**
 * Created by rohitsavant on 10/05/18.
 */

public class SubServiceResponse {
    String response;
    SubServices[] data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public SubServices[] getData() {
        return data;
    }

    public void setData(SubServices[] data) {
        this.data = data;
    }
}
