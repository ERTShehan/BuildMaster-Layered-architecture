package com.assignment.buildmaster.entity;

import java.io.Serializable;

public class Client implements Serializable {
    private String clientId;
    private String clientName;
    private String clientAddress;
    private String clientContact;
    private String clientEmail;

    public Client() {
    }

    public Client(String clientId, String clientName, String clientAddress, String clientContact, String clientEmail) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientAddress = clientAddress;
        this.clientContact = clientContact;
        this.clientEmail = clientEmail;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientContact() {
        return clientContact;
    }

    public void setClientContact(String clientContact) {
        this.clientContact = clientContact;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId='" + clientId + '\'' +
                ", clientName='" + clientName + '\'' +
                ", clientAddress='" + clientAddress + '\'' +
                ", clientContact='" + clientContact + '\'' +
                ", clientEmail='" + clientEmail + '\'' +
                '}';
    }
}
