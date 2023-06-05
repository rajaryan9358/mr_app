package com.ifstatic.mrbilling.view.create_transaction.models;

public class CreateTransactionModel {
    private String party;
    private String amount;
    private String paymentMode;
    private String date;
    private String mrNo;
    private ChequeDetailModel chequeDetail;

    public CreateTransactionModel(String party, String amount, String paymentMode, String date, String mrNo) {
        this.party = party;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.date = date;
        this.mrNo = mrNo;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMrNo() {
        return mrNo;
    }

    public void setMrNo(String mrNo) {
        this.mrNo = mrNo;
    }

    public ChequeDetailModel getChequeDetail() {
        return chequeDetail;
    }

    public void setChequeDetail(ChequeDetailModel chequeDetail) {
        this.chequeDetail = chequeDetail;
    }
}
