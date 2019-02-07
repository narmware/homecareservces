package com.narmware.doormojo.pojo;

/**
 * Created by rohitsavant on 10/05/18.
 */

public class BookScheduleResponse {
    String response;
    int booking_id;

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
