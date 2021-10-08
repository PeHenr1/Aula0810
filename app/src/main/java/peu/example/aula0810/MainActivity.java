package peu.example.aula0810;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // atributos
    SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // criando ou abrindo o banco de dados
        bd = openOrCreateDatabase( "nomedobanco", MODE_PRIVATE, null );

        // variável string para gerar comandos SQL
        String cmd;

        // criar a tabela pessoas, se a mesma não existir
        cmd = "CREATE TABLE IF NOT EXISTS pessoas ";
        cmd = cmd + "( id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR, email VARCHAR)";

        // executar o comando SQL no banco de dados que criamos
        bd.execSQL( cmd );

        // Variáveis para receber os dados lidos do banco:
        int id;
        String nome, email;

        // String que será montada para exibir os dados
        String msg;

        // criando SQL para consultar dados
        // OBS! Tive alguns problemas usando o '*' (select * from...)
        // Sugiro colocar os nomes dos campos na consulta
        cmd = "SELECT id, nome, email FROM pessoas";

        // executando o comando no banco de dados
        Cursor dadosLidos = bd.rawQuery( cmd, null );

        // Como já vimos, neste momento, o ponteiro do cursor está posicionado
        // na linha ANTERIOR a primeira linha de dados (se houver).
        // Utilizamos o comando moveToNext() para avançar para a próxima linha.
        // Caso esta linha esteja vazia, o comando retorna FALSE.
        // Senão, podemos ler os dados.

        while ( dadosLidos.moveToNext() ) {
            // Se entrou aqui, temos dados para ler.
            // O método getColumnIndex() retorna o número do campo, a partir de seu nome.

            id = dadosLidos.getInt( dadosLidos.getColumnIndex( "id" ) );
            nome = dadosLidos.getString( dadosLidos.getColumnIndex( "nome" ) );
            email = dadosLidos.getString( dadosLidos.getColumnIndex( "email" ) );

            // vamos criar uma String para exibir os dados:
            msg = "ID: " + id + " NOME: " + nome + " EMAIL: " + email;

            // vamos exibir os dados em um Toast
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }

        /*
        // comandos SQL para inserir registros na tabela pessoas

        cmd = "INSERT INTO pessoas (nome, email) values ( 'Ana', 'ana@gmail' )";
        bd.execSQL( cmd );

        cmd = "INSERT INTO pessoas (nome, email) values ( 'Bianca', 'bianca@gmail' )";
        bd.execSQL( cmd );

        cmd = "INSERT INTO pessoas (nome, email) values ( 'Carla', 'carla@gmail' )";
        bd.execSQL( cmd );
         */

    }
}