package br.com.saboresdomundo.model;

import java.io.Serializable;
import java.util.Objects;

public class Category implements Serializable {

    private int img;

    private int imgFilter;
    private String name;

    public Category() {
    }

    public Category(int img, String name, int imgFilter) {
        this.img = img;
        this.name = name;
        this.imgFilter = imgFilter;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgFilter() {
        return imgFilter;
    }

    public void setImgFilter(int imgFilter) {
        this.imgFilter = imgFilter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
