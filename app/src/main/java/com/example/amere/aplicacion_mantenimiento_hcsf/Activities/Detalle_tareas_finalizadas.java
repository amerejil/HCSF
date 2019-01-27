package com.example.amere.aplicacion_mantenimiento_hcsf.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.amere.aplicacion_mantenimiento_hcsf.R;
import com.example.amere.aplicacion_mantenimiento_hcsf.Utils;
import com.example.amere.aplicacion_mantenimiento_hcsf.data_task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Detalle_tareas_finalizadas extends AppCompatActivity {
    private TextView textViewTipoTarea;
    private TextView textViewTipo;
    private TextView textViewUbicacion;
    private TextView textViewPiso;
    private TextView textViewArea;
    private TextView textViewSubarea;
    private TextView textViewSolicitante;
    private TextView textViewTrabajoSolicitado;
    private TextView textViewNota;
    private CardView cardViewEstadoEquipo;
    private TextView textViewEstadoEquipo;
    private FirebaseDatabase database_hcsf;
    private data_task dataTask;
    private DatabaseReference task;
    private ActionBar ab;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_tareas_finalizadas);
        toolbar = findViewById(R.id.my_toolbar_detalle_tareas_finalizadas);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);
        textViewTipoTarea = findViewById(R.id.textViewWorkFinished);
        textViewTipo = findViewById(R.id.textViewDetalleTipoFinalizdo);
        textViewUbicacion = findViewById(R.id.textViewDetalleUbicacionFinalizado);
        textViewPiso = findViewById(R.id.textViewDetallePisoFinalizado);
        textViewArea = findViewById(R.id.textViewDetalleAreaFinalizado);
        textViewSubarea = findViewById(R.id.textViewDetalleSubareaFinalizado);
        textViewSolicitante = findViewById(R.id.textViewDetalleSolicitanteFinalizado);
        textViewTrabajoSolicitado = findViewById(R.id.textViewDetalleTrabajoSolicitado);
        textViewNota = findViewById(R.id.texViewNotaFinalizado);
        textViewEstadoEquipo = findViewById(R.id.textViewEstadoEquipo);
        cardViewEstadoEquipo = findViewById(R.id.cardViewEstadoEquipo);

        database_hcsf = Utils.getDatabase();
        String tipo = getIntent().getExtras().get("trabajos").toString();
        dataTask = (data_task) getIntent().getSerializableExtra("data");
        if (tipo.equals("diarios")) {
            //task = database_hcsf.getReference("Tareas_prueba");//cambio
            task = database_hcsf.getReference("Tareas");
        } else {
            //task = database_hcsf.getReference("Tareas_Mensuales_prueba");//cambio
            task = database_hcsf.getReference("Tareas_Mensuales");
            textViewTipoTarea.setText(R.string.submenu_monthly_work_finished);
        }
        if (dataTask != null) {
            textViewTipo.setText(dataTask.getTipo());
            textViewUbicacion.setText(dataTask.getUbicacion());
            textViewPiso.setText(dataTask.getPiso());
            textViewArea.setText(dataTask.getArea());
            textViewSubarea.setText(dataTask.getSubarea());
            textViewSolicitante.setText(dataTask.getSolicitante());
            textViewTrabajoSolicitado.setText("Trabajo solicitado: " + dataTask.getTrabajo_solicitado());
            textViewNota.setText("Nota: " + dataTask.getNota());
            textViewEstadoEquipo.setText(dataTask.getEstado_equipo());
            if (dataTask.getEstado_equipo().equals("Operativo")) {
                cardViewEstadoEquipo.setCardBackgroundColor(getResources().getColor(R.color.prioridad_bajo));
            }
            if (dataTask.getEstado_equipo().equals("De baja")) {
                cardViewEstadoEquipo.setCardBackgroundColor(getResources().getColor(R.color.prioridad_alta));
            }

        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }

    }
}
