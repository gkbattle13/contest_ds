package com.incar.contest.bean;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 分页
 * @param <T>
 */
public class Pagination<T> implements Serializable {
    private static final long serialVersionUID = 3755706962843033939L;

    /**
     * 第几页
     */
    @ApiModelProperty(value = "第几页")
    private Integer pageNum;

    /**
     * 一页的纪录数
     */
    @ApiModelProperty(value = "一页的纪录数")
    private Integer pageSize;

    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数")
    private Integer totalData;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private Integer totalPage;

    /**
     * 本页数据列表
     */
    @ApiModelProperty(value = "本页数据列表")
    private List<T> list;

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalData(Integer totalData) {
        this.totalData = totalData;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getTotalData() {
        return totalData;
    }

    public List<T> getList() {
        return list;
    }

}
