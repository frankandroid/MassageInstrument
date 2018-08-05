package com.frank.massageinstrument.adapter.bean;

public class GridItemBean {
    private boolean isSwitchOn;
    private String name;

    public GridItemBean() {
        super();
    }

    public GridItemBean(String name, boolean isSwitchOn) {
        super();
        this.isSwitchOn = isSwitchOn;
        this.name = name;
    }

    public boolean isSwitchOn() {
        return isSwitchOn;
    }

    public void setSwitchOn(boolean switchOn) {
        isSwitchOn = switchOn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
