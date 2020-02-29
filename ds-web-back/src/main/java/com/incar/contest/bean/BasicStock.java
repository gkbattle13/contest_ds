package com.incar.contest.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 基础信息
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2020/2/28 11:38
 */
@Setter
@Getter
public class BasicStock {

    private String tsCode;
    private String symbol;
    private String name;
    private String fullname;
    private String enname;
    private String exchange;
    private String currType;
    private String listStatus;
    private String listDate;
    private String delistDate;
    private String isHs;
    private String createTime;
}
