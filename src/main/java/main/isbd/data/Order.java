package main.isbd.data;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Заказ", schema = "s311817", catalog = "studs")
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ид", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "ид_клиента", nullable = false)
    private Integer client_id;
    @Basic
    @Column(name = "ид_консультанта", nullable = false)
    private Integer admin_id;
    @Basic
    @Column(name = "статус", nullable = false)
    private Object status;
    @Basic
    @Column(name = "поступил", nullable = false)
    private Timestamp formed_at;
    @Basic
    @Column(name = "завершен", nullable = true)
    private Timestamp done_at;
    @Basic
    @Column(name = "итоговая_сумма", nullable = false, precision = 0)
    private Float sum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return client_id;
    }

    public void setClientId(Integer client_id) {
        this.client_id = client_id;
    }

    public Integer getAdminId() {
        return admin_id;
    }

    public void setAdminId(Integer admin_id) {
        this.admin_id = admin_id;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Timestamp getFormedAt() {
        return formed_at;
    }

    public void setFormedAt(Timestamp formed_at) {
        this.formed_at = formed_at;
    }

    public Timestamp getDoneAt() {
        return done_at;
    }

    public void setDoneAt(Timestamp done_at) {
        this.done_at = done_at;
    }

    public Float getSum() {
        return sum;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (!Objects.equals(id, order.id)) return false;
        if (!Objects.equals(client_id, order.client_id)) return false;
        if (!Objects.equals(admin_id, order.admin_id))
            return false;
        if (!Objects.equals(status, order.status)) return false;
        if (!Objects.equals(formed_at, order.formed_at)) return false;
        if (!Objects.equals(done_at, order.done_at)) return false;
        if (!Objects.equals(sum, order.sum))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (client_id != null ? client_id.hashCode() : 0);
        result = 31 * result + (admin_id != null ? admin_id.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (formed_at != null ? formed_at.hashCode() : 0);
        result = 31 * result + (done_at != null ? done_at.hashCode() : 0);
        result = 31 * result + (sum != null ? sum.hashCode() : 0);
        return result;
    }
}
