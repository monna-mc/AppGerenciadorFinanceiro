package trabalho.finaly.gerenciadordedespesas;

import java.util.Calendar;

import trabalho.finaly.gerenciadordedespesas.model.Despesa;
import trabalho.finaly.gerenciadordedespesas.util.GerenciadorBD;
import trabalho.finaly.gerenciadordedespesas.util.GerenciadorBD.DespesasCursor;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Add_Despesa extends Activity {

	private TextView mDateDisplay;
	private Button mPickDate;
	private int mYear;
	private int mMonth;
	private int mDay;

	static final int DATE_DIALOG_ID = 0;

	Button btnSalvar, btnCancelar, btnResetar, btnNovo;

	Cursor listaDespesas;
	Despesa atual = null;

	EditText descricao = null;
	EditText valor = null;
	EditText data = null;
	RadioGroup status = null;
	GerenciadorBD gerenciador;

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CarregarListagem();
	}

	public void CarregarListagem() {
		setContentView(R.layout.lista_despesa);

		btnNovo = (Button) findViewById(R.id.btnNovo);
		btnNovo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CarregarCadastro();
			}
		});

		CarregarLista(this);
	}

	public void CarregarCadastro() {
		setContentView(R.layout.add__despesa);

		btnCancelar = (Button) findViewById(R.id.cancelarButton);
		btnCancelar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CarregarListagem();
			}
		});

		mDateDisplay = (TextView) findViewById(R.id.date);
		mPickDate = (Button) findViewById(R.id.date_picker_button);

		mPickDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		updateDisplay();

		/*
		 * 
		 * descricao = (EditText)findViewById(R.id.editDescricao); valor =
		 * (EditText)findViewById(R.id.editValor); status = (RadioGroup)
		 * findViewById(R.id.statusGroup); data = (EditText)
		 * findViewById(R.id.date);
		 * 
		 * btnSalvar = (Button)findViewById(R.id.SalvarButton);
		 * btnSalvar.setOnClickListener(new OnClickListener(){ public void
		 * onClick(View v) { SalvarCadastro(); }});
		 */
	}

	public void SalvarCadastro() {

		String mDstatus = null;

		switch (status.getCheckedRadioButtonId()) {
		case R.id.statusPaga:
			mDstatus = "paga";
			break;
		case R.id.statusNaoPaga:
			mDstatus = "Não paga";
			break;
		}

		GerenciadorBD db = new GerenciadorBD(this);

		Double mDvalor = Double.parseDouble(valor.getText().toString());

		db.InserirDespesa(descricao.getText().toString(), mDvalor, data.getText().toString(), mDstatus);
		setContentView(R.layout.lista_despesa);
		CarregarLista(this);

	}

	public void CarregarLista(Context c) {
		GerenciadorBD db = new GerenciadorBD(c);
		DespesasCursor cursor = db.RetornarDespesa(DespesasCursor.OrdenarPor.NomeCrescente);

		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			ImprimirLinha(cursor.getDescricao(), cursor.getValor(),cursor.getStatus(), cursor.getData());
		}
	}

	public void ImprimirLinha(String descricao, String valor, String data,String status) {
		TextView tv = (TextView) findViewById(R.id.listaDeDespesas);

		if (tv.getText().toString().equalsIgnoreCase("Nada cadastrado."))
			tv.setText("");

		tv.setText(tv.getText() + "\r\n" + "Descricao: " + descricao + "\n "
				+ "Valor: " + valor + "\n" + "Status: " + status + "\n"
				+ "Data: " + data);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		gerenciador.close();
	}

	private void updateDisplay() {
		mDateDisplay.setText(new StringBuilder().append(mMonth + 1).append("-")
				.append(mDay).append("-").append(mYear).append(" "));
	}

	@Override
	public Dialog onCreateDialog(int id) {

		return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);

	}

}
