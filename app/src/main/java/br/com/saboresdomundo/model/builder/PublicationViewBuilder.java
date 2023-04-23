package br.com.saboresdomundo.model.builder;

import java.util.ArrayList;
import java.util.List;

import br.com.saboresdomundo.R;
import br.com.saboresdomundo.model.PublicationView;

public class PublicationViewBuilder {

    public static List<PublicationView> buildDefultPublications(){

        List<PublicationView> publicationViews = new ArrayList<>();
        publicationViews.add(new PublicationView("Feijoada", R.mipmap.food_feijoada2, "Feijoada de vó para o fim de semana em familia", "Preparo: 40min"));
        publicationViews.add(new PublicationView("Pizza", R.mipmap.food_pizza, "Pizza de quejo para o jantar do domingo", "Preparo: 30min"));
        publicationViews.add(new PublicationView("Arroz", R.mipmap.arroz, "Arroz leve e soltinho", "Preparo: 20min"));

        return publicationViews;
    }

}
