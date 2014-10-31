package trabalho.finaly.gerenciadordedespesas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android .view.View.OnClickListener ;


public class MainActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button button = (Button) findViewById (R.id.botaoEntrar);
			
		button.setOnClickListener(new OnClickListener(){
			
			public void onClick(View view) {
				Intent intent = new Intent (MainActivity.this , Add_Despesa.class);
					startActivity ( intent );
			}
		});
	
	

	}
}
