package co.com.registropersonamovil2021.model;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Persona implements Serializable {
    private Integer idPersona;
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private String nombre;
    private String apellido;
    private boolean activo;
}
