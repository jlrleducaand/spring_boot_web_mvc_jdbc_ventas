package org.iesvdm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class PedidoDTO {

    private long id;
    private Double total;
    private Date fecha;
    private int id_cliente;
    private int id_comercial;



}
