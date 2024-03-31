package main.isbd.data;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Продукция_в_заказе", schema = "s311817", catalog = "studs")
@IdClass(ProductInOrderPK.class)
public class ProductInOrder {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ид_заказа", nullable = false)
    private Integer order_id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ид_типа", nullable = false)
    private Integer type_id;
    @Basic
    @Column(name = "статус", nullable = false)
    private Object status;
    @Basic
    @Column(name = "количество", nullable = false)
    private Integer count;

    public Integer getOrderId() {
        return order_id;
    }

    public void setOrderId(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getTypeId() {
        return type_id;
    }

    public void setTypeId(Integer type_id) {
        this.type_id = type_id;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductInOrder that = (ProductInOrder) o;

        if (!Objects.equals(order_id, that.order_id)) return false;
        if (!Objects.equals(type_id, that.type_id)) return false;
        if (!Objects.equals(status, that.status)) return false;
        if (!Objects.equals(count, that.count)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = order_id != null ? order_id.hashCode() : 0;
        result = 31 * result + (type_id != null ? type_id.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        return result;
    }
}
