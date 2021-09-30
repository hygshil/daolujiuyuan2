package com.xiexin.bean;

import java.io.Serializable;

/**
 * syslog
 * @author 
 */
public class Syslog implements Serializable {
    private Integer id;

    /**
     * 用户名
     */
    private String userid;

    /**
     * 模块
     */
    private String module;

    /**
     * 方法
     */
    private String method;

    /**
     * 执行时间
     */
    private String responseDate;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 访问时间
     */
    private String dates;

    /**
     * 提交结果
     */
    private String commit;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(String responseDate) {
        this.responseDate = responseDate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
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
        Syslog other = (Syslog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getModule() == null ? other.getModule() == null : this.getModule().equals(other.getModule()))
            && (this.getMethod() == null ? other.getMethod() == null : this.getMethod().equals(other.getMethod()))
            && (this.getResponseDate() == null ? other.getResponseDate() == null : this.getResponseDate().equals(other.getResponseDate()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getDates() == null ? other.getDates() == null : this.getDates().equals(other.getDates()))
            && (this.getCommit() == null ? other.getCommit() == null : this.getCommit().equals(other.getCommit()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getModule() == null) ? 0 : getModule().hashCode());
        result = prime * result + ((getMethod() == null) ? 0 : getMethod().hashCode());
        result = prime * result + ((getResponseDate() == null) ? 0 : getResponseDate().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getDates() == null) ? 0 : getDates().hashCode());
        result = prime * result + ((getCommit() == null) ? 0 : getCommit().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userid=").append(userid);
        sb.append(", module=").append(module);
        sb.append(", method=").append(method);
        sb.append(", responseDate=").append(responseDate);
        sb.append(", ip=").append(ip);
        sb.append(", dates=").append(dates);
        sb.append(", commit=").append(commit);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}