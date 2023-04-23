package br.com.saboresdomundo.model;

public class PublicationView {

    private String title;
    private int img;
    private String description;
    private String time;

    public PublicationView() {
    }

    public PublicationView(String title, int img, String description, String time) {
        this.title = title;
        this.img = img;
        this.description = description;
        this.time = time;
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
}
