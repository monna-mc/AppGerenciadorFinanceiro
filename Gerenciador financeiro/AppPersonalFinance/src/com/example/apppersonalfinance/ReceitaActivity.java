package com.example.apppersonalfinance;

import java.util.Calendar;

import com.example.modelo.Receita;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReceitaActivity extends Activity {

	private static final int INCLUIR = 0;

	Receita lReceita;
	EditText txtDescricao;
	EditText txtReceita;

	private TextView mDateDisplay;
	private Button mPickDate;
	private int mYear;
	private int mMonth;
	private int mDay;

	static final int DATE_DIALOG_ID = 0;

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
		setContentView(R.layout.activity_receita);
		
		try {

			final Bundle data = (Bundle) getIntent().getExtras();
			int lint = data.getInt("tipo");
			if (lint == INCLUIR) {
				// quando for incluir uma receita criamos uma nova instância
				lReceita = new Receita();
			} else {
				// quando for alterar o receita carregamos a classe que veio por
				// Bundle
				lReceita = (Receita) data.getSerializable("entrada");
			}

			// Criação dos objetos da Activity
			txtDescricao = (EditText) findViewById(R.id.edtRecDescricao);
			txtReceita = (EditText) findViewById(R.id.edtRecValor);
			mDateDisplay = (TextView) findViewById(R.id.dateRec);
			mPickDate = (Button) findViewById(R.id.date_receita);

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

			// Carregando os objetos com os dados
			// caso seja uma inclusão ele virá carregado com os atributos text
			// definido no arquivo main.xml
			txtDescricao.setText(lReceita.getDescricao());
			txtReceita.setText(lReceita.getReceita().toString());
			mDateDisplay.setText(lReceita.getData());

		} catch (Exception e) {
			trace("Erro : " + e.getMessage());
		}
	}

	public void btnConfirmar_click(View view) {
		try {
			// Quando confirmar a inclusão ou alteração deve-se devolver
			// o registro com os dados preenchidos na tela e informar
			// o RESULT_OK e em seguida finalizar a Activity

			Double mDvalor = Double
					.parseDouble(txtReceita.getText().toString());

			Intent data = new Intent();
			lReceita.setDescricao(txtDescricao.getText().toString());
			lReceita.setReceita(mDvalor);
			lReceita.setData(mDateDisplay.getText().toString());
			data.putExtra("entrada", lReceita);
			setResult(Activity.RESULT_OK, data);
			finish();
		} catch (Exception e) {
			trace("Erro : " + e.getMessage());
		}
	}

	public void btnCancelar_click(View view) {
		try {
			// Quando for simplesmente cancelar a operação de inclusão
			// ou alteração deve-se apenas informar o RESULT_CANCELED
			// e em seguida finalizar a Activity

			setResult(Activity.RESULT_CANCELED);
			finish();
		} catch (Exception e) {
			trace("Erro : " + e.getMessage());
		}
	}

	public void toast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

	private void trace(String msg) {
		toast(msg);
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
