package org.iesvdm.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iesvdm.modelo.Comercial;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Data
//Para generar un constructor con lombok con todos los args
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class ClienteDTO {

    private long id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String ciudad;
    private int categoria;

    //Campos añadidos al Bean Cliente
    private List<Comercial> comercialesAsociados;
    private Map<Integer, Integer> comercialNumPedidos;

    private Double sumaPedidos;
    private int numPedidos;

    private int numPedidosUltimoTrimestre;
    private int numPedidosUltimoSemestre;
    private int numPedidosUltimoAño;
    private int numPedidosUltimoLustro;

    public ClienteDTO(long id, String nombre, String apellido1, String apellido2, String ciudad, int categoria) {
        this.id = id;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.ciudad = ciudad;
        this.categoria = categoria;
    }

}
