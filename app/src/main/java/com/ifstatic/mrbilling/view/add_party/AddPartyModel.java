package com.ifstatic.mrbilling.view.add_party;

public class AddPartyModel {

    private String party;
    private String address;

    public AddPartyModel(String party, String address) {
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
