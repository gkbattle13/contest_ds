package com.incar.contest.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2020/2/28 21:07
 */
@Getter
@Setter
public class GPS {
    public GPS(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    private double lat;
    private double lon;
}
