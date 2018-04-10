package com.litsoft.evaluateserver.util;

import java.util.List;

public class PageInfo<T> {

    private static final long serialVersionUID = -1202716581589799959L;
    // 当前页
    private int currentPage;
    //每頁大小
    private int pageSize;
    //总记录数
    private int totalSize;
    //总页数
    private int totalPage;
    //分頁查詢結果
    private List<T> pageList;

    public PageInfo() {
    }

    public int getCurrentPage() {
        if(currentPage<=0)
            currentPage = 1;
        return currentPage;
    }

    public PageInfo(int totalSize, int currentPage, int pageSize, List<T> pageList) {
        this.totalSize = totalSize;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.pageList = pageList;
        this.getTotalPage();

    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize = 16;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPage() {
        if(totalSize%pageSize==0)
            totalPage = totalSize/pageSize;
        else
            totalPage = totalSize/pageSize+1;
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }
}
