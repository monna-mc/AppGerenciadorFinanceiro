package com.example.DAO;
 
import java.util.ArrayList;
import java.util.List;

import com.example.modelo.Receita;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
 
public class ReceitaDAO {
 
    private SQLiteDatabase database;
    private BaseDAO dbHelper;
 
    //Campos da tabela Agenda
    private String[] colunas = {
    		BaseDAO.RECEITA_ID, 
            BaseDAO.RECEITA_DESCRICAO,
            BaseDAO.RECEITA_RECEITA, 
            BaseDAO.RECEITA_DATA };
 
    public ReceitaDAO(Context context) {
        dbHelper = new BaseDAO(context);
    }
 
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
 
    public void close() {
        dbHelper.close();
    }
 
    public long Inserir(Receita pValue) {
        ContentValues values = new ContentValues();
     
        //Carregar os valores nos campos que será incluído
        values.put(BaseDAO.RECEITA_DESCRICAO, pValue.getDescricao());
        values.put(BaseDAO.RECEITA_RECEITA, pValue.getReceita());
        values.put(BaseDAO.RECEITA_DATA, pValue.getData());
         
        return database.insert(BaseDAO.TBL_RECEITA, null, values);
    }
     
     
    public int Alterar(Receita pValue) {
        long id = pValue.getId();
        ContentValues values = new ContentValues();
         
        //Carregar os novos valores nos campos que serão alterados
        values.put(BaseDAO.RECEITA_DESCRICAO, pValue.getDescricao());
        values.put(BaseDAO.RECEITA_RECEITA, pValue.getReceita());
        values.put(BaseDAO.RECEITA_DATA, pValue.getData());
 
        //Alterar o registro com base no ID
        return database.update(BaseDAO.TBL_RECEITA, values, BaseDAO.RECEITA_ID + " = " + id, null);
    }
 
    public void Excluir(Receita pValue) {
        long id = pValue.getId();
         
        //Exclui o registro com base no ID
        database.delete(BaseDAO.TBL_RECEITA, BaseDAO.RECEITA_ID + " = " + id, null);
    }
 
    public List<Receita> Consultar() {
        List<Receita> lstEntrada = new ArrayList<Receita>();
 
        //Consulta para trazer todos os dados da tabela ordenados pela coluna Descricao
        Cursor cursor = database.query(BaseDAO.TBL_RECEITA, colunas,null, null, null, null, BaseDAO.RECEITA_DESCRICAO);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Receita lReceita = cursorToReceita(cursor);
            lstEntrada.add(lReceita);
            cursor.moveToNext();
        }
         
        //Tenha certeza que você fechou o cursor
        cursor.close();
        return lstEntrada;
    }
 
    //Converter o Cursor de dados no objeto Receita
    private Receita cursorToReceita(Cursor cursor) {
        Receita lReceita = new Receita();
        lReceita.setId(cursor.getLong(0));
        lReceita.setDescricao(cursor.getString(1));
        lReceita.setReceita(cursor.getDouble(2));
        lReceita.setData(cursor.getString(3));
        return lReceita;
    }
}