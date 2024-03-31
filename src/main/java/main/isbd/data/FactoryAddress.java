package main.isbd.data;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "Адрес_завода", schema = "s311817", catalog = "studs")
public class FactoryAddress {
    @Id
    @Column(name = "ид_завода")
    private Integer factory_id;

    @Basic
    @Column(name = "адрес")
    private String address;

    public Integer getFactoryId() {
        return factory_id;
    }

    public void setFactoryId(Integer factory_id) {
        this.factory_id = factory_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FactoryAddress factoryAddress = (FactoryAddress) o;
        return factory_id.equals(factoryAddress.factory_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(factory_id);
    }

    public boolean isValid() {
        return address != null && address.length() > 0;
    }
}
