package main.isbd.data;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Клиент", schema = "s311817", catalog = "studs")
public class Client {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ид", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "номер_телефона", nullable = false, length = 20)
    private String phone_number;
    @Basic
    @Column(name = "email", nullable = false, length = 64)
    private String email;
    @Basic
    @Column(name = "пароль", nullable = false, length = 64)
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer ид) {
        this.id = ид;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (!Objects.equals(id, client.id)) return false;
        if (!Objects.equals(phone_number, client.phone_number))
            return false;
        if (!Objects.equals(email, client.email)) return false;
        if (!Objects.equals(password, client.password)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (phone_number != null ? phone_number.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
