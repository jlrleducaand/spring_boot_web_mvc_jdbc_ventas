package org.iesvdm.controlador;

import java.util.List;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PedidoController {


    private PedidoService pedidoService;
    public PedidoController(PedidoService pedidoService) {

        this.pedidoService = pedidoService;
    }


    @GetMapping("/pedidos")
    	public String listar(Model model) {
        List<Pedido> listaPedidos = pedidoService.listAll();
        model.addAttribute("listaPedidos", listaPedidos);

        return "pedidos";
    }

}
