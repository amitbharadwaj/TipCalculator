package com.amit.tipcalculator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TipActivity extends Activity {

	protected static final int MSG_KEYBOARD_HIDE = 10;

	private EditText etAmount;
	
	private RadioGroup rgTip;
	private EditText etOtherTip;
	
	private TextView tvTip;
	private TextView tvTipTotal;

	private Double amount = 0.0;
	private Double tip = 0.0;
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == MSG_KEYBOARD_HIDE ) {
				imm.hideSoftInputFromWindow(etAmount.getWindowToken(), 0);						
			}
		};
	};

	InputMethodManager imm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip);

		etAmount = (EditText) findViewById(R.id.etAmount);
		rgTip = (RadioGroup) findViewById(R.id.rgTipPercent);
		tvTip = (TextView) findViewById(R.id.tvTip);
		tvTipTotal = (TextView) findViewById(R.id.tvTotal);
		etOtherTip = (EditText) findViewById(R.id.etOtherTip);

		imm = (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE);

		updateTipAmount();
		setUpListeners();
	}



	private void setUpListeners() {
		etAmount.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				updateTipAmount();
				
				mHandler.removeMessages(MSG_KEYBOARD_HIDE);
				mHandler.sendEmptyMessageDelayed(MSG_KEYBOARD_HIDE, 1000);
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
				
				mHandler.removeMessages(MSG_KEYBOARD_HIDE);
				mHandler.sendEmptyMessageDelayed(MSG_KEYBOARD_HIDE, 1000);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {				
			}
		});
		
		etOtherTip.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					if (rgTip.getCheckedRadioButtonId() != R.id.rbOther) {
						RadioButton rbOther = (RadioButton) findViewById(R.id.rbOther);
						rbOther.setChecked(true);
					}
				}
				
			}
		});

	}


	public void onTipSubmit(View v) {
		int checkedRadio = rgTip.getCheckedRadioButtonId();
		tip = getTipFromCheckedRadio(checkedRadio);
		updateTipAmount();
		
		if (etOtherTip.hasFocus() && checkedRadio != R.id.rbOther) {
			etOtherTip.clearFocus();
			imm.hideSoftInputFromWindow(etAmount.getWindowToken(), 0);
		}
	}


	private void updateTipAmount() {
		String amountStr = etAmount.getText().toString();
		amount = getAmountInDouble(amountStr, "amount");

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
			if (otherTipString.equals(getString(R.string.textEmpty))) {
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


	private Double getAmountInDouble(String amountStr, String field) {
		Double amount = 0.0;

		if (amountStr == null || amountStr.isEmpty()) {
			return amount;
		}

		if (amountStr.startsWith("$")) {
			amountStr = amountStr.substring(1);
		}
		
		try {
			amount = Double.parseDouble(amountStr);	
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
