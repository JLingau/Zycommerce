package models;

public class UserModel {
    private int id;
    private String username;
    private String password;
    private String privilege;
    private String status;
    private String fullname;
    private String email;
    private String phone;

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserModel(String username, String password, String privilege, String status,
                     String fullname, String email, String phone) {
        this.username = username;
        this.password = password;
        this.privilege = privilege;
        this.status = status;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPrivilege() {
        return privilege;
    }

    public String getStatus() {
        return status;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
