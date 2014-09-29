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

		//final View controlsView = findViewById(R.id.fullscreen_content_controls);
		//final View contentView = findViewById(R.id.fullscreen_content);

			
		button.setOnClickListener(new OnClickListener(){
			
			public void onClick(View view) {
				Intent intent = new Intent (MainActivity.this , Add_Despesa.class);
					startActivity ( intent );
			}
		});
	
	

	}
}
