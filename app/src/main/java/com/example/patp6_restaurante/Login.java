package com.example.patp6_restaurante;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
        EditText edUser, edSenha;
        Button btLogin;
        private SQLiteDatabase bancoDados;
        Cursor c;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

//identifica elementos
            edUser = findViewById(R.id.edUser);
            edSenha = findViewById(R.id.edSenha);
            btLogin = findViewById(R.id.btLogin);

//funcao criar o banco de dados
            criarBancoDados();

//clique botao logar - abrir menu mas falta verificar autenticação

            btLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String strUser = edUser.getText().toString();
                    String strSenha = edSenha.getText().toString();

                    if ((strUser.equals("admin")) && (strSenha.equals("admin"))) {
//logou, abrir a proxima janela
                    Intent logarmenu = new Intent(getApplicationContext(),Menu.class);
                    startActivity(logarmenu);
                    } else {
                        FuncaoMostraCaixaTexto msg = new FuncaoMostraCaixaTexto("Usuário ou senha incorretos!", "Erro", Login.this);
                    }
                }
            });
        }

    private void criarBancoDados() {
            try {
                bancoDados = openOrCreateDatabase("bdRestaurante",MODE_PRIVATE,null);
                bancoDados.execSQL("CREATE TABLE IF NOT EXISTS login(idlogin INTEGER PRIMARY KEY AUTOINCREMENT, user VARCHAR, senha VARCHAR)");
                bancoDados.execSQL("CREATE TABLE IF NOT EXISTS comanda(numero INTEGER PRIMARY KEY, situacao INTEGER)");
                bancoDados.execSQL("CREATE TABLE IF NOT EXISTS cliente(idcliente INTEGER PRIMARY KEY AUTOINCREMENT, numero_cli INTEGER, mesa INTEGER, cliente VARCHAR, telefone VARCHAR, FOREIGN KEY(numero_com) REFERENCES comanda(numero))");
                bancoDados.execSQL("CREATE TABLE IF NOT EXISTS pedido(idpedido INTEGER PRIMARY KEY AUTOINCREMENT, idcliente_ped INTEGER, idcardapio_ped INTEGER, quantidade VARCHAR, FOREIGN KEY(idcliente_ped) REFERENCES cliente(idcliente),FOREIGN KEY(idcardapio_ped) REFERENCES cardapio(idcardapio)) ");
                bancoDados.execSQL("CREATE TABLE IF NOT EXISTS cardapio(idcardapio INTEGER PRIMARY KEY AUTOINCREMENT, descricao VARCHAR, valor VARCHAR)");
                bancoDados.execSQL("INSERT OR REPLACE INTO login (user, senha) VALUES ('admin', 'admin')");
                bancoDados.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }