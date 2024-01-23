package org.iesvdm.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//Para generar un constructor con lombok con todos los args
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    private long id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String ciudad;
    private int categoria;


}
