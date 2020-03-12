package org.jeecgframework.web.hrm.bean;



import java.io.Serializable;
import java.util.List;

public class PageBean<T> implements Serializable {
    private int currPage;//当前页数
    private int pageSize;//每页显示的记录数
    private int total; //总计录数
    private int totalPage;//总页数
    private List<T> rows;//每页显示的数据

    public int getCurrPage() {
        return currPage;
    }
    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public int getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public List<T> getRows() {
        return rows;
    }
    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
