package com.kuka.domain;


public class ResultPageDto<T> {
    /**
     * 标记
     */
    private int code;

    /**
     * 返回的信息
     */
    private String msg;
    /**
     * 当前页数
     */
    private Integer page;


    /**
     * 页大小
     */
    private Integer limit;

    /**
     * 总条数
     */
    private Long count;

    /**
     * 数据
     */
    private T data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
