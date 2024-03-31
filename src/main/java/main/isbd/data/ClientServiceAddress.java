package main.isbd.data;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Адрес_службы_поддержки", schema = "s311817", catalog = "studs")
public class ClientServiceAddress {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ид_службы_поддержки", nullable = false)
    private Integer client_service_id;
    @Basic
    @Column(name = "адрес", nullable = false, length = -1)
    private String address;

    public Integer getClientServiceId() {
        return client_service_id;
    }

    public void setClientServiceId(Integer client_service_id) {
        this.client_service_id = client_service_id;
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

        ClientServiceAddress that = (ClientServiceAddress) o;

        if (!Objects.equals(client_service_id, that.client_service_id))
            return false;
        if (!Objects.equals(address, that.address)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = client_service_id != null ? client_service_id.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
