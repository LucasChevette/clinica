package com.example.clipack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Servicos_clipack extends AppCompatActivity {

    private CardView b1, b2, b3, b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicos_clipack);
        b1 = findViewById(R.id.car_cardio);
        b2 = findViewById(R.id.car_orto);
        b3 = findViewById(R.id.car_gastro);
        b4 = findViewById(R.id.car_derma);

        b1.setOnClickListener(this::onClick);
        b2.setOnClickListener(this::onClick);
        b3.setOnClickListener(this::onClick);
        b4.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        switch (view.getId()){
            case R.id.car_cardio:
                chamarAgendamento("c", "Cardiologista");
                break;
            case R.id.car_orto:
                chamarAgendamento("o", "Ortopedista");
                break;
            case R.id.car_gastro:
                chamarAgendamento("g", "Gastro");
                break;
            case R.id.car_derma:
                chamarAgendamento("d", "Dermatologista");
                break;
        }
    }

    public void chamarAgendamento(String codigo, String mensagem) {
        Intent i = new Intent(getApplicationContext(), Info_agenda.class);
        i.putExtra(codigo, mensagem);
        startActivity(i);
    }
}