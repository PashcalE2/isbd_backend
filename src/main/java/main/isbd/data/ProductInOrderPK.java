package main.isbd.data;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class ProductInOrderPK implements Serializable {
    @Column(name = "ид_заказа", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer order_id;
    @Column(name = "ид_типа", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer type_id;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductInOrderPK that = (ProductInOrderPK) o;

        if (!Objects.equals(order_id, that.order_id)) return false;
        if (!Objects.equals(type_id, that.type_id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = order_id != null ? order_id.hashCode() : 0;
        result = 31 * result + (type_id != null ? type_id.hashCode() : 0);
        return result;
    }
}
