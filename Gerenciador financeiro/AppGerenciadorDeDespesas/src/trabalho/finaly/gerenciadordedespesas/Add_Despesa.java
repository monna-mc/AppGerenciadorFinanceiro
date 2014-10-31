package trabalho.finaly.gerenciadordedespesas;

import trabalho.finaly.gerenciadordedespesas.model.Despesa;
import trabalho.finaly.gerenciadordedespesas.util.GerenciadorBD;
import trabalho.finaly.gerenciadordedespesas.util.GerenciadorBD.DespesasCursor;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;


public class Add_Despesa extends  Activity {

	Button btnSalvar, btnCancelar, btnResetar , btnNovo;
	
	Cursor listaDespesas;
	Despesa atual = null;

	EditText descricao = null;
	EditText valor = null;
	EditText data = null;
	RadioGroup status = null;
	GerenciadorBD gerenciador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CarregarListagem();
	}
	
	public void CarregarListagem()
    {
    	setContentView(R.layout.lista_despesa);
        
        btnNovo = (Button)findViewById(R.id.btnNovo);
        btnNovo.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				CarregarCadastro();
			}});
        
        CarregarLista(this);
    }
	
	 public void CarregarCadastro()
	    {
	    	setContentView(R.layout.add__despesa);
	    	
	        btnCancelar = (Button)findViewById(R.id.cancelarButton);
	        btnCancelar.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					CarregarListagem();
				}});
	        
	        descricao = (EditText)findViewById(R.id.editDescricao);
	        valor = (EditText)findViewById(R.id.editValor);
	        status = (RadioGroup) findViewById(R.id.statusGroup);
	        data = (EditText) findViewById(R.id.date);
	        
	        
	        btnSalvar = (Button)findViewById(R.id.SalvarButton);
	        btnSalvar.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					SalvarCadastro();
				}});
	    }
	 
	 public void SalvarCadastro(){
		 
		 String mDstatus = null;

			switch (status.getCheckedRadioButtonId()){
			case R.id.statusPaga:
				mDstatus = "paga";
				break;
			case R.id.statusNaoPaga:
				mDstatus = "Não paga";
				break;
			}
		 
	    	GerenciadorBD db = new GerenciadorBD(this);
	    	
	    	Double mDvalor = Double.parseDouble(valor.getText().toString());
	    	
			db.InserirDespesa(descricao.getText().toString(),mDvalor ,data.getText().toString(), mDstatus);
			setContentView(R.layout.lista_despesa);
			CarregarLista(this);
			
	    }
			
			public void CarregarLista(Context c)
			{
		    	GerenciadorBD db = new GerenciadorBD(c);
		        DespesasCursor cursor = db.RetornarDespesa(DespesasCursor.OrdenarPor.NomeCrescente);
		        
		        for( int i=0; i <cursor.getCount(); i++)
		        {
		        	cursor.moveToPosition(i);
		        	ImprimirLinha(cursor.getDescricao(), cursor.getValor(), cursor.getStatus(), cursor.getData());
		        }
		    }
			
			public void ImprimirLinha(String descricao, String valor, String data, String status)
		    {
		    	TextView tv = (TextView)findViewById(R.id.listaDeDespesas);
		    	
		    	if(tv.getText().toString().equalsIgnoreCase("Nada cadastrado."))
		    		tv.setText("");
		    	
		    	tv.setText(tv.getText() + "\r\n" +"Descricao: "+ descricao + "\n " +"Valor: "+ valor + "\n"+ "Status: "+status + "\n"+ "Data: "+data);
		    }
	

	@Override
	public void onDestroy() {
		super.onDestroy();
		gerenciador.close();
	}

}
