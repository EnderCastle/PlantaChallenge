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

    private boolean humedadBoolean;
    private boolean temperaturaBoolean;
    private boolean nombreBoolean;
    private String velocidadCrecimiento = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        String velocidad1 = getString(R.string.growSp1);
        String velocidad2 = getString(R.string.growSp2);
        String velocidad3 = getString(R.string.growSp3);

        String stat1 = getString(R.string.stat1);
        String stat2 = getString(R.string.stat2);

        // pillamos los valores del main
        humedadBoolean = getIntent().getBooleanExtra("humedad", false);
        temperaturaBoolean = getIntent().getBooleanExtra("temperatura", false);
        nombreBoolean = getIntent().getBooleanExtra("cientifico", false);
        velocidadCrecimiento = getIntent().getStringExtra("velocidad");
        String spin = getIntent().getStringExtra("data");

        // definimos los layout
        ToggleButton humedad = (ToggleButton) this.findViewById(R.id.humedadToggle);
        ToggleButton temperatura = (ToggleButton) this.findViewById(R.id.temperaturaToggle);
        ToggleButton nombre = (ToggleButton) this.findViewById(R.id.cientificoToggle);

        RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.radioGroup);

        Spinner spinner = (Spinner) this.findViewById(R.id.spinner);

        ArrayList<String> spinList = new ArrayList<>();

        spinList.add(stat1);
        spinList.add(stat2);

        spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, spinList));

        // damos valor a los layout

        // booleans
        if (humedadBoolean) {
            humedad.setChecked(true);
        }
        if (temperaturaBoolean) {
            temperatura.setChecked(true);
        }
        if (nombreBoolean) {
            nombre.setChecked(true);
        }


        // radio group
        RadioButton opcionVelocidad;

        if (velocidadCrecimiento.equals(velocidad1)) {
            opcionVelocidad = (RadioButton) radioGroup.getTouchables().get(0);
            opcionVelocidad.setChecked(true);
        }
        else if (velocidadCrecimiento.equals(velocidad2)) {
            opcionVelocidad = (RadioButton) radioGroup.getTouchables().get(1);
            opcionVelocidad.setChecked(true);
        }
        else if (velocidadCrecimiento.equals(velocidad3)) {
            opcionVelocidad = (RadioButton) radioGroup.getTouchables().get(2);
            opcionVelocidad.setChecked(true);
        }

        // spinner
        if (spin.equals(stat1)) {
            spinner.setSelection(0);
        }
        else if (spin.equals(stat2)) {
            spinner.setSelection(1);
        }
    }

    public void volver (View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        ToggleButton humedad = (ToggleButton) this.findViewById(R.id.humedadToggle);
        ToggleButton temperatura = (ToggleButton) this.findViewById(R.id.temperaturaToggle);
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

        // valor del toggle temperatura
        if (temperatura.isChecked()) {
            temperaturaBoolean = true;
        }
        else {
            temperaturaBoolean = false;
        }
        intent.putExtra("temperatura",temperaturaBoolean);

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

        // devolvemos datos al main y cerramos la clase ajustes
        setResult(RESULT_OK,intent);
        finish();
    }
}