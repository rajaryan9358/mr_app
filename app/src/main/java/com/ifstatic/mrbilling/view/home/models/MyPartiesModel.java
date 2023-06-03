package com.ifstatic.mrbilling.view.home.models;

public class MyPartiesModel {

    private String party;
    private String address;

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
}
