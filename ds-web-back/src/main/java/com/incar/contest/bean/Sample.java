package com.incar.contest.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Sample Entity
 *
 * @author Kong, created on 2020-02-27T17:42.
 * @since 1.0.0-SNAPSHOT
 */
public class Sample {

    private String id;

    private String deviceCode;

    private Long tripNo;

    private Double speed;

    private Double distance;

    private String longitude;

    private String latitude;

    private Long angle;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date collectTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public Long getTripNo() {
        return tripNo;
    }

    public void setTripNo(Long tripNo) {
        this.tripNo = tripNo;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Long getAngle() {
        return angle;
    }

    public void setAngle(Long angle) {
        this.angle = angle;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }
}
