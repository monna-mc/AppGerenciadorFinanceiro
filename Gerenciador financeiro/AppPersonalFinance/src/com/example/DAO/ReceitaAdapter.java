package com.example.DAO;
 
import java.util.List;

import com.example.apppersonalfinance.R.id;
import com.example.apppersonalfinance.R.layout;
import com.example.modelo.Receita;
 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

 
public class ReceitaAdapter extends BaseAdapter  {
    private Context context;
 
    private List<Receita> lstReceita;
    private LayoutInflater inflater;
 
    public ReceitaAdapter(Context context, List<Receita> listEntrada) {
        this.context = context;
        this.lstReceita = listEntrada;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
     
    //Atualizar ListView de acordo com o lstReceita
    @Override
    public void notifyDataSetChanged() {   
        try{
            super.notifyDataSetChanged();
        }catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    } 
         
    public int getCount() {
        return lstReceita.size();
    }
 
    //Remover item da lista
    public void remove(final Receita item) {
        this.lstReceita.remove(item);
    } 
     
    //Adicionar item na lista
    public void add(final Receita item) {
        this.lstReceita.add(item);
    }    
     
    public Object getItem(int position) {
        return lstReceita.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        try
        {
             
        	Receita receita = lstReceita.get(position);
 
            //O ViewHolder irá guardar a instâncias dos objetos do row
            ViewHolder holder;
             
            //Quando o objeto convertView não for nulo nós não precisaremos inflar
            //os objetos do XML, ele será nulo quando for a primeira vez que for carregado
            if (convertView == null) {
                convertView = inflater.inflate(layout.receita_row, null);
                 
                //Cria o Viewholder e guarda a instância dos objetos
                holder = new ViewHolder();
                holder.tvDescricao = (TextView) convertView.findViewById(id.txtDescricao);
                holder.tvReceita =(TextView) convertView.findViewById(id.txtReceita);
                holder.tvData = (TextView) convertView.findViewById(id.txtData);
                 
                convertView.setTag(holder);
            } else {
                //pega o ViewHolder para ter um acesso rápido aos objetos do XML
                //ele sempre passará por aqui quando,por exemplo, for efetuado uma rolagem na tela 
                holder = (ViewHolder) convertView.getTag();
            }
 
            holder.tvDescricao.setText(receita.getDescricao());
            holder.tvReceita.setText(receita.getReceita().toString());
            holder.tvData.setText(receita.getData());
 
            return convertView;            
             
        }catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
        return convertView;
    }
 
 
    public void toast (String msg)
    {
        Toast.makeText (context, msg, Toast.LENGTH_SHORT).show ();
    } 
     
    private void trace (String msg) 
    {
        toast (msg);
    } 
     
    //Criada esta classe estática para guardar a referência dos objetos abaixo
    static class ViewHolder {
        public TextView tvDescricao;
        public TextView tvReceita;
        public TextView tvData;
    }    
}