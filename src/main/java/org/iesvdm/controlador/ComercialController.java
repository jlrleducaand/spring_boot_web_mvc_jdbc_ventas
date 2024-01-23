package org.iesvdm.controlador;

import java.util.List;

import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.dao.ComercialDAOImpl;
import org.iesvdm.dto.PedidoDTO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.service.ComercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class ComercialController {

    @Autowired
    private ComercialService comercialService;

    @Autowired
    private ComercialDAO comercialDAO;

    //Se utiliza inyección automática por constructor del framework Spring.
    //Por tanto, se puede omitir la anotación Autowired
    //@Autowired  ,ademas si solo hay un constructor se puede omitir tb


    //@RequestMapping(value = "/clientes", method = RequestMethod.GET)
    //equivalente a la siguiente anotación
    @GetMapping("/comerciales") //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa
    public String listar(Model model) {

        List<Comercial> listaComerciales =  comercialService.listAll();
        model.addAttribute("listaComerciales", listaComerciales);

        return "comerciales";

    }

    @GetMapping("/comerciales/{id}")
    public String detalle(Model model, @PathVariable Integer id ) {

        // Obtener el detalle del comercial.
        Comercial comercial = comercialService.detalle(id);
        model.addAttribute("comercial", comercial);

        // Obtener la lista de pedidos del comercial y añadirla al modelo.
        List<PedidoDTO> listaPedidos = comercialDAO.listaPedidosComercial(id);
        model.addAttribute("listaPedidos", listaPedidos);

        // Obtener el detalle del cliente.


        // Nombre de la plantilla
        return "comercial-detalle";

    }

    @GetMapping("/comerciales/crear")
    public String crear(Model model) {

        Comercial comercial = new Comercial();
        model.addAttribute("comercial", comercial);

        return "comercial-crear";

    }

    @PostMapping("/comerciales/crear")
    public RedirectView submitCrear(@ModelAttribute("comercial") Comercial comercial) {

        comercialService.newComercial(comercial);

        return new RedirectView("/comerciales") ;

    }

    @GetMapping("/comerciales/editar/{id}")
    public String editar(Model model, @PathVariable Integer id) {

        Comercial comercial = comercialService.detalle(id);
        model.addAttribute("comercial", comercial);

        return "comercial-editar";

    }


    @PostMapping("/comerciales/editar/{id}")
    public RedirectView submitEditar(@ModelAttribute("comercial") Comercial comercial) {

        comercialService.replaceComercial(comercial);

        return new RedirectView("/comerciales");
    }

    @PostMapping("/comerciales/borrar/{id}")
    public RedirectView submitBorrar(@PathVariable Integer id) {

        comercialService.deleteComercial(id);

        return new RedirectView("/comerciales");
    }


}
