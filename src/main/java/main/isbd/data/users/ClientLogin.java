package main.isbd.data.users;

public class ClientLogin {
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValid() {
        return  name != null && name.length() > 0
                && password != null && password.length() > 0;
    }
}
