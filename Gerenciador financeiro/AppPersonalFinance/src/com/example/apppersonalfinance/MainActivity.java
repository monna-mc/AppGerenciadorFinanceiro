package com.example.apppersonalfinance;

import java.util.List;

import com.example.DAO.ReceitaAdapter;
import com.example.DAO.ReceitaDAO;
import com.example.apppersonalfinance.R;
import com.example.modelo.Receita;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	
	 private static final int INCLUIR = 0;
	    private static final int ALTERAR = 1;
	  
	    private ReceitaDAO lReceitaDAO; //instância responsável pela persistência dos dados
	    List<Receita> lstReceitas;  //lista cadastrada no BD
	    ReceitaAdapter adapter;   //Adapter responsável por apresentar os contatos na tela

	    boolean blnShort = false;
	    int Posicao = 0;    //determinar a posição do contato dentro da lista
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		lReceitaDAO = new ReceitaDAO(this);
        lReceitaDAO.open();
 
        lstReceitas = lReceitaDAO.Consultar();
 
        adapter = new ReceitaAdapter(this, lstReceitas);
        setListAdapter(adapter);
   
        registerForContextMenu(getListView());
	}
	
	 public void onClickAdd(View view) {
	        switch (view.getId()) {
	        case R.id.add:
	            InserirReceita();
	            break;
	        }
	    }
	 
	 
	//Rotina executada quando finalizar a Activity ContatoUI
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        Receita lEntrada = null;
	         
	        try
	        {
	            super.onActivityResult(requestCode, resultCode, data);
	            if (resultCode == Activity.RESULT_OK)
	            {
	                //obtem dados inseridos/alterados na Activity ContatoUI
	                lEntrada = (Receita)data.getExtras().getSerializable("entrada");
	                 
	                //o valor do requestCode foi definido na função startActivityForResult
	                if (requestCode == INCLUIR)
	                {
	                    //verifica se digitou algo no nome do contato
	                    if (!lEntrada.getDescricao().equals("")) 
	                    {
	                        //necessário abrir novamente o BD pois ele foi fechado no método onPause()
	                        lReceitaDAO.open();
	                         
	                        //insere o contato no Banco de Dados SQLite
	                        lReceitaDAO.Inserir(lEntrada);
	                         
	                        //insere o contato na lista de contatos em memória
	                        lstReceitas.add(lEntrada);
	                    }
	                }else if (requestCode == ALTERAR){
	                    lReceitaDAO.open();
	                    //atualiza o contato no Banco de Dados SQLite
	                    lReceitaDAO.Alterar(lEntrada);
	                     
	                    //atualiza o contato na lista de contatos em memória
	                    lstReceitas.set(Posicao, lEntrada);
	                }
	                 
	                //método responsável pela atualiza da lista de dados na tela
	                adapter.notifyDataSetChanged();
	            }
	        }
	        catch (Exception e) {
	            trace("Erro : " + e.getMessage());
	        }        
	    }    
	     
	    private void InserirReceita(){
	        try
	        {
	            //a variável "tipo" tem a função de definir o comportamento da Activity
	            //ContatoUI, agora a variável tipo está definida com o valor "0" para
	            //informar que será uma inclusão
	             
	        	Intent intent = new Intent (MainActivity.this , ReceitaActivity.class);
				intent.putExtra("tipo", INCLUIR);
				startActivityForResult(intent, INCLUIR);
	        	
	          
	        }
	        catch (Exception e) {
	            trace("Erro : " + e.getMessage());
	        }            
	    }    
	     
	    @Override
	    protected void onResume() {
	        //quando a Activity main receber o foco novamente abre-se novamente a conexão
	        lReceitaDAO.open();
	        super.onResume();
	    }
	 
	    @Override
	    protected void onPause() {
	        //toda vez que o programa peder o foco fecha-se a conexão com o BD
	        lReceitaDAO.close();
	        super.onPause();
	    }
	 
	    public void toast (String msg)
	    {
	        Toast.makeText (getApplicationContext(), msg, Toast.LENGTH_SHORT).show ();
	    } 
	     
	    private void trace (String msg) 
	    {
	        toast (msg);
	    }
	 
	    @Override   
	    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {        
	        try
	        {
	            //Criação do popup menu com as opções que termos sobre
	            //nossos Contatos
	             
	            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	            if (!blnShort)
	            {
	                Posicao = info.position;
	            }
	            blnShort = false;
	             
	            menu.setHeaderTitle("Selecione:");            
	            //a origem dos dados do menu está definido no arquivo arrays.xml 
	            String[] menuItems = getResources().getStringArray(R.array.menu);             
	            for (int i = 0; i<menuItems.length; i++) {                
	                menu.add(Menu.NONE, i, i, menuItems[i]);            
	            }        
	        }catch (Exception e) {
	            trace("Erro : " + e.getMessage());
	        }            
	    }  
	 
	     
	    //Este método é disparado quando o usuário clicar em um item do ContextMenu
	    @Override   
	    public boolean onContextItemSelected(MenuItem item) {       
	        Receita lEntrada = null;
	        try
	        {
	            int menuItemIndex = item.getItemId();        
	 
	            //Carregar a instância POJO com a posição selecionada na tela
	            lEntrada = (Receita) getListAdapter().getItem(Posicao);
	             
	            if (menuItemIndex == 0){
	                //Carregar a Activity ContatoUI com o registro selecionado na tela
	                 
	                Intent it = new Intent(this, ReceitaActivity.class);
	                it.putExtra("tipo", ALTERAR);
	                it.putExtra("entrada", lEntrada);
	                startActivityForResult(it, ALTERAR); //chama a tela de alteração
	            }else if (menuItemIndex == 1){
	                //Excluir do Banco de Dados e da tela o registro selecionado
	                 
	                lReceitaDAO.Excluir(lEntrada);
	                lstReceitas.remove(lEntrada);
	                adapter.notifyDataSetChanged(); //atualiza a tela
	            }
	        }catch (Exception e) {
	            trace("Erro : " + e.getMessage());
	        }   
	        return true;   
	         
	    }    
	 
	    @Override
	    protected void onListItemClick(ListView l, View v, int position, long id) {
	        super.onListItemClick(l, v, position, id);
	        //por padrão o ContextMenu, só é executado através de LongClick, mas
	        //nesse caso toda vez que executar um ShortClick, abriremos o menu
	        //e também guardaremos qual a posição do itm selecionado
	         
	        Posicao = position;
	        blnShort = true;
	        this.openContextMenu(l);
	    }

}
