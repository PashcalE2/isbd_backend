package main.isbd.data;

import jakarta.persistence.*;

@Entity
@Table(name = "Название_продукции", schema = "s311817", catalog = "studs")
public class ProductName {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ид_типа", nullable = false)
    private Integer идТипа;
    @Basic
    @Column(name = "название", nullable = false, length = -1)
    private String название;

    public Integer getИдТипа() {
        return идТипа;
    }

    public void setИдТипа(Integer идТипа) {
        this.идТипа = идТипа;
    }

    public String getНазвание() {
        return название;
    }

    public void setНазвание(String название) {
        this.название = название;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductName that = (ProductName) o;

        if (идТипа != null ? !идТипа.equals(that.идТипа) : that.идТипа != null) return false;
        if (название != null ? !название.equals(that.название) : that.название != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = идТипа != null ? идТипа.hashCode() : 0;
        result = 31 * result + (название != null ? название.hashCode() : 0);
        return result;
    }
}
