package com.narmware.doormojo.pojo;

/**
 * Created by rohitsavant on 14/05/18.
 */

public class MyBookings {
    String b_service_name,b_date,b_ratings,b_desc,b_price,b_service_img,b_status,order_id,b_no;

    public MyBookings(String order_id,String b_service_name, String b_date, String b_ratings, String b_desc, String b_price, String b_service_img, String b_status,String b_no) {
        this.b_service_name = b_service_name;
        this.b_date = b_date;
        this.b_ratings = b_ratings;
        this.b_desc = b_desc;
        this.b_price=b_price;
        this.b_service_img=b_service_img;
        this.b_status=b_status;
        this.order_id=order_id;
        this.b_no=b_no;
    }

    public String getB_no() {
        return b_no;
    }

    public void setB_no(String b_no) {
        this.b_no = b_no;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getB_price() {
        return b_price;
    }

    public void setB_price(String b_price) {
        this.b_price = b_price;
    }

    public String getB_service_name() {
        return b_service_name;
    }

    public void setB_service_name(String b_service_name) {
        this.b_service_name = b_service_name;
    }

    public String getB_date() {
        return b_date;
    }

    public void setB_date(String b_date) {
        this.b_date = b_date;
    }

    public String getB_ratings() {
        return b_ratings;
    }

    public void setB_ratings(String b_ratings) {
        this.b_ratings = b_ratings;
    }

    public String getB_desc() {
        return b_desc;
    }

    public void setB_desc(String b_desc) {
        this.b_desc = b_desc;
    }

    public String getB_service_img() {
        return b_service_img;
    }

    public void setB_service_img(String b_service_img) {
        this.b_service_img = b_service_img;
    }

    public String getB_status() {
        return b_status;
    }

    public void setB_status(String b_status) {
        this.b_status = b_status;
    }
}
