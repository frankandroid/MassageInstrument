package com.frank.massageinstrument.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

//这个表是客户直接给的，不是手动创建的，所以不需要创建，否则报错
@Entity(nameInDb = "acupoint",createInDb = false)
public class AcupointBean {
    //    @Id(autoincrement = true)
//    private Long id;
    private int acu_id;
    private int acu_key_no;
    private String acu_name;
    private int acu_parent_no_id;
    private String acu_xml;
    @Generated(hash = 523628965)
    public AcupointBean(int acu_id, int acu_key_no, String acu_name,
            int acu_parent_no_id, String acu_xml) {
        this.acu_id = acu_id;
        this.acu_key_no = acu_key_no;
        this.acu_name = acu_name;
        this.acu_parent_no_id = acu_parent_no_id;
        this.acu_xml = acu_xml;
    }
    @Generated(hash = 1757873851)
    public AcupointBean() {
    }
    public int getAcu_id() {
        return this.acu_id;
    }
    public void setAcu_id(int acu_id) {
        this.acu_id = acu_id;
    }
    public int getAcu_key_no() {
        return this.acu_key_no;
    }
    public void setAcu_key_no(int acu_key_no) {
        this.acu_key_no = acu_key_no;
    }
    public String getAcu_name() {
        return this.acu_name;
    }
    public void setAcu_name(String acu_name) {
        this.acu_name = acu_name;
    }
    public int getAcu_parent_no_id() {
        return this.acu_parent_no_id;
    }
    public void setAcu_parent_no_id(int acu_parent_no_id) {
        this.acu_parent_no_id = acu_parent_no_id;
    }
    public String getAcu_xml() {
        return this.acu_xml;
    }
    public void setAcu_xml(String acu_xml) {
        this.acu_xml = acu_xml;
    }
   

}
