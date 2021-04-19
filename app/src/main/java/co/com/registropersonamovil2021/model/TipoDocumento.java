package co.com.registropersonamovil2021.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoDocumento {
    private Integer idTipoDocumento;
    private String nombreDocumento;
}
