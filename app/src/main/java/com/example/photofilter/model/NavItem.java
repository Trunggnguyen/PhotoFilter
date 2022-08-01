package com.example.photofilter.model;


public class NavItem {
    int navId;
    int navIcon;
    String navName;
    boolean isSelect;

    public NavItem(int navId, int navIcon, String navName) {
        this.navId = navId;
        this.navIcon = navIcon;
        this.navName = navName;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getNavId() {
        return navId;
    }

    public void setNavId(int navId) {
        this.navId = navId;
    }

    public int getNavIcon() {
        return navIcon;
    }

    public void setNavIcon(int navIcon) {
        this.navIcon = navIcon;
    }

    public String getNavName() {
        return navName;
    }

    public void setNavName(String navName) {
        this.navName = navName;
    }
}
