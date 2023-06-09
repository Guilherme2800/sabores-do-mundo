package br.com.saboresdomundo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.saboresdomundo.HomeActivity;
import br.com.saboresdomundo.R;
import br.com.saboresdomundo.model.Usuario;

public class RegisterActivity extends AppCompatActivity {

    EditText edEmail;
    EditText edPassword;

    EditText edName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edName = findViewById(R.id.registerName);
        edEmail = findViewById(R.id.registerEmail);
        edPassword = findViewById(R.id.registerPassword);

        ImageView backRegister = findViewById(R.id.buttonBackRegister);
        backRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView goLogin = findViewById(R.id.buttonGoLogin);
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button button = findViewById(R.id.buttonRegister);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSignUpClick(view);
            }
        });
    }

    public void buttonSignUpClick(View view) {
        String name = edName.getText().toString();
        String email = edEmail.getText().toString();
        String password = edPassword.getText().toString();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    String msg = task.isSuccessful() ? "SIGN UP OK!": "SIGN UP ERROR!";
                    Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();

                    if (task.isSuccessful()) {
                        Usuario tempUser = new Usuario();
                        tempUser.setLogin(email);
                        tempUser.setPassword(password);
                        tempUser.setName(name);

                        DatabaseReference drUsers = FirebaseDatabase.getInstance().getReference("users");
                        drUsers.child(mAuth.getCurrentUser().getUid()).setValue(tempUser);

                        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                    }
                });
    }
}