package org.iesvdm.domain;

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
    private Cliente cliente;
    private Comercial comercial;

}
