package com.palmville.rotaract;

public class Donor {
    String Name, emailID, bloodGroup, clubName, recentCheck;
    int contactNumber,age;

    public Donor(String name, String emailID, int age, String bloodGroup, String clubName, String recentCheck, int contactNumber) {
        Name = name;
        this.emailID = emailID;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.clubName = clubName;
        this.recentCheck = recentCheck;
        this.contactNumber = contactNumber;
    }
}

