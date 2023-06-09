package br.com.saboresdomundo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import br.com.saboresdomundo.R;


public class LoginTabFragment extends Fragment {

    View rootView;

    EditText email;

    EditText password;

    Button button;

    float v = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.login_tag_fragment, container, false);

        email = rootView.findViewById(R.id.email);
        password = rootView.findViewById(R.id.password);
        button = rootView.findViewById(R.id.button_login);

        email.setTranslationY(300);
        password.setTranslationY(300);
        button.setTranslationY(300);

        email.setAlpha(v);
        password.setAlpha(v);
        button.setAlpha(v);

        email.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(400).start();
        password.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        button.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(600).start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSignInClick();
            }
        });

        return rootView;
    }
    public void buttonSignInClick() {
        String login = email.getText().toString();
        String passwd = password.getText().toString();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(login, passwd)
                .addOnCompleteListener(getActivity(), task -> {
                    String msg = task.isSuccessful() ? "SIGN IN OK!": "SIGN IN ERROR!";
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    if(task.isSuccessful()){
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }
                });
    }

}
