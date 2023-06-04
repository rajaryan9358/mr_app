package com.ifstatic.mrbilling.view.home.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MyPartiesModel implements Parcelable {

    private String party;
    private String address;

    public MyPartiesModel() {
    }

    public MyPartiesModel(String party, String address) {
        this.party = party;
        this.address = address;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.party);
        dest.writeString(this.address);
    }

    public void readFromParcel(Parcel source) {
        this.party = source.readString();
        this.address = source.readString();
    }

    protected MyPartiesModel(Parcel in) {
        this.party = in.readString();
        this.address = in.readString();
    }

    public static final Parcelable.Creator<MyPartiesModel> CREATOR = new Parcelable.Creator<MyPartiesModel>() {
        @Override
        public MyPartiesModel createFromParcel(Parcel source) {
            return new MyPartiesModel(source);
        }

        @Override
        public MyPartiesModel[] newArray(int size) {
            return new MyPartiesModel[size];
        }
    };
}
