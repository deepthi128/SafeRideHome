/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nwmissouri.universitypolice.saferidehome.pojos;

/**
 *
 * @author Abhyudaya Reddy Gurram
 */
public class User {

    private String userId;
    private String password;
    private String userType;
    private String name;
    private String emailId;
    private String phoneNumber;
    private String accountStatus;

    public User() {
    }

    public User(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public User(String userId, String password, String userType, String name, String emailId, String phoneNumber, String accountStatus) {
        this.userId = userId;
        this.password = password;
        this.userType = userType;
        this.name = name;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
        this.accountStatus = accountStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
