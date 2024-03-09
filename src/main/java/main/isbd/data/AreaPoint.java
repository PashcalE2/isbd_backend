package main.isbd.data;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "areapoint_lab4", schema = "s311817", catalog = "studs")
public class AreaPoint {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "x")
    private double x;
    @Basic
    @Column(name = "y")
    private double y;
    @Basic
    @Column(name = "r")
    private double r;
    @Basic
    @Column(name = "hit")
    private boolean hit;
    @Basic
    @Column(name = "datetime")
    private String dateTime;
    @Basic
    @Column(name = "userlogin")
    private String userLogin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreaPoint that = (AreaPoint) o;
        return id == that.id && Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0 && Double.compare(that.r, r) == 0 && hit == that.hit && Objects.equals(dateTime, that.dateTime) && Objects.equals(userLogin, that.userLogin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, r, hit, dateTime, userLogin);
    }
}
