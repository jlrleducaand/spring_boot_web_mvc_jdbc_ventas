package org.iesvdm.service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.iesvdm.dao.ClienteDAO;
import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.dao.PedidoDAOImpl;
import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.mapstruct.ClienteMapper;
import org.iesvdm.mapstruct.ComercialMapper;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;

@Service
public class ComercialService implements ComercialServiceI {

    private final ComercialDAO comercialDAO;
    private final PedidoDAOImpl pedidoDAOImpl;
    private final ClienteDAO clienteDAO;
    private final ComercialMapper comercialMapper;
    private final ComercialDTO comercialDTO;
    private final ClienteMapper clienteMapper;



    @Autowired
    public ComercialService(ComercialDAO comercialDAO, PedidoDAOImpl pedidoDAOImpl,
                            ClienteDAO clienteDAO, ComercialMapper comercialMapper,
                            ClienteMapper clienteMapper, ComercialDTO comercialDTO) {

        this.comercialDAO = comercialDAO;
        this.pedidoDAOImpl = pedidoDAOImpl;
        this.clienteDAO = clienteDAO;
        this.comercialMapper = comercialMapper;
        this.clienteMapper = clienteMapper;
        this.comercialDTO = comercialDTO;
    }

    public List<Comercial> listAll() {

        return comercialDAO.getAll();
    }

    public Comercial detalle(Integer id) {

        return comercialDAO.find(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comercial no encontrado"));
    }

    public void newComercial(Comercial comercial) {

        comercialDAO.create(comercial);

    }

    public void replaceComercial(Comercial comercial) {

        comercialDAO.update(comercial);

    }

    public void deleteComercial(int id) {

        comercialDAO.delete(id);

    }

    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public List<PedidoDTO> obtenerPedidosPorComercial(int idComercial) {
        return comercialDAO.listaPedidosComercial(idComercial);
    }

    @Override
    public Optional<Cliente> obtenerClientePorId(int idCliente) {
        return clienteDAO.find(idCliente);
    }


    @Override
    public OptionalDouble obtenerMediaPedidosComercial(int  idcomercial) {
        // Obtener la lista de pedidos y calcular la media
        List<PedidoDTO> listaPedidosDTO = comercialDAO.listaPedidosComercial(idcomercial);
        OptionalDouble media = listaPedidosDTO.stream()
                .mapToDouble(PedidoDTO::getTotal)
                .average();
        return media;
    }

    @Override
    public OptionalDouble obtenerTotalPedidosComercial(int idComercial) {
        List<PedidoDTO> listaPedidosDTO = comercialDAO.listaPedidosComercial(idComercial);
        OptionalDouble total = listaPedidosDTO.stream()
                .mapToDouble(PedidoDTO::getTotal)
                .reduce(Double::sum);
        return total;
    }


    @Override
    public Optional<PedidoDTO> obtenerPedidoDeMayorCuantia(int idComercial) {
        return Optional.ofNullable(comercialDAO.listaPedidosComercial(idComercial).stream()
                .max(Comparator.comparing(PedidoDTO::getTotal))
                .orElse(null));
    }

    @Override
    public Optional<PedidoDTO> obtenerPedidoDeMenorCuantia(int idComercial) {
        return Optional.ofNullable(comercialDAO.listaPedidosComercial(idComercial).stream()
                .min(Comparator.comparing(PedidoDTO::getTotal))
                .orElse(null));
    }

    @Override
    public List<ClienteDTO> obtenerListaClientesConPedidosPorIdComercial(int idComercial) {
        List<PedidoDTO> lstPediComercial = obtenerPedidosPorComercial(idComercial);

        // Obtener clientes únicos que tienen pedidos con el comercial específico
        List<ClienteDTO> lstClieConPedidos = lstPediComercial.stream()
                .map(PedidoDTO::getId_cliente)  // Obtener solo los IDs de clientes
                .distinct()  // Filtrar clientes únicos
                .map(idCliente -> clienteDAO.find(idCliente))  // Obtener clientes por ID
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(clienteMapper::clienteAClienteDTO)  // Convertir a DTO si es necesario
                .collect(Collectors.toList());

        return lstClieConPedidos;
    }



    @Override
    public List<PedidoDTO> obtenerListaPedidoDeThisComercialIdCliente(int idCliente) {
        List<PedidoDTO> lstPorComer = obtenerPedidosPorComercial(this.comercialDTO.getId());

        // Filtrar la lista de pedidos para obtener solo los relacionados con el cliente específico
        List<PedidoDTO> lstPorComerPorClie = lstPorComer.stream()
                .filter(pedido -> pedido.getId_cliente() == idCliente)
                .collect(Collectors.toList());

        return lstPorComerPorClie;
    }

}


