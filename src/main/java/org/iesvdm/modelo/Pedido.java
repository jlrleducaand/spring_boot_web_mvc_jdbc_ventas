package org.iesvdm.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    private long id;
    private Double total;
    private Date fecha;
    private int id_cliente;
    private int id_comercial;

}
