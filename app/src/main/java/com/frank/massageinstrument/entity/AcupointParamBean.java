package com.frank.massageinstrument.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(nameInDb = "acupoint_param")
public class AcupointParamBean {
    @Id(autoincrement = true)
    private Long id;
    private Integer status;//0关，1开
    private int frequnce;
    private int strength;
    private String startTime;
    private String endTime;
    private String number;//电极编号
    private String name;//名称
    private int mode;
    @Generated(hash = 1012813466)
    public AcupointParamBean(Long id, Integer status, int frequnce, int strength,
                             String startTime, String endTime, String number, String name,
                             int mode) {
        this.id = id;
        this.status = status;
        this.frequnce = frequnce;
        this.strength = strength;
        this.startTime = startTime;
        this.endTime = endTime;
        this.number = number;
        this.name = name;
        this.mode = mode;
    }
    @Generated(hash = 1043347385)
    public AcupointParamBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getFrequnce() {
        return this.frequnce;
    }
    public void setFrequnce(int frequnce) {
        this.frequnce = frequnce;
    }
    public int getStrength() {
        return this.strength;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public String getStartTime() {
        return this.startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return this.endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getNumber() {
        return this.number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getMode() {
        return this.mode;
    }
    public void setMode(int mode) {
        this.mode = mode;
    }
    public void setMode(Integer mode) {
        this.mode = mode;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }


}
