package main.isbd.data;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Расписание_консультантов", schema = "s311817", catalog = "studs")
public class AdminSchedule {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ид", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "рабочее_время", nullable = false)
    private Integer working_hours;
    @Basic
    @Column(name = "описание", nullable = true, length = -1)
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorking_hours() {
        return working_hours;
    }

    public void setWorking_hours(Integer working_hours) {
        this.working_hours = working_hours;
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

        AdminSchedule that = (AdminSchedule) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(working_hours, that.working_hours)) return false;
        if (!Objects.equals(description, that.description)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (working_hours != null ? working_hours.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
