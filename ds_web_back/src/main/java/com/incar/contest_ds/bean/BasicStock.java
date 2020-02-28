package com.incar.contest_ds.bean;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 基础信息
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2020/2/28 11:38
 */
@Entity
@Table(name = "basic_stock")
@Setter
@Getter
public class BasicStock {

    @Id
    @GenericGenerator(name = "idGenerator",strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
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
