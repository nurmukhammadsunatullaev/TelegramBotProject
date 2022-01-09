package main.app.models;

import java.io.Serializable;

public class UserModel implements Serializable {
    private Long userId;
    private String userFirstName;
    private String userLastName;
    private String userUserName;
    private boolean accept;

    public UserModel(Long userId, String userFirstName, String userLastName, String userUserName) {
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userUserName = userUserName;
    }

    public UserModel(String userFirstName, String userLastName, String userUserName) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userUserName = userUserName;
    }

    public UserModel(String userFirstName, String userLastName) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }



    public UserModel() {
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    private String setEmpty(String value){
        return value==null  ?   ""  :   value;
    }

    public String getUserUserName() {
        return userUserName;
    }

    public void setUserUserName(String userUserName) {
        this.userUserName = setEmpty(userUserName);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = setEmpty(userFirstName);
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = setEmpty(userLastName);
    }
}
