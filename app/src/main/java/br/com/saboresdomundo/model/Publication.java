package br.com.saboresdomundo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Publication implements Serializable {

    private String title;
    private String description;
    private BigDecimal price;
    private String time;
    private int img;
    private List<String> ingredients;
    private List<Steps> firstStep;
    private List<Steps> steps;
    private List<Category> category;
    private Usuario autor;
    private int stars;

    public Publication() {
    }

    public Publication(String title, int img, String description, String time, List<Category> category) {
        this.title = title;
        this.img = img;
        this.description = description;
        this.time = time;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public List<Steps> getFirstStep() {
        return firstStep;
    }

    public void setFirstStep(List<Steps> firstStep) {
        this.firstStep = firstStep;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
