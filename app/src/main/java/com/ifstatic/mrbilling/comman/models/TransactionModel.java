package com.ifstatic.mrbilling.comman.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.ifstatic.mrbilling.view.create_transaction.models.ChequeDetailModel;
import com.ifstatic.mrbilling.view.create_transaction.models.OnlineDetailModel;
import com.ifstatic.mrbilling.view.create_transaction.models.UpiDetailModel;

public class TransactionModel implements Parcelable {

    private String mrNo ;
    private String party;
    private String paymentMode ;
    private String amount;
    private String date;
    private String address;
private String username;
    private String userId;
    private String transactionId;


    private ChequeDetailModel chequeDetail;
    private UpiDetailModel upiDetail;
    private OnlineDetailModel onlineDetail;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionModel() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public TransactionModel(String mrNo, String party, String paymentMode, String amount, String date) {
        this.mrNo = mrNo;
        this.party = party;
        this.paymentMode = paymentMode;
        this.amount = amount;
        this.date = date;
    }

    public ChequeDetailModel getChequeDetail() {
        return chequeDetail;
    }

    public void setChequeDetail(ChequeDetailModel chequeDetail) {
        this.chequeDetail = chequeDetail;
    }
    public UpiDetailModel getUpiDetail() {
        return upiDetail;
    }

    public void setUpiDetail(UpiDetailModel upiDetail) {
        this.upiDetail = upiDetail;
    }

    public OnlineDetailModel getOnlineDetail() {
        return onlineDetail;
    }

    public void setOnlineDetail(OnlineDetailModel onlineDetail) {
        this.onlineDetail = onlineDetail;
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


     @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mrNo);
        dest.writeString(this.party);
        dest.writeString(this.paymentMode);
        dest.writeString(this.amount);
        dest.writeString(this.date);
        dest.writeString(this.address);
        dest.writeString(this.username);
        dest.writeString(this.userId);
        dest.writeString(this.transactionId);
        dest.writeParcelable(this.chequeDetail, flags);
        dest.writeParcelable(this.upiDetail, flags);
        dest.writeParcelable(this.onlineDetail, flags);
    }

    public void readFromParcel(Parcel source) {
        this.mrNo = source.readString();
        this.party = source.readString();
        this.paymentMode = source.readString();
        this.amount = source.readString();
        this.date = source.readString();
        this.address = source.readString();
        this.username = source.readString();
        this.userId = source.readString();
        this.transactionId = source.readString();
        this.chequeDetail = source.readParcelable(ChequeDetailModel.class.getClassLoader());
        this.upiDetail = source.readParcelable(UpiDetailModel.class.getClassLoader());
        this.onlineDetail = source.readParcelable(OnlineDetailModel.class.getClassLoader());
    }

    protected TransactionModel(Parcel in) {
        this.mrNo = in.readString();
        this.party = in.readString();
        this.paymentMode = in.readString();
        this.amount = in.readString();
        this.date = in.readString();
        this.address = in.readString();
        this.username = in.readString();
        this.userId = in.readString();
        this.transactionId = in.readString();
        this.chequeDetail = in.readParcelable(ChequeDetailModel.class.getClassLoader());
        this.upiDetail = in.readParcelable(UpiDetailModel.class.getClassLoader());
        this.onlineDetail = in.readParcelable(OnlineDetailModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<TransactionModel> CREATOR = new Parcelable.Creator<TransactionModel>() {
        @Override
        public TransactionModel createFromParcel(Parcel source) {
            return new TransactionModel(source);
        }

        @Override
        public TransactionModel[] newArray(int size) {
            return new TransactionModel[size];
        }
    };
}
