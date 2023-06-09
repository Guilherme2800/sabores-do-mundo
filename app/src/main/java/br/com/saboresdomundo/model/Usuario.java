package br.com.saboresdomundo.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.List;
@IgnoreExtraProperties
public class Usuario {

    private String login;
    private String password;

    private String name;

    private List<Publication> favoritePublications;
    private List<Publication> finishPublications;
    private List<Publication> myPublications;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Publication> getFavoritePublications() {
        return favoritePublications;
    }

    public void setFavoritePublications(List<Publication> favoritePublications) {
        this.favoritePublications = favoritePublications;
    }

    public List<Publication> getFinishPublications() {
        return finishPublications;
    }

    public void setFinishPublications(List<Publication> finishPublications) {
        this.finishPublications = finishPublications;
    }

    public List<Publication> getMyPublications() {
        return myPublications;
    }

    public void setMyPublications(List<Publication> myPublications) {
        this.myPublications = myPublications;
    }
}
