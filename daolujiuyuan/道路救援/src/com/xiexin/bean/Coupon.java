package com.xiexin.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * coupon
 * @author 
 */
public class Coupon implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 开始时间
     */
    private Date begin;

    /**
     * 结束时间
     */
    private Date end;

    /**
     * 打折百分比 例如：90就是打9折
     */
    private String discount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否已经删除
     */
    private String del;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.del = del;
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
        Coupon other = (Coupon) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getBegin() == null ? other.getBegin() == null : this.getBegin().equals(other.getBegin()))
            && (this.getEnd() == null ? other.getEnd() == null : this.getEnd().equals(other.getEnd()))
            && (this.getDiscount() == null ? other.getDiscount() == null : this.getDiscount().equals(other.getDiscount()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getDel() == null ? other.getDel() == null : this.getDel().equals(other.getDel()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getBegin() == null) ? 0 : getBegin().hashCode());
        result = prime * result + ((getEnd() == null) ? 0 : getEnd().hashCode());
        result = prime * result + ((getDiscount() == null) ? 0 : getDiscount().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getDel() == null) ? 0 : getDel().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", begin=").append(begin);
        sb.append(", end=").append(end);
        sb.append(", discount=").append(discount);
        sb.append(", remark=").append(remark);
        sb.append(", del=").append(del);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}