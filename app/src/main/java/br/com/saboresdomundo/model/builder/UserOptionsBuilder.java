package br.com.saboresdomundo.model.builder;

import java.util.ArrayList;
import java.util.List;

import br.com.saboresdomundo.R;
import br.com.saboresdomundo.activity.NewPublicationActivity;
import br.com.saboresdomundo.model.UserOptions;

public class UserOptionsBuilder {

    public static List<UserOptions> getOptions(){

        List<UserOptions> options = new ArrayList<>();
        options.add(new UserOptions("Nova receita", "Cadastro de novas receitas", R.mipmap.new_food, NewPublicationActivity.class));
        options.add(new UserOptions("Minhas receitas", "Suas receitas cadastradas", R.mipmap.my_food_icon, NewPublicationActivity.class));
        options.add(new UserOptions("Informações", "Informações sobre o aplicativo", R.mipmap.info_icon, NewPublicationActivity.class));

        return options;
    }

}
