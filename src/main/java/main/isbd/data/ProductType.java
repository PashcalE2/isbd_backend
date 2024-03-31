package main.isbd.data;

import jakarta.persistence.*;

@Entity
@Table(name = "Тип_продукции", schema = "s311817", catalog = "studs")
public class ProductType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ид", nullable = false)
    private Integer ид;
    @Basic
    @Column(name = "цена", nullable = false, precision = 0)
    private Float цена;

    public Integer getИд() {
        return ид;
    }

    public void setИд(Integer ид) {
        this.ид = ид;
    }

    public Float getЦена() {
        return цена;
    }

    public void setЦена(Float цена) {
        this.цена = цена;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductType that = (ProductType) o;

        if (ид != null ? !ид.equals(that.ид) : that.ид != null) return false;
        if (цена != null ? !цена.equals(that.цена) : that.цена != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ид != null ? ид.hashCode() : 0;
        result = 31 * result + (цена != null ? цена.hashCode() : 0);
        return result;
    }
}
