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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.saboresdomundo.R;
import br.com.saboresdomundo.model.Usuario;

public class SignUpTabFragment extends Fragment {
    View rootView;

    EditText email;

    EditText name;

    EditText password;

    Button button;

    float v = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.sign_up_fragment, container, false);

        email = rootView.findViewById(R.id.email);
        password = rootView.findViewById(R.id.password);
        name = rootView.findViewById(R.id.name);
        button = rootView.findViewById(R.id.button_signup);

        email.setTranslationY(300);
        password.setTranslationY(300);
        name.setTranslationY(300);
        button.setTranslationY(300);

        email.setAlpha(v);
        password.setAlpha(v);
        name.setAlpha(v);
        button.setAlpha(v);

        name.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        email.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(400).start();
        password.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        button.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(600).start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSignUpClick();
            }
        });

        return rootView;
    }

    public void buttonSignUpClick() {
        String nameString = name.getText().toString();
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(getActivity(), task -> {
                    String msg = task.isSuccessful() ? "SIGN UP OK!": "SIGN UP ERROR!";
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

                    if (task.isSuccessful()) {
                        Usuario tempUser = new Usuario();
                        tempUser.setLogin(emailString);
                        tempUser.setPassword(passwordString);
                        tempUser.setName(nameString);

                        DatabaseReference drUsers = FirebaseDatabase.getInstance().getReference("users");
                        drUsers.child(mAuth.getCurrentUser().getUid()).setValue(tempUser);

                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }
                });
    }
}
