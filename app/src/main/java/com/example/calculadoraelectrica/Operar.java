package com.example.calculadoraelectrica;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Operar {

    public static double getDoubleData(EditText dv) {
        return Double.parseDouble(dv.getText().toString());
    }

    public static Double operar(EditText Volt, EditText dvolt, EditText Inten, EditText Long,
                         EditText FactorP, RadioButton cuWire, RadioButton alWire,
                         RadioButton trif, RadioButton mono) {

        double conduct = 0;
        double result = 0;

        double V = getDoubleData(Volt);
        double dv = getDoubleData(dvolt);
        double I = getDoubleData(Inten);
        double L = getDoubleData(Long);
        double FP = getDoubleData(FactorP);

        if (cuWire.isChecked()) {
            conduct = 48.47;
        }
        if (alWire.isChecked()) {
            conduct = 29.67;
        }
        if (conduct != 0) {
            if (trif.isChecked()) {
                result = ((Math.sqrt(3) * (I * L * FP)) / (((V * dv) / 100) * conduct));
                return result;
            }
            if (mono.isChecked()) {
                result = ((2 * (I * L * FP)) / (((V * dv) / 100) * conduct));
                return result;
            }
        }
        return result + 404;
    }
}
