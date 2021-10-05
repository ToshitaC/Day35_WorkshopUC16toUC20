package com.blz;

public class Contacts {
    private int id;
    private String firstName;
    private String lastName,emailId,cityName;
    private String address,zipCode,stateName;
    private String phoneNumber;
    private String addressType,address_name;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public String getStateName() {
        return stateName;
    }
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAddressType() {
        return addressType;
    }
    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
    public String getAddress_name() {
        return address_name;
    }
    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }
    public Contacts(int id,String firstName,String lastName,String address_name,String addressType,
                    String address,String cityName,String stateName,String zipCode,String phoneNumber, String emailId) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.cityName = cityName;
        this.address = address;
        this.zipCode = zipCode;
        this.stateName = stateName;
        this.phoneNumber = phoneNumber;
        this.addressType = addressType;
        this.address_name = address_name;
    }


    @Override
    public String toString() {
        return "Contacts [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
                + ", cityName=" + cityName + ", address=" + address + ", zipCode=" + zipCode + ", stateName="
                + stateName + ", phoneNumber=" + phoneNumber + ", addressType=" + addressType + ", address_name="
                + address_name + "]";
    }
    public boolean equals(Object obj) {
        Contacts conObj = (Contacts)obj;
        if (conObj.getFirstName().equals(this.firstName) && conObj.getLastName().equals(this.lastName) )
            return true;
        else
            return false;
    }
}

