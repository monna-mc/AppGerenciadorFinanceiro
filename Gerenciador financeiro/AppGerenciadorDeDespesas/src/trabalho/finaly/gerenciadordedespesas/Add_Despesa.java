package trabalho.finaly.gerenciadordedespesas;

import trabalho.finaly.gerenciadordedespesas.model.Despesa;
import trabalho.finaly.gerenciadordedespesas.util.GerenciadorBD;
import android.os.Bundle;
import android.app.TabActivity;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.TabSpec;

public class Add_Despesa extends TabActivity {

	Cursor listaDespesas;
	Despesa atual = null;

	EditText mDdescricao = null;
	EditText mDvalor = null;
	EditText mDdata = null;
	RadioGroup mDstatus = null;
	GerenciadorBD gerenciador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add__despesa);
		gerenciador = new GerenciadorBD(this);

		mDdescricao = (EditText) findViewById(R.id.editDescricao);
		mDvalor = (EditText) findViewById(R.id.editValor);
		mDdata = (EditText) findViewById(R.id.date);
		mDstatus = (RadioGroup) findViewById(R.id.statusGroup);

		Button salvar = (Button) findViewById(R.id.SalvarButton);
		salvar.setOnClickListener(onSave);

		ListView lista = (ListView) findViewById(R.id.despesas);

		listaDespesas = gerenciador.obterDespesas();
		startManagingCursor(listaDespesas);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		gerenciador.close();
	}

	private OnClickListener onSave = new OnClickListener() {

		public void onClick(View arg0) {
			String status = null;

			switch (mDstatus.getCheckedRadioButtonId()){
			case R.id.statusPaga:
				status = "paga";
				break;
			case R.id.statusNaoPaga:
				status = "Não paga";
				break;
			}

			Double valor = Double.parseDouble(mDvalor.getText().toString());
			gerenciador.inserirDespesa(mDdescricao.getText().toString(),valor ,mDdata.getText().toString(), status);
			listaDespesas.requery();
		}
	};

}
