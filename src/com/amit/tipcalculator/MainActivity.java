package com.amit.tipcalculator;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private EditText etAmount;
	private TextView tvTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAmount = (EditText) findViewById(R.id.etAmount);
        tvTip = (TextView) findViewById(R.id.tvTip);
    }
    
    public void onSubmitFixed(View v) {
    	if (etAmount == null) {
    		Toast.makeText(this, "etAmount in null baby", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	Log.d("debugging ", etAmount.getText().toString());
    	Double amount = getAmountInDouble(etAmount.getText().toString());
    	if (amount == null){
    		return;
    	}
    	
    	Button b = (Button) v;
    	String buttonText = b.getText().toString();
    	double tipPercent = 0;
    	if (buttonText.equals(this.getString(R.string.tenP))) {
    		tipPercent = 10.0;
    	} else if (buttonText.equals(this.getString(R.string.fifteenP))) {
    		tipPercent = 15.0;
    	} else if (buttonText.equals(this.getString(R.string.twentyP))) {
    		tipPercent = 20.0;
    	} else {
    		throw new RuntimeException ();
    	}
    	
    	double tip = (amount * tipPercent) / 100;
    	String tipString = String.format("%.2f", tip);
    	tvTip.setText(tipString);
 
    }


    private Double getAmountInDouble(String string) {
    	Double amount = null;
    	try {
    		amount = Double.parseDouble(string);	
    	} catch (NumberFormatException e) {
    		Toast.makeText(this, "Incorrect input in amount", Toast.LENGTH_SHORT).show();
    	}
    	 
		return amount;
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    
}
