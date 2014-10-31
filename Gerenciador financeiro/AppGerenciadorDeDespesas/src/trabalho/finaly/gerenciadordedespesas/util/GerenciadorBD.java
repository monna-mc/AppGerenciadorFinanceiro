package trabalho.finaly.gerenciadordedespesas.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GerenciadorBD extends SQLiteOpenHelper{

	private static final String NOME_BANCO = "financas.db";
	private static final int VERSAO_SCHEMA = 1;

	public GerenciadorBD(Context context) {
		super(context, NOME_BANCO, null, VERSAO_SCHEMA);		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE despesa ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
				" descricao TEXT, valor REAL, data TEXT, status TEXT);");
		
		db.execSQL("CREATE TABLE caixa ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
				" saldo REAL, receita REAL, data TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
	
	public void inserirDespesa(String descricao, Double valor,String data, String status) {
		ContentValues valores = new ContentValues();

		valores.put("descricao", descricao);
		valores.put("valor", valor);
		valores.put("data", data);
		valores.put("status", status);

		getWritableDatabase().insert("despesa","nome", valores);
	}
	
	public Cursor obterDespesas() {
		return getReadableDatabase().rawQuery("select id, descricao, valor, data, " +
				"status FROM despesa ORDER BY valor", null);
	}
	
	public void inserirCaixa(Double saldo, Double receita, String data) {
		ContentValues valores = new ContentValues();

		valores.put("saldo", saldo);
		valores.put("receita", receita);
		valores.put("data", data);


		getWritableDatabase().insert("caixa","nome", valores);
	}
	
	public Cursor obterCaixa() {
		return getReadableDatabase().rawQuery("select id, saldo, receita, data, " +
				" FROM caixa ORDER BY data", null);
	}
	
	
}

