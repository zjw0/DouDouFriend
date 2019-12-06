package cn.appoa.doudoufriend.bean;

import java.io.Serializable;


public class UserMenuList implements Serializable {

    public int Id;
    public int Icon;
    public String MenuIntro;
    public Class clazz;

    public UserMenuList(){

    }

    public UserMenuList(int id, int icon, String menuIntro, Class clazz) {
        Id = id;
        Icon = icon;
        MenuIntro = menuIntro;
        this.clazz = clazz;
    }
}
