package com.example.agendaescolarpro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.widget.*;
import android.database.Cursor;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaLaceNotas extends AppCompatActivity {
  EditText et_nome_aluno,et_matricula,et_matematica,et_portugues;
  Button bt_gravar,bt_consultar,bt_voltar_log;

   SQLiteDatabase db=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_lace_notas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        et_nome_aluno = (EditText) findViewById(R.id.et_nome_aluno);
        et_matricula = (EditText) findViewById(R.id.et_matricula);
        et_matematica = (EditText) findViewById(R.id.et_matematica);
        et_portugues = (EditText) findViewById(R.id.et_portugues);
        bt_gravar = (Button) findViewById(R.id.bt_gravar);
        bt_consultar = (Button) findViewById(R.id.bt_consultar);
        bt_voltar_log = (Button) findViewById(R.id.bt_voltar);

        abrirDB();
        abrirOuCriarTabela();
        fecharDB();
    }
    public void abrirDB(){
        try{
            db=openOrCreateDatabase("bancoLocalAgenda",MODE_PRIVATE, null);
        }catch (Exception ex){
            msg("Erro ao abrir ou criar banco de dados");
        }
        et_nome_aluno.setText(null);
        et_matricula.setText(null);
        et_matematica.setText(null);
        et_portugues.setText(null);

        bt_consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaLaceNotas.this, TelaConsulta.class);
                startActivity(intent);

            }
        });
        bt_voltar_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaLaceNotas.this, TelaPrincipal.class);
                startActivity(intent);

            }
        });
    }
    public void inserirAtributosAlunos(View v) {
        abrirDB();
        String st_nomeAluno, st_matricula, st_matematica, st_portugues;
        st_nomeAluno = et_nome_aluno.getText().toString();
        st_matricula = et_matricula.getText().toString();
        st_matematica = et_matematica.getText().toString();
        st_portugues = et_portugues.getText().toString();
        if (st_nomeAluno == "" || st_matricula == "" || st_matematica == "" || st_portugues == "") ;
        {
        msg("Todos os campos tem que ser preenchidos");

        }
        try {
            db.execSQL("INSERT INTO atributosAlunos(nomeAluno, matricula, matematica,portugues) VALUES ('" + st_nomeAluno + "','" + st_matricula + "','" + st_matematica + "','" + st_portugues + "')");
        }catch(Exception ex) {
            msg("Erro ao inserir Atributos");
        }finally {
            msg("Registro inserido com sucesso");
        }

    }



    public void fecharDB(){
        db.close();
    }
    public void msg(String txt){
        AlertDialog.Builder adb=new AlertDialog.Builder(this);
        adb.setMessage(txt);
        adb.setNeutralButton("ok",null);
        adb.show();

    }
    public void abrirOuCriarTabela(){
        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS atributosAlunos(id INTEGER PRIMARY KEY, nomeAluno TEXT, matricula TEXT, matematica TEXT, portugues TEXT);");

        }catch (Exception ex){
            msg("Erro ao criar tabela");
        }

    }

}