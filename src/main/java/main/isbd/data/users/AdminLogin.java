package main.isbd.data.users;

public class AdminLogin {
    private Integer id;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValid() {
        return  id != null
                && password != null && password.length() > 0;
    }
}
