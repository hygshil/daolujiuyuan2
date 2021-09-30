package com.xiexin.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * orders
 * @author 
 */
public class Orders implements Serializable {
    /**
     * 订单主键
     */
    private Integer id;

    /**
     * 下订单手机号
     */
    private String phone;

    /**
     * 接订单技师
     */
    private Integer mid;

    /**
     * 订单纬度
     */
    private String lng;

    /**
     * 订单经度
     */
    private String lat;

    /**
     * 下订单时间
     */
    private Date createtime;

    /**
     * 接单时间
     */
    private Date updtetime;

    /**
     * 完成订单时间
     */
    private Date endtime;

    /**
     * 救援内容
     */
    private String contents;

    /**
     * 状态：0刚下单，1已接单，2已到达正在修，3已完成，4已评价
     */
    private String status;

    /**
     * 救援地址
     */
    private String address;

    /**
     * 附带内容
     */
    private String attached;

    /**
     * 金额
     */
    private String cost;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdtetime() {
        return updtetime;
    }

    public void setUpdtetime(Date updtetime) {
        this.updtetime = updtetime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAttached() {
        return attached;
    }

    public void setAttached(String attached) {
        this.attached = attached;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Orders other = (Orders) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getMid() == null ? other.getMid() == null : this.getMid().equals(other.getMid()))
            && (this.getLng() == null ? other.getLng() == null : this.getLng().equals(other.getLng()))
            && (this.getLat() == null ? other.getLat() == null : this.getLat().equals(other.getLat()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdtetime() == null ? other.getUpdtetime() == null : this.getUpdtetime().equals(other.getUpdtetime()))
            && (this.getEndtime() == null ? other.getEndtime() == null : this.getEndtime().equals(other.getEndtime()))
            && (this.getContents() == null ? other.getContents() == null : this.getContents().equals(other.getContents()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getAttached() == null ? other.getAttached() == null : this.getAttached().equals(other.getAttached()))
            && (this.getCost() == null ? other.getCost() == null : this.getCost().equals(other.getCost()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getMid() == null) ? 0 : getMid().hashCode());
        result = prime * result + ((getLng() == null) ? 0 : getLng().hashCode());
        result = prime * result + ((getLat() == null) ? 0 : getLat().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getUpdtetime() == null) ? 0 : getUpdtetime().hashCode());
        result = prime * result + ((getEndtime() == null) ? 0 : getEndtime().hashCode());
        result = prime * result + ((getContents() == null) ? 0 : getContents().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getAttached() == null) ? 0 : getAttached().hashCode());
        result = prime * result + ((getCost() == null) ? 0 : getCost().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", mid=" + mid +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", createtime=" + createtime +
                ", updtetime=" + updtetime +
                ", endtime=" + endtime +
                ", contents='" + contents + '\'' +
                ", status='" + status + '\'' +
                ", address='" + address + '\'' +
                ", attached='" + attached + '\'' +
                ", cost='" + cost + '\'' +
                '}';
    }
}