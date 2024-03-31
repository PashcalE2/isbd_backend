package main.isbd.data;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Описание_материала", schema = "s311817", catalog = "studs")
public class MaterialDescription {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ид_типа", nullable = false)
    private Integer type_id;
    @Basic
    @Column(name = "описание", nullable = false, length = -1)
    private String description;

    public Integer getTypeId() {
        return type_id;
    }

    public void setTypeId(Integer type_id) {
        this.type_id = type_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MaterialDescription that = (MaterialDescription) o;

        if (!Objects.equals(type_id, that.type_id)) return false;
        if (!Objects.equals(description, that.description)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type_id != null ? type_id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
