package org.iesvdm.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iesvdm.modelo.Pedido;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
// no usamos herencia con lombok no se integra bien con las anotaciones de generación automática
// del constructor
public class ComercialDTO {

    private int id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private float comision;

    //Campos extras para las funcionalidades
    private Double total;
    private Double media;

}
