package com.example.DAO;
 
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
//Classe respons�vel pela cria��o do Banco de Dados e tabelas
public class BaseDAO extends SQLiteOpenHelper 
{
 
    public static final String TBL_RECEITA = "receita";
    public static final String RECEITA_ID = "_id";
    public static final String RECEITA_DESCRICAO = "descricao";
    public static final String RECEITA_RECEITA = "receita";
    public static final String RECEITA_DATA = "data";
 
    private static final String DATABASE_NAME = "financas.db";
    private static final int DATABASE_VERSION = 1;
 
    //Estrutura da tabela  (sql statement)
    private static final String CREATE_RECEITA = "create table " +
        TBL_RECEITA + "( " + RECEITA_ID       + " integer primary key autoincrement, " + 
        RECEITA_DESCRICAO     + " text not null, " +
        RECEITA_RECEITA + " REAL, " +
        RECEITA_DATA + " text not null);";
 
    public BaseDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase database) {
        //Cria��o da tabela
        database.execSQL(CREATE_RECEITA);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Caso seja necess�rio mudar a estrutura da tabela
        //dever� primeiro excluir a tabela e depois recri�-la
        db.execSQL("DROP TABLE IF EXISTS" + TBL_RECEITA);
        onCreate(db);
    }
 
}