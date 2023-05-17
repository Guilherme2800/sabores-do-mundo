package br.com.saboresdomundo.model.builder;

import java.util.ArrayList;
import java.util.List;

import br.com.saboresdomundo.R;
import br.com.saboresdomundo.model.Category;

public class CategoryBuilder {

    public static List<Category> buildDefultCategories(){

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(R.mipmap.category_massa, "Massa", R.mipmap.category_img_massa2_foreground));
        categories.add(new Category(R.mipmap.category_vegana, "Vegana", R.mipmap.category_img_vegana_foreground));
        categories.add(new Category(R.mipmap.category_asiatica, "Asiatica", R.mipmap.category_img_asiatica_foreground));
        categories.add(new Category(R.mipmap.category_hamburguer, "Hamburguer", R.mipmap.category_img_burguer2_foreground));

        return categories;
    }

    public static List<Category> buildCountryCategories(){

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(R.mipmap.country_flag_brasil, "Brasil", R.mipmap.country_background_brasil_foreground));
        categories.add(new Category(R.mipmap.country_flag_italia, "Itália", R.mipmap.country_background_italia_foreground));
        categories.add(new Category(R.mipmap.country_flag_japao, "Japão", R.mipmap.country_background_japao_foreground));

        return categories;
    }

}
