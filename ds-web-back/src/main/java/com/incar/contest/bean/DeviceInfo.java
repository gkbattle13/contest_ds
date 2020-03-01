package com.incar.contest.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 * 设备信息
 *
 * @author Kong, created on 2020-03-01T11:13.
 * @since 1.0.0-SNAPSHOT
 */
public class DeviceInfo {

    /**
     * 设备编号
     */
    @ApiModelProperty(position = 1, value = "设备编号")
    private String deviceCode;

    /**
     * 里程数
     */
    @ApiModelProperty(position = 2, value = "里程数")
    private Double mileage;

    public DeviceInfo() {
    }

    public DeviceInfo(String deviceCode, Double mileage) {
        this.deviceCode = deviceCode;
        this.mileage = mileage;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }
}
