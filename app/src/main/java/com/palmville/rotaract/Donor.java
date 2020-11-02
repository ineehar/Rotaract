package com.palmville.rotaract;

public class Donor {
    String Name, emailID, bloodGroup, clubName, recentCheck;
    long contactNumber;
    int age;

    public Donor(String name, String emailID, int age, String bloodGroup, String clubName, String recentCheck, long contactNumber) {
        Name = name;
        this.emailID = emailID;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.clubName = clubName;
        this.recentCheck = recentCheck;
        this.contactNumber = contactNumber;
    }
}

