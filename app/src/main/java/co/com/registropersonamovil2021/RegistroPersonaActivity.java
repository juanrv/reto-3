package co.com.registropersonamovil2021;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.com.registropersonamovil2021.dto.PersonaDTO;
import co.com.registropersonamovil2021.service.persona.PersonaServiceImpl;
import co.com.registropersonamovil2021.service.tipodocumento.TipoDocumentoServiceImpl;
import co.com.registropersonamovil2021.util.ActionBarUtil;

public class RegistroPersonaActivity extends AppCompatActivity {
    @BindView(R.id.txt_documento)
    EditText txtDocumento;

    @BindView(R.id.txt_nombre)
    EditText txtNombre;

    @BindView(R.id.txt_apellido)
    EditText txtApellido;

    @BindView(R.id.spinner_tipo_documento)
    Spinner spinnerDocumento;

    private Integer documentoSeleccionado;
    PersonaDTO persona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_persona);
        ButterKnife.bind(this);
        persona = new PersonaDTO();
        ActionBarUtil.getInstance(this, true).setToolBar(getString(R.string.registro_persona), getString(R.string.insertar));
        listarTipoDocumentos();
        itemSeleccionadoSpinner();
    }

    private void itemSeleccionadoSpinner(){
        spinnerDocumento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                documentoSeleccionado = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void listarTipoDocumentos(){
        TipoDocumentoServiceImpl tipoDocumentoService = new TipoDocumentoServiceImpl(this);
        tipoDocumentoService.getTipoDocumento(spinnerDocumento);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            if(validarCampos()){
                dialogoDeConfirmacionRegistroPersona();
                return true;
            }
            else {
                return false;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validarCampos (){
        if(spinnerDocumento.getSelectedItem().toString().equalsIgnoreCase("-- Seleccione --")) {
            Toast.makeText(this, R.string.tipo_documento_no_valido, Toast.LENGTH_LONG).show();
            return false;
        }        else if(txtDocumento.getText().toString().isEmpty() ||  txtDocumento.equals(null)){
            Toast.makeText(this, R.string.documeto_vacio, Toast.LENGTH_LONG).show();
            return false;
        }else if(txtNombre.getText().toString().isEmpty() || txtNombre.equals(null)){
            Toast.makeText(this, R.string.nombre_vacio, Toast.LENGTH_LONG).show();
            return false;
        }else if(txtApellido.getText().toString().isEmpty() || txtApellido.equals(null)){
            Toast.makeText(this, R.string.apellido_vacio, Toast.LENGTH_LONG).show();
            return false;
        }else{

            return true;
        }
    }

    private void dialogoDeConfirmacionRegistroPersona(){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistroPersonaActivity.this);
        builder.setCancelable(false);
        builder.setTitle(R.string.confirm);
        builder.setMessage(R.string.confirm_message_guardar_informacion);
        builder.setPositiveButton(R.string.confirm_action, (dialog, which) -> {
            insertarInformacion();
            goToMainActivity();
        });
        builder.setNegativeButton(R.string.cancelar, (dialog, which) ->  dialog.cancel() );
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void insertarInformacion() {
        PersonaServiceImpl personaService  = new PersonaServiceImpl(this);
        persona.setIdTipoDocumento(documentoSeleccionado);
        persona.setNumeroDocumento(txtDocumento.getText().toString());
        persona.setNombre(txtNombre.getText().toString());
        persona.setApellido(txtApellido.getText().toString());
        personaService.insertar(persona);
    }

    private void goToMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}