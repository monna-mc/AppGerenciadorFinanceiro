package trabalho.finaly.gerenciadordedespesas.util;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

public class GerenciadorBD extends SQLiteOpenHelper{

	private static final String NOME_BANCO = "financas.db";
	private static final int VERSAO_SCHEMA = 1;
	
	private static final String LOG_TAG = "Finance";
	private final Context contexto;

	public GerenciadorBD(Context context) {
		super(context, NOME_BANCO, null, VERSAO_SCHEMA);
		this.contexto = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		try 
		{
		db.execSQL("CREATE TABLE despesa ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
				" descricao TEXT, valor REAL, data TEXT, status TEXT);");
		
		db.execSQL("CREATE TABLE caixa ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
				" saldo REAL, receita REAL, data TEXT);");
		
		} 
		catch (SQLException e) 
		{
			Log.e("Erro ao criar as tabelas e testar os dados", e.toString());
		} 
		finally 
		{
			db.endTransaction();
		}
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		Log.w(LOG_TAG, "Atualizando a base de dados da versão " + oldVersion + " para " + newVersion + ", que destríra todos os dados antigos");
		db.beginTransaction();
		
		try 
		{
			db.setTransactionSuccessful();
		}
		catch (SQLException e) 
		{
			Log.e("Erro ao atualizar as tabelas e testar os dados", e.toString());
			throw e;
		} 
		finally 
		{
			db.endTransaction();
		}
		
		onCreate(db);
	}
	
	public DespesasCursor RetornarDespesa(DespesasCursor.OrdenarPor ordenarPor) 
	{
		String sql = DespesasCursor.CONSULTA + (ordenarPor == DespesasCursor.OrdenarPor.NomeCrescente ? "ASC" : "DESC");
		SQLiteDatabase bd = getReadableDatabase();
		DespesasCursor cc = (DespesasCursor) bd.rawQueryWithFactory(new DespesasCursor.Factory(), sql, null, null);
		cc.moveToFirst();
		return cc;
	}
	
	public long InserirDespesa(String descricao, Double valor,String data, String status){
		SQLiteDatabase db = getReadableDatabase();
		
		try
		{
			ContentValues initialValues = new ContentValues();
			initialValues.put("descricao", descricao);
			initialValues.put("valor", valor);
			initialValues.put("data", data);
			initialValues.put("status", status);
			return db.insert("despesa", null, initialValues);
		}
		finally
		{
			db.close();
		}
	}
	
	public static class DespesasCursor extends SQLiteCursor
	{
		public static enum OrdenarPor{
			NomeCrescente,
			NomeDecrescente
		}
		
		private static final String CONSULTA = "SELECT * FROM despesa ORDER BY descricao ";
		
		private DespesasCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) 
		{
			super(db, driver, editTable, query);
		}
		
		private static class Factory implements SQLiteDatabase.CursorFactory
		{
			@Override
			public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) 
			{
				return new DespesasCursor(db, driver, editTable, query);
			}
		}
		
		public long getID()
		{
			return getLong(getColumnIndexOrThrow("_id"));
		}
		
		public String getDescricao()
		{
			return getString(getColumnIndexOrThrow("descricao"));
		}
		
		public String getValor() 
		{
			return getString(getColumnIndexOrThrow("valor"));
				
		}
		
		public String getStatus() 
		{
			return getString(getColumnIndexOrThrow("status"));
		}
		
		public String getData() 
		{
			return getString(getColumnIndexOrThrow("data"));
		}
	}
	
	public void inserirCaixa(Double saldo, Double receita, String data) {
		ContentValues valores = new ContentValues();

		valores.put("saldo", saldo);
		valores.put("receita", receita);
		valores.put("data", data);


		getWritableDatabase().insert("caixa","nome", valores);
	}
	
}

