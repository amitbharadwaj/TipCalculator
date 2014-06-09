package com.amit.tipcalculator;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TipActivity extends Activity {

	private EditText etAmount;
	
	private RadioGroup rgTip;
	private EditText etOtherTip;
	
	private TextView tvTip;
	private TextView tvTipTotal;

	private Double amount = 0.0;
	private Double tip = 0.0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip);

		etAmount = (EditText) findViewById(R.id.etAmount);
		rgTip = (RadioGroup) findViewById(R.id.rgTipPercent);
		tvTip = (TextView) findViewById(R.id.tvTip);
		tvTipTotal = (TextView) findViewById(R.id.tvTotal);
		etOtherTip = (EditText) findViewById(R.id.etOtherTip);

		updateTipAmount();  
		setUpListeners();
	}



	private void setUpListeners() {
		etAmount.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				updateTipAmount();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {				
			}
		});
		
		etOtherTip.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				updateTipAmount();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {				
			}
		});

	}


	public void onTipSubmit(View v) {
		updateTipAmount(); 
	}


	private void updateTipAmount() {
		amount = getAmountInDouble(etAmount.getText().toString(), "amount");        
		tip = getTipFromCheckedRadio(rgTip.getCheckedRadioButtonId());
		tvTip.setText(String.format("%.2f", tip));
		tvTipTotal.setText(String.format("%.2f", amount + tip));
	}

	private Double getTipFromCheckedRadio(int selectedRadio) {
		switch (selectedRadio) {
		case R.id.rbFifteenP:
			tip = (amount * 15.0) / 100;
			break;
		case R.id.rbTwentyP:
			tip = (amount * 20.0) / 100;
			break;
		case R.id.rbOther:
			String otherTipString = etOtherTip.getText().toString();
			if (otherTipString.equals(getString(R.string.textOther))) {
				etOtherTip.requestFocus();
				break;
			}
			tip = getAmountInDouble(otherTipString, "Other Tip");
			break;
		default:
			break;
		}
		return tip;
	}


	private Double getAmountInDouble(String string, String field) {
		Double amount = 0.0;
		if (string == null || string.isEmpty()) {
			return amount;
		}
		
		try {
			amount = Double.parseDouble(string);	
		} catch (NumberFormatException e) {
			Toast.makeText(this, "Incorrect input in " + field, Toast.LENGTH_SHORT).show();
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
