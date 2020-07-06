package com.github.donkeyrit.javaapp.model;

public class Client {
    private String firstName;
    private String secondName;
    private String middleName;
    private String address;
    private String phoneNumber;
    private int idUser;

    public Client(String firstName, String secondName, String middleName, String address, String phoneNumber, int idUser) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.idUser = idUser;
    }

    private Client() { }

    public String getFirstName() {
        return firstName;
    }
    public String getSecondName() {
        return secondName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public String getAddress() {
        return address;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public int getIdUser() {
        return idUser;
    }

    public static class ClientBuilder {

        private Client client;

        public ClientBuilder() {
            this.client = new Client();
        }

        public ClientBuilder setFirstName(String firstName) {
            this.client.firstName = firstName;
            return this;
        }

        public ClientBuilder setSecondName(String secondName) {
            this.client.secondName = secondName;
            return this;
        }

        public ClientBuilder setMiddleName(String middleName) {
            this.client.middleName = middleName;
            return this;
        }

        public ClientBuilder setAddress(String address) {
            this.client.address = address;
            return this;
        }

        public ClientBuilder setPhoneNumber(String phoneNumber) {
            this.client.phoneNumber = phoneNumber;
            return this;
        }

        public ClientBuilder setIdUser(int idUser) {
            this.client.idUser = idUser;
            return this;
        }

        public Client create() {
            return this.client;
        }

        public void flush() {
            this.client = new Client();
        }
    }
}
