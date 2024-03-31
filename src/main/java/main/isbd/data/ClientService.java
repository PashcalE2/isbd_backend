package main.isbd.data;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Служба_поддержки", schema = "s311817", catalog = "studs")
public class ClientService {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ид", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "название", nullable = false, length = -1)
    private String name;
    @Basic
    @Column(name = "номер_телефона", nullable = false, length = 20)
    private String phone_number;
    @Basic
    @Column(name = "email", nullable = false, length = 64)
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer ид) {
        this.id = ид;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientService that = (ClientService) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(phone_number, that.phone_number))
            return false;
        if (!Objects.equals(email, that.email)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone_number != null ? phone_number.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
