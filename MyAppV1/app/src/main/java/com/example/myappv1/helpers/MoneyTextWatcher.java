package com.example.myappv1.helpers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MoneyTextWatcher implements TextWatcher {
    EditText editTex;
    public MoneyTextWatcher(EditText text){
        editTex = text;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        editTex.removeTextChangedListener(this);

        try {
            String originalString = s.toString();

            Long longval;
            if (originalString.contains(",")) {
                originalString = originalString.replaceAll(",", "");
            }
            longval = Long.parseLong(originalString);

            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            formatter.applyPattern("#,###,###,###");
            String formattedString = formatter.format(longval);

            //setting text after format to EditText
            editTex.setText(formattedString);
            editTex.setSelection(editTex.getText().length());
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

        editTex.addTextChangedListener(this);
    }}


