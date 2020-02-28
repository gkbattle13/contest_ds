package com.incar.contest_ds.bean;

import java.text.DecimalFormat;

/**
 * Point
 *
 * @author Kong, created on 2020-02-28T14:56.
 * @since 1.0.0-SNAPSHOT
 */
public class Point implements Comparable<Point>{

    //点ID
    public int id;

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

    public Point(int id, double longitude, double latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return this.id+"#"+this.latitude+","+this.longitude;
    }
    public String getTestString(){
        DecimalFormat df = new DecimalFormat("0.000000");
        return df.format(this.latitude)+","+df.format(this.longitude);
    }
    public String getResultString(){
        DecimalFormat df = new DecimalFormat("0.000000");
        return this.id+"#"+df.format(this.latitude)+","+df.format(this.longitude);
    }

    @Override
    public int compareTo(Point other) {
        if(this.id<other.id) {
            return -1;
        }else if (this.id>other.id) {
            return 1;
        }

        return 0;
    }
}
