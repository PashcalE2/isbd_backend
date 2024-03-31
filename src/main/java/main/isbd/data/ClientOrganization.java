package main.isbd.data;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Организация_клиента", schema = "s311817", catalog = "studs")
public class ClientOrganization {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ид_клиента", nullable = false)
    private Integer client_id;
    @Basic
    @Column(name = "название", nullable = false)
    private Object name;

    public Integer getClientId() {
        return client_id;
    }

    public void setClientId(Integer client_id) {
        this.client_id = client_id;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientOrganization that = (ClientOrganization) o;

        if (!Objects.equals(client_id, that.client_id)) return false;
        if (!Objects.equals(name, that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = client_id != null ? client_id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
