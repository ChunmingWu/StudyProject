package com.adminstrator.guaguakaapplication.gaugaule.bean;

/**
 * Created by Administrator on 2019/8/19.
 */

public class ScratchPrizesDialogPackageBean {
    private String buyNumber;
    private String freeNumber;
    private String amount;
    private boolean isSelected;

    public ScratchPrizesDialogPackageBean(String buyNumber, String freeNumber, String amount) {
        this.buyNumber = buyNumber;
        this.freeNumber = freeNumber;
        this.amount = amount;
    }

    public ScratchPrizesDialogPackageBean(String buyNumber, String freeNumber, String amount, boolean isSelected) {
        this.buyNumber = buyNumber;
        this.freeNumber = freeNumber;
        this.amount = amount;
        this.isSelected = isSelected;
    }

    public String getBuyNumber() {
        return buyNumber;
    }

    public void setBuyNumber(String buyNumber) {
        this.buyNumber = buyNumber;
    }

    public String getFreeNumber() {
        return freeNumber;
    }

    public void setFreeNumber(String freeNumber) {
        this.freeNumber = freeNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "ScratchPrizesDialogPackageBean{" +
                "buyNumber='" + buyNumber + '\'' +
                ", freeNumber='" + freeNumber + '\'' +
                ", amount='" + amount + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
