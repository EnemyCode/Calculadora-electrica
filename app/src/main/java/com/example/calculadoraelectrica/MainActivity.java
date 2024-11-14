package com.example.calculadoraelectrica;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.color.DynamicColors;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView total;
    EditText voltage;
    EditText downVolt;
    EditText amper;
    EditText longer;
    EditText factor;
    RadioButton cuWire;
    RadioButton alWire;
    RadioButton trif;
    RadioButton mono;
    ListView bdview;

    DecimalFormat df = new DecimalFormat("#.00");
    private ArrayList<String> toListView = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private Conexion dataBaseConnection;
    private SQLiteDatabase db;
    private long insertion;

    String BBDD_NAME = "CalcDataBase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DynamicColors.applyToActivitiesIfAvailable(this.getApplication());
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        voltage = findViewById(R.id.Tension);
        downVolt = findViewById(R.id.DownVolt);
        amper = findViewById(R.id.Amper);
        longer = findViewById(R.id.Longer);
        factor = findViewById(R.id.Factor);
        total = findViewById(R.id.total);
        cuWire = findViewById(R.id.copper);
        alWire = findViewById(R.id.aluminum);
        trif = findViewById(R.id.Trif);
        mono = findViewById(R.id.Mono);
        bdview = findViewById(R.id.bdview);

        this.deleteDatabase(BBDD_NAME);
        dataBaseConnection = new Conexion(this, BBDD_NAME, null, 1);
    }

    //BOTONES Y SEÑALES
    @SuppressLint("SetTextI18n")
    public void calcularButton(View v){
        try {
            total.setText("Resultado: "+df.format(Operar.operar(voltage, downVolt, amper, longer, factor,cuWire,alWire,trif,mono))+"mm²");
        }
        catch (Exception e){
            Toast.makeText(MainActivity.this, "Error en datos introducidos, por favor, reviselo", Toast.LENGTH_LONG).show();
        }
    }

    public void guardarButton(View v){

            bdview.setAdapter(null);
            toListView.clear();

            DBopen();
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put("Voltage", getDoubleData(voltage));
            nuevoRegistro.put("CaidaVoltage", getDoubleData(downVolt));
            nuevoRegistro.put("Longitud", getDoubleData(longer));
            nuevoRegistro.put("Intensidad", getDoubleData(amper));
            nuevoRegistro.put("FactorPotencia", getDoubleData(factor));
            //ME FALTA AÑADIR EL RESULTADO

            insertion = db.insert("Calculos", null, nuevoRegistro);

            Cursor c = db.rawQuery("SELECT * FROM " + "Calculos",null);

            while (c.moveToNext()){
                @SuppressLint("DefaultLocale") String query = String.format("%d %s %s %s %s",c.getInt(0),df.format(c.getDouble(1)),df.format(c.getDouble(2)),df.format(c.getDouble(3)),df.format(c.getDouble(4)));
                toListView.add(query);
            }
            c.close();
            DBclose();

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,toListView);
            bdview.setAdapter(adapter);

            if(insertion == -1){
                Toast.makeText(MainActivity.this, "Datos no guardados", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(MainActivity.this, "Datos introducidos", Toast.LENGTH_LONG).show();
            }
        }

    public void clearBD(View v){
        DBclose();

        DBclose();
    }

    //METODOS PARA HANDLING Y GESTION DE BBDD

    public void DBopen(){
        db = dataBaseConnection.getWritableDatabase();
    }

    public void DBclose(){
        dataBaseConnection.close();
    }
    //METODOS DE SETTEO DE DATOS

    public double getDoubleData(EditText dv){
        return Double.parseDouble(dv.getText().toString());
    }



}