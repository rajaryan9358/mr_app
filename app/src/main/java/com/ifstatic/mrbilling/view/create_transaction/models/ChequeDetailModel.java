package com.ifstatic.mrbilling.view.create_transaction.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ChequeDetailModel implements Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeString(this.bankName);
        dest.writeString(this.chequeNo);
    }

    public void readFromParcel(Parcel source) {
        this.date = source.readString();
        this.bankName = source.readString();
        this.chequeNo = source.readString();
    }

    protected ChequeDetailModel(Parcel in) {
        this.date = in.readString();
        this.bankName = in.readString();
        this.chequeNo = in.readString();
    }

    public static final Parcelable.Creator<ChequeDetailModel> CREATOR = new Parcelable.Creator<ChequeDetailModel>() {
        @Override
        public ChequeDetailModel createFromParcel(Parcel source) {
            return new ChequeDetailModel(source);
        }

        @Override
        public ChequeDetailModel[] newArray(int size) {
            return new ChequeDetailModel[size];
        }
    };
}
