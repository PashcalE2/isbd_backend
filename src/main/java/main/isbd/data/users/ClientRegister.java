package main.isbd.data.users;

public class ClientRegister {
    private String name;
    private String phone_number;
    private String email;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValid() {
        return  name != null && name.length() > 0 &&
                phone_number != null && phone_number.length() > 0 &&
                email != null && email.length() > 0 &&
                password != null && password.length() > 0;
    }
}
