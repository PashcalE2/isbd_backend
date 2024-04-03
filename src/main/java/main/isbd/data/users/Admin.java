package main.isbd.data.users;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Консультант", schema = "s311817", catalog = "studs")
public class Admin {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ид", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "ид_службы_поддержки", nullable = false)
    private Integer client_service_id;
    @Basic
    @Column(name = "ид_расписания", nullable = false)
    private Integer schedule_id;
    @Basic
    @Column(name = "ФИО", nullable = false, length = 64)
    private String full_name;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClient_service_id() {
        return client_service_id;
    }

    public void setClient_service_id(Integer client_service_id) {
        this.client_service_id = client_service_id;
    }

    public Integer getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(Integer schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
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

        Admin admin = (Admin) o;

        if (!Objects.equals(id, admin.id)) return false;
        if (!Objects.equals(client_service_id, admin.client_service_id))
            return false;
        if (!Objects.equals(schedule_id, admin.schedule_id)) return false;
        if (!Objects.equals(full_name, admin.full_name)) return false;
        if (!Objects.equals(phone_number, admin.phone_number))
            return false;
        if (!Objects.equals(email, admin.email)) return false;
        if (!Objects.equals(password, admin.password)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (client_service_id != null ? client_service_id.hashCode() : 0);
        result = 31 * result + (schedule_id != null ? schedule_id.hashCode() : 0);
        result = 31 * result + (full_name != null ? full_name.hashCode() : 0);
        result = 31 * result + (phone_number != null ? phone_number.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
