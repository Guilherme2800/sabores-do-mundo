package br.com.saboresdomundo.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@IgnoreExtraProperties
public class Publication implements Serializable {

    private String title;
    private String description;
    private Long price;
    private String time;
    private int img;

    private String imgPath;
    private List<String> ingredients;
    private List<Steps> firstStep;
    private List<Steps> steps;
    private List<Category> category;
    private String autor;
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
        if(category == null){
            category = new ArrayList<>();
        }
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
