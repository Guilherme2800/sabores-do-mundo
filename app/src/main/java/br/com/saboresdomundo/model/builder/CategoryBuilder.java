package br.com.saboresdomundo.model.builder;

import java.util.ArrayList;
import java.util.List;

import br.com.saboresdomundo.R;
import br.com.saboresdomundo.model.Category;

public class CategoryBuilder {

    public static List<Category> buildDefultCategories(){

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(R.mipmap.category_massa, "Massa"));
        categories.add(new Category(R.mipmap.category_vegana, "Vegana"));
        categories.add(new Category(R.mipmap.category_asiatica, "Asiatica"));
        categories.add(new Category(R.mipmap.category_hamburguer, "Hamburguers"));

        return categories;
    }

}
