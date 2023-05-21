package br.com.saboresdomundo.model;

public class UserOptions {

    private String title;
    private String description;
    private int img;

    private Class clazz;

    public UserOptions() {
    }

    public UserOptions(String title, String description, int img, Class clazz) {
        this.title = title;
        this.description = description;
        this.img = img;
        this.clazz = clazz;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
