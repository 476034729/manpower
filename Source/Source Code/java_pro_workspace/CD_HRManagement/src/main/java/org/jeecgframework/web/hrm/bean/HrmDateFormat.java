package org.jeecgframework.web.hrm.bean;

public class HrmDateFormat {
    private String start_time;
    private String end_time;
    private Double count_time;
    private Double surplus_time;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public Double getCount_time() {
        return count_time;
    }

    public void setCount_time(Double count_time) {
        this.count_time = count_time;
    }

    public Double getSurplus_time() {
        return surplus_time;
    }

    public void setSurplus_time(Double surplus_time) {
        this.surplus_time = surplus_time;
    }
}
