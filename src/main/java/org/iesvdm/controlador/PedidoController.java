package org.iesvdm.controlador;

import java.util.List;

import org.iesvdm.domain.Pedido;
import org.iesvdm.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

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


    @GetMapping("/pedidos/{id}")
    public String detalle(Model model, @PathVariable Integer id) {

        Pedido pedido = pedidoService.detalle(id);
        model.addAttribute("pedido", pedido);

        return "pedido-detalle";

    }

    @GetMapping("/pedidos/crear")
    public String crear(Model model) {

        Pedido pedido = new Pedido();
        model.addAttribute("pedido", pedido);

        return "pedido-crear";

    }

    @PostMapping("/pedidos/crear")
    public RedirectView submitCrear(@ModelAttribute("pedido") Pedido pedido) {

        pedidoService.newPedido(pedido);

        return new RedirectView("/pedidos");

    }

    @GetMapping("/pedidos/editar/{id}")
    public String editar(Model model, @PathVariable Integer id) {

        Pedido pedido = pedidoService.detalle(id);
        model.addAttribute("pedido", pedido);

        return "pedido-editar";

    }


    @PostMapping("/pedidos/editar/{id}")
    public RedirectView submitEditar(@ModelAttribute("pedido") Pedido pedido) {

        pedidoService.replacePedido(pedido);

        return new RedirectView("/pedidos");
    }

    @PostMapping("/pedidos/borrar/{id}")
    public RedirectView submitBorrar(@PathVariable Integer id) {

        pedidoService.deletePedido(id);

        return new RedirectView("/pedidos");
    }
}
