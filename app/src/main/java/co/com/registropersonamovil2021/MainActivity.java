package co.com.registropersonamovil2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.com.registropersonamovil2021.adapter.PersonaAdapter;
import co.com.registropersonamovil2021.model.Persona;

import co.com.registropersonamovil2021.service.persona.PersonaServiceImpl;
import co.com.registropersonamovil2021.util.ActionBarUtil;

public class MainActivity extends AppCompatActivity {

    private List<Persona> listaPersonas;
    @BindView(R.id.listViewPersonas)
    ListView listViewPersonas;


    private PersonaAdapter personaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ActionBarUtil.getInstance(this, false).setToolBar(getString(R.string.listado_persona));
        listaPersonas=new ArrayList<>();
        cargarInformation();
        seleccionarItem();


    }
    private void seleccionarItem(){
        listViewPersonas.setClickable(true);
        listViewPersonas.setOnItemClickListener((parent, view, position, id) -> {
            Persona personaSeleccionada = (Persona) listViewPersonas.getItemAtPosition(position);
            goToEditarPersona(view, personaSeleccionada.getIdPersona(),personaSeleccionada.getTipoDocumento().getIdTipoDocumento() , personaSeleccionada.getNumeroDocumento(),
                    personaSeleccionada.getNombre(),personaSeleccionada.getApellido());
        });

    }
    private void cargarInformation() {
        PersonaServiceImpl personaService = new PersonaServiceImpl(this);
        personaService.getPersona(listViewPersonas);


    }


    public void goToEditarPersona(View view, Integer idPersona, int idTipoDocumento, String documento, String nombre, String apellido) {
        Intent intent = new Intent(this,EditarPersonaActivity.class);
        intent.putExtra("id_persona", idPersona);
        intent.putExtra("documento_persona", documento);
        intent.putExtra("id_documento", idTipoDocumento);
        intent.putExtra("nombre_persona", nombre);
        intent.putExtra("apellido_persona", apellido);
        startActivity(intent);
    }

    public void goToRegistroPersona(View view) {
        Intent intent = new Intent(this,RegistroPersonaActivity.class);
        startActivity(intent);
    }
    @Override
    public void onResume(){
        super.onResume();
        cargarInformation();

    }


}