package com.example.apps1m.diario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;

public class EditorTexto extends AppCompatActivity {

    Button aceptar;
    Button cancelar;
    Adapter entrada;

    EditText TextoDiario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_texto);
        TextoDiario = findViewById(R.id.TextoDiario);

        Intent texto = getIntent();
        String textoU = texto.getStringExtra("texto");

        TextoDiario.setText(textoU);

    }

    /**
     * Vuelve al main activity y cierra el editor de texto
     * @param view
     */
    public void botoncancelar(View view) {
        Intent intentResultado = new Intent();
        intentResultado.putExtra("texto", 5);
        setResult(RESULT_CANCELED, intentResultado);
        finish();
    }

    /**
     * Guarda los datos escritos en el editor de texto, tanto para añadir una entrada como para sobrescribirla
     * @param view
     */
    public void botonguaradar(View view) {
        Intent intentResultado = new Intent();
        intentResultado.putExtra("texto", TextoDiario.getText().toString());
        setResult(RESULT_OK, intentResultado);
        finish();
    }


}
