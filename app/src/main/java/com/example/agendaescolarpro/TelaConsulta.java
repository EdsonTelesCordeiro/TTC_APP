package com.example.agendaescolarpro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaConsulta extends AppCompatActivity {

    EditText et_nome_aluno, et_matricula, et_matematica, et_portugues;
    Button bt_anterior, bt_proximo, bt_voltar_consulta;
    SQLiteDatabase db = null;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_consulta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et_nome_aluno = (EditText) findViewById(R.id.et_nome_aluno_consulta);
        et_matricula = (EditText) findViewById(R.id.et_matricula_consulta);
        et_matematica = (EditText) findViewById(R.id.et_matematica_consulta);
        et_portugues = (EditText) findViewById(R.id.et_portugues_consulta);
        bt_anterior = (Button) findViewById(R.id.bt_anterior);
        bt_proximo = (Button) findViewById(R.id.bt_proximo);
        bt_voltar_consulta = (Button) findViewById(R.id.bt_voltar_consulta);

        buscarDados();

        bt_voltar_consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaConsulta.this, TelaLaceNotas.class);
                startActivity(intent);

            }
        });



    }

    public void abrirDB() {
        try {
            db = openOrCreateDatabase("bancoLocalAgenda", MODE_PRIVATE, null);
        } catch (Exception ex) {
            msg("Erro ao abrir ou criar banco de dados");
        }



    }



    public void buscarDados() {
        abrirDB();

        cursor = db.query("atributosAlunos", new String[]{"nomeAluno", "matricula", "matematica", "portugues"},
                null,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor.getCount() !=0){
            cursor.moveToFirst();
            mostrarDados();

        }else {
            msg("Não foi Encontrado Registros");

        }
    }

    public void proximoRegistro(View v) {
        try {
            cursor.moveToNext();
            mostrarDados();
        } catch (
                Exception ex) {
            if (cursor.isAfterLast()) {
                msg("Não existem mais registros");
            } else {
                msg("Erro ao navegar pelos registros");
            }
        }
    }
    public void registroAnterior(View v) {
        try {
            cursor.moveToPrevious();
            mostrarDados();
        } catch (
                Exception ex) {
            if (cursor.isBeforeFirst()) {
                msg("Não existem mais registros");
            } else {
                msg("Erro ao navegar pelos registros");
            }
        }
    }

    @SuppressLint("Range")
    public void mostrarDados(){
        et_nome_aluno.setText(cursor.getString(cursor.getColumnIndex("nomeAluno")));
        et_matricula.setText(cursor.getString(cursor.getColumnIndex("matricula")));
        et_matematica.setText(cursor.getString(cursor.getColumnIndex("matematica")));
        et_portugues.setText(cursor.getString(cursor.getColumnIndex("portugues")));
    }
        public void msg (String txt){
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setMessage(txt);
            adb.setNeutralButton("ok", null);
            adb.show();

        }
    }
