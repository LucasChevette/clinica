package com.example.clipack;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Calendar;
import java.util.Date;

public class Info_agenda extends AppCompatActivity {

    private TextView info;
    private EditText nome, data, hora;
    private CheckBox m, f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_agenda);
        info = findViewById(R.id.title_info);
        nome = findViewById(R.id.info_nom);
        data = findViewById(R.id.editTextDate);
        hora = findViewById(R.id.editTextTime);
        m = findViewById(R.id.checkBox);
        f = findViewById(R.id.checkBox3);

        Intent i = getIntent();
        String servico = i.getStringExtra("c");
        if(servico == null){
            servico = i.getStringExtra("o");
            if(servico == null){
                servico = i.getStringExtra("g");
                if(servico == null){
                    servico = i.getStringExtra("d");
                    info.setText("Dermatologista");
                } else {
                    info.setText("Gastro");
                }
            } else {
                info.setText("Ortopedista");
            }
        } else {
            info.setText("Cardiologista");
        }

        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m.isChecked()){
                    if(f.isChecked()){
                        f.setChecked(false);
                    }
                }
            }
        });

        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(f.isChecked()){
                    if(m.isChecked()){
                        m.setChecked(false);
                    }
                }
            }
        });

    }

    public void confirmarConsulta(View n){
        String sexo;
        AlertDialog a = new AlertDialog.Builder(Info_agenda.this).create();
        a.setTitle("Confirmado");
        if(m.isChecked()){
           sexo = "Masculino";
        } else {
            sexo = "Feminino";
        }
        a.setMessage(nome.getText() + "\n" + info.getText()+ "\n" + data.getText()+ "\t" + hora.getText()+ "\n" + sexo);
        a.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent j= new Intent(getApplicationContext(), Servicos_clipack.class);
                startActivity(j);
            }
        });
        a.show();
    }
}