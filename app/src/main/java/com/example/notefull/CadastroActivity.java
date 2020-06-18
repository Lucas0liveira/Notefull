package com.example.notefull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);
        final EditText edtNome = findViewById(R.id.edtNome);
        final EditText edtEmail = findViewById(R.id.edtEmail);
        final EditText edtSenha = findViewById(R.id.edtSenhaSignIn);
        final EditText edtSenhaConfirm = findViewById(R.id.edtSenhaConfirm);
        Button signIn = findViewById(R.id.button);

        signIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String nome = edtNome.getText().toString();
                String email = edtEmail.getText().toString();
                String senha = edtSenha.getText().toString();
                String senhaConfirm = edtSenhaConfirm.getText().toString();
                onSubmitClicked(nome, email, senha, senhaConfirm);
            }
        });
    }

    public void onSubmitClicked(String nome, String email, String senha, String senhaConfirm){

        if(!senha.equals(senhaConfirm)){
            Log.d(null, "As senhas não batem!");
        } else {
            User user = new User(nome, email, senha);
            DatabaseHelper db = new DatabaseHelper(this);
            if(!db.SignIn(user)){
                Log.d(null, "Email já cadastrado!");
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }

    }
}