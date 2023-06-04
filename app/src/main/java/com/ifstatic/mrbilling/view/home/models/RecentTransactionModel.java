package com.ifstatic.mrbilling.view.home.models;

public class RecentTransactionModel {

    private String mrNo ;
    private String party;
    private String paymentMode ;
    private String amount;
    private String date;

    public RecentTransactionModel() {
    }

    public String getMrNo() {
        return mrNo;
    }

    public void setMrNo(String mrNo) {
        this.mrNo = mrNo;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
