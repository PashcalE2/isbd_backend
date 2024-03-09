package main.isbd.data;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "users", schema = "s311817", catalog = "studs")
public class Users {
    @Id
    @Column(name = "login")
    private String login;
    @Basic
    @Column(name = "password")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;  
        Users users = (Users) o;
        return login.equals(users.login) && password.equals(users.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }

    public boolean isValid() {
        return  login != null && login.length() > 0
                && password != null && password.length() > 0;
    }
}
