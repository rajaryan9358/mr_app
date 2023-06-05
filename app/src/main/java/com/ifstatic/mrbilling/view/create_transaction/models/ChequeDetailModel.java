package com.ifstatic.mrbilling.view.create_transaction.models;

public class ChequeDetailModel {

    private String date;
    private String bankName;
    private String chequeNo;

    public ChequeDetailModel() {
    }

    public ChequeDetailModel(String date, String bankName, String chequeNo) {
        this.date = date;
        this.bankName = bankName;
        this.chequeNo = chequeNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }
}
