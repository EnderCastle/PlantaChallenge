package com.example.planta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class ActivityAjustes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        Spinner spinner = (Spinner) this.findViewById(R.id.spinner);

        ArrayList<String> spinList = new ArrayList<>();

        spinList.add("Realistic");
        spinList.add("Standar");

        spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, spinList));

    }

    public void volver (View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        boolean humedadBoolean;
        boolean nombreBoolean;
        String velocidadCrecimiento = "";

        ToggleButton humedad = (ToggleButton) this.findViewById(R.id.humedadToggle);
        ToggleButton nombre = (ToggleButton) this.findViewById(R.id.cientificoToggle);

        RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.radioGroup);

        Spinner spinner = (Spinner) this.findViewById(R.id.spinner);

        int radioButtonId = radioGroup.getCheckedRadioButtonId();

        // valor del toggle humedad
        if (humedad.isChecked()) {
            humedadBoolean = true;
        }
        else {
            humedadBoolean = false;
        }
        intent.putExtra("humedad",humedadBoolean);

        // valor del toggle cientifico
        if (nombre.isChecked()) {
            nombreBoolean = true;
        }
        else {
            nombreBoolean = false;
        }
        intent.putExtra("nombre",nombreBoolean);

        // valor radioGroupId
        if (radioButtonId != -1) { // compruebo que algo esta marcado
            RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonId);
            velocidadCrecimiento = radioButton.getText().toString();
        }
        intent.putExtra("velocidad",velocidadCrecimiento);

        // valor del spinner
        String opcion = spinner.getSelectedItem().toString();
        intent.putExtra("dataDisplayed",opcion);


        setResult(RESULT_OK,intent);
        finish();
    }
}