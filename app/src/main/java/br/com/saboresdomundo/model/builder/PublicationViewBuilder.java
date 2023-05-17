package br.com.saboresdomundo.model.builder;

import java.util.ArrayList;
import java.util.List;

import br.com.saboresdomundo.R;
import br.com.saboresdomundo.model.Publication;

public class PublicationViewBuilder {

    public static List<Publication> buildDefultPublications(){

        List<Publication> publications = new ArrayList<>();
        publications.add(new Publication("Feijoada", R.mipmap.food_feijoada3_foreground, "Feijoada de vó para o fim de semana em familia", "Preparo: 40min", CategoryBuilder.buildDefultCategories()));
        publications.add(new Publication("Pizza", R.mipmap.food_pizza_foreground, "Pizza de quejo para o jantar do domingo", "Preparo: 30min", CategoryBuilder.buildDefultCategories()));
        publications.add(new Publication("Arroz", R.mipmap.arroz_foreground, "Arroz leve e soltinho", "Preparo: 20min", CategoryBuilder.buildDefultCategories()));
        publications.add(new Publication("Arroz", R.mipmap.arroz_foreground, "Arroz leve e soltinho", "Preparo: 20min", CategoryBuilder.buildCountryCategories()));
        return publications;
    }

}
