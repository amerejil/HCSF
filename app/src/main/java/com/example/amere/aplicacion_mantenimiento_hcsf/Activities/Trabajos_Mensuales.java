package com.example.amere.aplicacion_mantenimiento_hcsf.Activities;

        import android.annotation.SuppressLint;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.pm.ActivityInfo;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.EditText;
        import android.widget.RelativeLayout;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.amere.aplicacion_mantenimiento_hcsf.Adapters.Adapter_for_task_list;
        import com.example.amere.aplicacion_mantenimiento_hcsf.OnSwipeTouchListener;
        import com.example.amere.aplicacion_mantenimiento_hcsf.R;
        import com.example.amere.aplicacion_mantenimiento_hcsf.Utils;
        import com.example.amere.aplicacion_mantenimiento_hcsf.administrador_notificaciones;
        import com.example.amere.aplicacion_mantenimiento_hcsf.data_task;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;

        import java.text.SimpleDateFormat;
        import java.util.ArrayList;

public class Trabajos_Mensuales extends AppCompatActivity {
    private FirebaseDatabase database_hcsf;
    private ArrayList<data_task> lista_tareas_diarias;
    private RecyclerView recyclerViewTMonthlyTask;
    private Adapter_for_task_list adaptador;
    private administrador_notificaciones administradorNotificaciones;
    private DatabaseReference task;
    private LinearLayoutManager linearLayoutManager_montly_task;
    private TextView textViewType;
    private TextView textViewDate;
    private TextView textViewState;
    private FloatingActionButton floatingActionButtonAddDailyTask;
    private RelativeLayout relativeLayout;
    private int click_text_type;
    private int click_text_date;
    private int click_text_state;
    private SharedPreferences preferences;

    ///agregue variables de agregar trabajos diarios
    //ojo si se comentan es porque arriba ya existen
    /*************************************************************************/
    private Spinner tipo;
    private String stringTipo;
    private String stringUbicacion;
    private String stringPiso;
    private String stringAtencion;
    private String stringArea;
    private String stringSolicitante;
    private String stringSubarea;
    private String stringTrabajoSolicitado;
    private String stringFecha_inicio_entero;
    private EditText area;
    private EditText subarea;
    private EditText solicitante;
    private EditText trabajo_solicitado;
    private Spinner ubicacion;
    private Spinner piso;
    private Spinner atencion;
    private FloatingActionButton enviarTarea;
   // private DatabaseReference task;
   // private FirebaseDatabase database_hcsf;
    private String stringFecha_inicio;
    private String stringEstado;
/*************************************************************************/

    @SuppressLint("ClickableViewAccessibility")


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajos__mensuales);
        preferences=getSharedPreferences("tipo",Context.MODE_PRIVATE);
        database_hcsf = Utils.getDatabase();
        task = database_hcsf.getReference("Tareas_Mensuales");
        lista_tareas_diarias=new ArrayList<>();
        recyclerViewTMonthlyTask =findViewById(R.id.recyclerViewMonthlyTask);
        linearLayoutManager_montly_task =new LinearLayoutManager(this);
        linearLayoutManager_montly_task.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewTMonthlyTask.setLayoutManager(linearLayoutManager_montly_task);
        textViewType=findViewById(R.id.textViewTypeMonthlyTask);
        textViewDate=findViewById(R.id.textViewDateMonthlyTask);
        textViewState=findViewById(R.id.textViewStateMonthlyTask);
        relativeLayout=findViewById(R.id.relativeLayoutTrabajosMensuales);
        floatingActionButtonAddDailyTask=findViewById(R.id.floatingActionButton_Add_MonthlyTask);
        administradorNotificaciones=new administrador_notificaciones(this);
        click_text_type=0;
        click_text_state=0;
        click_text_date=0;
        final Query orden_estado =task.orderByChild("estado");
        final Query orden_tipo=task.orderByChild("tipo");
        final Query orden_fecha=task.orderByChild("fecha_inicio_entero");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //se agrego funcionalidades y declaraciones provenientes de agregar trabajos diarios
        //ojo si se comentan es porque arriba ya existen o se reemplazan por el del layout correspondiente
/*************************************************************************/
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
       // super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setContentView(R.layout.activity_agregar_trabajos_diarios);  //se comento para reemplazarlo y definir abajo
        setContentView(R.layout.activity_trabajos__mensuales); //este layout por el de trabajos mensuales
        area=findViewById(R.id.editTextArea);
        subarea=findViewById(R.id.editTextSubrea);
        solicitante=findViewById(R.id.editTextSolicitante);
        trabajo_solicitado=findViewById(R.id.editText);
        tipo=findViewById(R.id.spinner1);
        ubicacion=findViewById(R.id.spinner2);
        piso=findViewById(R.id.spinner3);
        atencion=findViewById(R.id.spinner4);
        enviarTarea=findViewById(R.id.floatingActionButton1);
        database_hcsf= FirebaseDatabase.getInstance();
        //task=database_hcsf.getReference("Tareas");                //se comento el path de la base tareas porveniente de agregar trabajos diarios
        task=database_hcsf.getReference("Tareas_Mensuales"); //por uno llamado Tareas _Mensuales
        ArrayAdapter<CharSequence> adapterTipo=ArrayAdapter.createFromResource(this,R.array.tipo,R.layout.spinner_item);
        ArrayAdapter<CharSequence> adapterUbicacion=ArrayAdapter.createFromResource(this,R.array.ubicacion,R.layout.spinner_item);
        ArrayAdapter<CharSequence> adapterPiso=ArrayAdapter.createFromResource(this,R.array.piso,R.layout.spinner_item);
        ArrayAdapter<CharSequence> adapterAtemcion=ArrayAdapter.createFromResource(this,R.array.atencion,R.layout.spinner_item);
        ubicacion.setAdapter(adapterUbicacion);
        tipo.setAdapter(adapterTipo);
        piso.setAdapter(adapterPiso);
        atencion.setAdapter(adapterAtemcion);

/*************************************************************************/
        textViewType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_text_type++;
                textViewDate.setText(getString(R.string.date));
                textViewState.setText(getString(R.string.state));
                if(click_text_type==1)
                {
                    textViewType.setText("Tipo ↓");

                    orden_tipo.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            lista_tareas_diarias=new ArrayList<>();
                            for(DataSnapshot datasnapshot:dataSnapshot.getChildren())
                            {

                                if(!datasnapshot.getValue(data_task.class).getEstado().equals("Finalizado"))
                                    lista_tareas_diarias.add(datasnapshot.getValue(data_task.class));

                            }
                            adaptador =new Adapter_for_task_list(lista_tareas_diarias, new Adapter_for_task_list.OnItemClickListener() {
                                @Override
                                public void onItemClick(data_task data, int position) {
                                    Intent intent_detalle_tareas_mensuales=new Intent(Trabajos_Mensuales.this,Detalle_tareas_diarias.class);
                                    String id=data.getId();
                                    intent_detalle_tareas_mensuales.putExtra("Id_mensuales",id);
                                    startActivity(intent_detalle_tareas_mensuales);

                                }
                            },Trabajos_Mensuales.this,preferences);
                            recyclerViewTMonthlyTask.setAdapter(adaptador);
                            // This method is called once with the initial value and again

                            // whenever data at this location is updated.


                        }



                        @Override

                        public void onCancelled(DatabaseError error) {

                            // Failed to read value



                        }

                    });
                    click_text_type=0;

                }


            }
        });
        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_text_date++;
                textViewState.setText(getString(R.string.state));
                textViewType.setText(getString(R.string.type));
                if(click_text_date==1)
                {
                    textViewDate.setText("Fecha ↓");
                    orden_fecha.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            lista_tareas_diarias=new ArrayList<>();
                            for(DataSnapshot datasnapshot:dataSnapshot.getChildren())
                            {
                                if(!datasnapshot.getValue(data_task.class).getEstado().equals("Finalizado"))
                                    lista_tareas_diarias.add(datasnapshot.getValue(data_task.class));

                            }
                            adaptador =new Adapter_for_task_list(lista_tareas_diarias, new Adapter_for_task_list.OnItemClickListener() {
                                @Override
                                public void onItemClick(data_task data, int position) {
                                    Intent intent_detalle_tareas_mensuales=new Intent(Trabajos_Mensuales.this,Detalle_tareas_diarias.class);
                                    String id=data.getId();
                                    intent_detalle_tareas_mensuales.putExtra("Id_mensuales",id);
                                    startActivity(intent_detalle_tareas_mensuales);

                                }
                            },Trabajos_Mensuales.this,preferences);
                            recyclerViewTMonthlyTask.setAdapter(adaptador);
                            // This method is called once with the initial value and again

                            // whenever data at this location is updated.


                        }



                        @Override

                        public void onCancelled(DatabaseError error) {

                            // Failed to read value



                        }

                    });
                    click_text_date=0;
                }

            }
        });
        textViewState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_text_state++;
                textViewDate.setText(getString(R.string.date));
                textViewType.setText(getString(R.string.type));
                if(click_text_state==1)
                {
                    textViewState.setText("Estado ↓");
                    orden_estado.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            lista_tareas_diarias=new ArrayList<>();
                            for(DataSnapshot datasnapshot:dataSnapshot.getChildren())
                            {
                                if(!datasnapshot.getValue(data_task.class).getEstado().equals("Finalizado"))
                                    lista_tareas_diarias.add(datasnapshot.getValue(data_task.class));

                            }
                            adaptador =new Adapter_for_task_list(lista_tareas_diarias, new Adapter_for_task_list.OnItemClickListener() {
                                @Override
                                public void onItemClick(data_task data, int position) {
                                    Intent intent_detalle_tareas_mensuales=new Intent(Trabajos_Mensuales.this,Detalle_tareas_diarias.class);
                                    String id=data.getId();
                                    intent_detalle_tareas_mensuales.putExtra("Id_mensuales",id);
                                    startActivity(intent_detalle_tareas_mensuales);

                                }
                            },Trabajos_Mensuales.this,preferences);
                            recyclerViewTMonthlyTask.setAdapter(adaptador);
                            // This method is called once with the initial value and again

                            // whenever data at this location is updated.


                        }



                        @Override

                        public void onCancelled(DatabaseError error) {

                            // Failed to read value



                        }

                    });
                    click_text_state=0;
                }


            }
        });
        if(click_text_type==0 && click_text_date==0 && click_text_state==0)
        {
            task.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    lista_tareas_diarias=new ArrayList<>();
                    for(DataSnapshot datasnapshot:dataSnapshot.getChildren())
                    {
                        if(!datasnapshot.getValue(data_task.class).getEstado().equals("Finalizado"))
                            lista_tareas_diarias.add(datasnapshot.getValue(data_task.class));

                    }
                    adaptador =new Adapter_for_task_list(lista_tareas_diarias, new Adapter_for_task_list.OnItemClickListener() {
                        @Override
                        public void onItemClick(data_task data, int position) {
                            Intent intent_detalle_tareas_mensuales=new Intent(Trabajos_Mensuales.this,Detalle_tareas_diarias.class);
                            String id=data.getId();
                            intent_detalle_tareas_mensuales.putExtra("Id_mensuales",id);
                            startActivity(intent_detalle_tareas_mensuales);

                        }
                    },Trabajos_Mensuales.this,preferences);
                    recyclerViewTMonthlyTask.setAdapter(adaptador);
                    // This method is called once with the initial value and again

                    // whenever data at this location is updated.


                }



                @Override

                public void onCancelled(DatabaseError error) {

                    // Failed to read value



                }

            });
        }
        relativeLayout.setOnTouchListener(new OnSwipeTouchListener(Trabajos_Mensuales.this) {
            public void onSwipeRight() {
                Intent intent=new Intent(Trabajos_Mensuales.this,Menu_Principal.class);
                startActivity(intent);

            }
        });

        floatingActionButtonAddDailyTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* String value=task.push().getKey();

                task.child(value).setValue(new data_task(value,"H","o","l","a","c","a","r","a") );*/
                Intent intent_añadir_trabajo=new Intent(Trabajos_Mensuales.this,Agregar_trabajos_diarios.class);
                startActivity(intent_añadir_trabajo);

            }
        });
        //Se agrego la implementacion de las funcionalidades provenientes de Agregar_Trabajos_diarios
        //********************************************************************************/
        tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stringTipo= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ubicacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stringUbicacion=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        piso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stringPiso=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        atencion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stringAtencion=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        enviarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long date=System.currentTimeMillis();
                stringEstado="No iniciado";
                SimpleDateFormat f_date=new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat f_date_entero=new SimpleDateFormat("ddMMyyyy");
                stringFecha_inicio_entero =f_date_entero.format(date);
                stringFecha_inicio=f_date.format(date);
                stringSubarea=subarea.getText().toString();
                stringSolicitante=solicitante.getText().toString();
                stringTrabajoSolicitado=trabajo_solicitado.getText().toString();
                stringArea=area.getText().toString();
                String value=task.push().getKey();
                Toast.makeText(Trabajos_Mensuales.this,stringFecha_inicio,Toast.LENGTH_SHORT).show();
                task.child(value).setValue(new data_task(value,stringTipo,stringUbicacion,stringPiso,stringArea,
                        stringSubarea,stringAtencion,stringSolicitante,stringTrabajoSolicitado,stringFecha_inicio, stringFecha_inicio_entero,"","",stringEstado,"Desconocido","") );
            }
        });



        //*********************************************************************************/

    }

}
