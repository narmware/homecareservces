package com.narmware.doormojo.pojo;

/**
 * Created by rohitsavant on 05/05/18.
 */

public class SubServices {
    String sub_service_url,sub_service_name,sub_service_id,cat_id,sub_service_desc;

    public SubServices(String ser_img_url, String ser_name, String sub_service_id, String main_service_id,String sub_service_desc) {
        this.sub_service_url = ser_img_url;
        this.sub_service_name = ser_name;
        this.sub_service_id = sub_service_id;
        this.cat_id = main_service_id;
        this.sub_service_desc=sub_service_desc;
    }

    public String getSer_img_url() {
        return sub_service_url;
    }

    public void setSer_img_url(String ser_img_url) {
        this.sub_service_url = ser_img_url;
    }

    public String getSer_name() {
        return sub_service_name;
    }

    public void setSer_name(String ser_name) {
        this.sub_service_name = ser_name;
    }

    public String getSub_service_id() {
        return sub_service_id;
    }

    public void setSub_service_id(String sub_service_id) {
        this.sub_service_id = sub_service_id;
    }

    public String getMain_service_id() {
        return cat_id;
    }

    public void setMain_service_id(String main_service_id) {
        this.cat_id = main_service_id;
    }

    public String getSub_service_desc() {
        return sub_service_desc;
    }

    public void setSub_service_desc(String sub_service_desc) {
        this.sub_service_desc = sub_service_desc;
    }
}
