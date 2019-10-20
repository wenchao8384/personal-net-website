package com.gofun.model;

import java.util.Date;

public class ParkingSpacePo {
    private Long spaceid;

    private String parkingid;

    private String carid;

    private String platenum;

    private String spacestate;

    private String orderid;

    private Date toparkingtime;

    private String usestatus;

    private String user;

    private String phone;

    private String controlverion;

    private String resource;

    private String spacetype;

    private String remark;

    private Date createdat;

    public Long getSpaceid() {
        return spaceid;
    }

    public void setSpaceid(Long spaceid) {
        this.spaceid = spaceid;
    }

    public String getParkingid() {
        return parkingid;
    }

    public void setParkingid(String parkingid) {
        this.parkingid = parkingid == null ? null : parkingid.trim();
    }

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid == null ? null : carid.trim();
    }

    public String getPlatenum() {
        return platenum;
    }

    public void setPlatenum(String platenum) {
        this.platenum = platenum == null ? null : platenum.trim();
    }

    public String getSpacestate() {
        return spacestate;
    }

    public void setSpacestate(String spacestate) {
        this.spacestate = spacestate == null ? null : spacestate.trim();
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public Date getToparkingtime() {
        return toparkingtime;
    }

    public void setToparkingtime(Date toparkingtime) {
        this.toparkingtime = toparkingtime;
    }

    public String getUsestatus() {
        return usestatus;
    }

    public void setUsestatus(String usestatus) {
        this.usestatus = usestatus == null ? null : usestatus.trim();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user == null ? null : user.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getControlverion() {
        return controlverion;
    }

    public void setControlverion(String controlverion) {
        this.controlverion = controlverion == null ? null : controlverion.trim();
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource == null ? null : resource.trim();
    }

    public String getSpacetype() {
        return spacetype;
    }

    public void setSpacetype(String spacetype) {
        this.spacetype = spacetype == null ? null : spacetype.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }
}