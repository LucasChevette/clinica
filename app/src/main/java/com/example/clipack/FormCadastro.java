package com.example.clipack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FormCadastro extends AppCompatActivity {

    public static Object Class;
    private EditText edit_nome, edit_email, edit_cpf, edit_senha;
    private Button cad_button;
    String[] mensagens = {"preencha todos os campos", "realizado com sucesso"};
    String usuarioID;


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forn_cadastro);
        iniciarcomponentes();
    cad_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
                String nome = edit_nome.getText().toString();
                String email = edit_email.getText().toString();
                String cpf = edit_cpf.getText().toString();
                String senha = edit_senha.getText().toString();

                if(nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || senha.isEmpty()){
                    Snackbar snackbar = Snackbar.make(view,mensagens[0],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.RED);
                    snackbar.show();
                }else{
                    cadastrarUsuario(view);


                }
        }
    });
}
        private void cadastrarUsuario(View view) {
            String email = edit_email.getText().toString();
            String senha = edit_senha.getText().toString();

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        SalvarDadosusuarios();

                        Snackbar snackbar = Snackbar.make(view,mensagens[1],Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.RED);
                        snackbar.show();

                    } else {
                        String erro;
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthWeakPasswordException e) {
                            erro = "Digite uma senha com o mínimo 6 caracteres";
                        } catch(FirebaseAuthUserCollisionException e) {
                            erro = "Este conta já foi cadastrada";
                        } catch(FirebaseAuthInvalidCredentialsException e) {
                            erro = "E-mail inválido";
                        } catch(Exception e){
                            erro = "Erro ao cadastrar usuário";
                        }
                        Snackbar snackbar = Snackbar.make(view,erro,Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.RED);
                        snackbar.show();
                    }
                }

            });}

    private void SalvarDadosusuarios() {
        String nome = edit_nome.getText().toString();
        String cpf = edit_cpf.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> usuarios = new HashMap<>();
        usuarios.put("nome",nome);
        usuarios.put("cpf",cpf);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db", "Sucesso ao salvar os dados");
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_erro", "Erro ao salvar os dados" + e.toString());
            }
        });
    }


    public void chamarLogin(View b){
        Intent i = new Intent(getApplicationContext(), FormLogin.class);
        startActivity(i);
    }

    public void iniciarcomponentes() {
        edit_nome = findViewById(R.id.edit_nome);
        edit_email = findViewById(R.id.edit_email);
        edit_cpf = findViewById(R.id.edit_cpf);
        edit_senha = findViewById(R.id.edit_senha);
        cad_button = findViewById(R.id.cad_button);
    }
    }