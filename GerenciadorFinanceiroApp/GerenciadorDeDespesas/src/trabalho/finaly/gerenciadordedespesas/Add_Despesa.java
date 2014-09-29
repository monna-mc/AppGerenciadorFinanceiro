package trabalho.finaly.gerenciadordedespesas;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Add_Despesa extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add__despesa);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add__despesa, menu);
		return true;
	}

}
