package model;

import java.util.List;
import java.util.Objects;

public class Category {
    private Integer id;
    private String nameUA;
    private String nameEN;
    private List<Service> services;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameUA() {
        return nameUA;
    }

    public void setNameUA(String nameUA) {
        this.nameUA = nameUA;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id.equals(category.id) && nameUA.equals(category.nameUA) && nameEN.equals(category.nameEN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameUA, nameEN);
    }

    public String toStringUA() {
        return nameUA;
    }

    public String toStringEN() {
        return nameEN;
    }
}
