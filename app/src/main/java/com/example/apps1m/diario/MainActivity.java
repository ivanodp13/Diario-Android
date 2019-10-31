package com.example.apps1m.diario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    ArrayList<String> entradas;
    ArrayAdapter adapter;
    ListView lista;

    int posLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = findViewById(R.id.lista);

        entradas = new ArrayList<>();

        sharedPreferences = getSharedPreferences("Datos guardados", MODE_PRIVATE);

        ArrayList<String> DatosGuardados = recuperar();
        entradas.addAll(DatosGuardados);

        almacenar(entradas);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, entradas);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posLista = position;
                Log.d("posListado", posLista+"");

                editartxt();

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Añade una entrada nueva
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String Entrada = data.getStringExtra("texto");
                Log.d("contenido", Entrada+"");
                entradas.add(Entrada);
                adapter.notifyDataSetChanged();
                almacenar(entradas);
            }
        }
        //Sobrescribe el contenido de una entrada ya creada previamente
        if (requestCode == 2) {
            if(resultCode == RESULT_OK){
                String Entrada = data.getStringExtra("texto");
                entradas.set(posLista, Entrada);
                adapter.notifyDataSetChanged();
                almacenar(entradas);
            }
        }
    }

    /**
     * Cuando se pulsa el boton añadir se abre el editor de texto
     * @param view
     */
    public void botonanadir(View view) {
        Intent intent = new Intent(this, EditorTexto.class);
        startActivityForResult(intent, 1);
    }

    /**
     * Cuando se pulsa en una de las entradas se abre el editor de texto y carga la entrada que corresponda
     */
    public void editartxt() {
        String texto = entradas.get(posLista);

        Intent intent = new Intent(this, EditorTexto.class);
        intent.putExtra("texto",texto);
        startActivityForResult(intent, 2);
    }

    /**
     * Almacena los datos
     * @param textos
     */
    private void almacenar(ArrayList<String> textos) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < textos.size(); i++) {
            editor.putString("texto" + i, textos.get(i));
        }
        editor.putInt("cantidad", textos.size());
        editor.commit();
    }

    /**
     * Recupera los datos almacenados previamente
     * @return
     */
    private ArrayList<String> recuperar() {
        ArrayList<String> textos = new ArrayList<>();
        // Recuperar textos
        int cantidad = sharedPreferences.getInt("cantidad", 0);
        for (int i = 0; i < cantidad; i++) {
            String texto = sharedPreferences.getString("texto" + i, "");
            textos.add(texto);
        }
        return textos;
    }


}
