package com.incar.contest.bean;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * Point
 *
 * @author Kong, created on 2020-02-28T14:56.
 * @since 1.0.0-SNAPSHOT
 */
public class Point {

    //点ID
    public Date time;

    //经度
    public double longitude;

    //纬度
    public double latitude;

    public Point(){
    }

    public Point(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Point(Date time, double longitude, double latitude) {
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    //空构造函数
    public String toString(){
        //DecimalFormat df = new DecimalFormat("0.000000");
        return this.time+"#"+this.latitude+","+this.longitude;
    }
    public String getTestString(){
        DecimalFormat df = new DecimalFormat("0.000000");
        return df.format(this.latitude)+","+df.format(this.longitude);
    }
    public String getResultString(){
        DecimalFormat df = new DecimalFormat("0.000000");
        return this.time+"#"+df.format(this.latitude)+","+df.format(this.longitude);
    }
}
