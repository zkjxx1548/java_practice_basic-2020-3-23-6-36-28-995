package com.thoughtworks;

public class User {
    private String name;
    private String telephone;
    private String email;
    private String password;
    private int loginCount;
    private boolean loginLock;

    public User() {
    }

    public User(String name, String telephone, String email, String password, int loginCount, boolean loginLock) {
        this.name = name;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.loginCount = loginCount;
        this.loginLock = loginLock;
    }

    public String getName() {
        return name;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public boolean isLoginLock() {
        return loginLock;
    }
}
