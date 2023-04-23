package br.com.saboresdomundo.model;

public interface SelectItemCategory {

    public default void onItemClick(Category category){

    };

    public default void onItemClick(PublicationView publicationView){

    };
}
