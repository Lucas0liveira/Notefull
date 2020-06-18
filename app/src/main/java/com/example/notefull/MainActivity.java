package com.example.notefull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edtEmail = findViewById(R.id.edtEmail);
        EditText edtSenha = findViewById(R.id.edtSenhaLogin);
        Button enter = findViewById(R.id.buttonEnter);
        Button signIn = findViewById(R.id.buttonSignIn);

        final String email = edtEmail.getText().toString();
        final String password = edtSenha.getText().toString();

        enter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onLoginClicked(email, password);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CadastroActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onLoginClicked(String email, String password){
        DatabaseHelper db = new DatabaseHelper(this);
        User user = new User("", email, password);
        if(!db.Login(user)){
            Log.d(null, "Senha ou email incorretos!");
        } else {
            Log.d(null, "LOGIN BEM SUCEDIDO");
            Intent intent = new Intent(this, MainActivity.class); //TROCAR ACTIVITY
            startActivity(intent);
        }

    }

}