package cn.appoa.doudoufriend.bean;

import java.io.Serializable;

public class DateList implements Serializable {

    public int id;
    public String startDate;
    public int days;
    public String endDate;

    public DateList(int id, String startDate, int days, String endDate) {
        this.id = id;
        this.startDate = startDate;
        this.days = days;
        this.endDate = endDate;
    }

    public DateList() {
    }
}
