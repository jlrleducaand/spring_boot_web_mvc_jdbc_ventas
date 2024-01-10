package org.iesvdm.service;

import java.util.List;

import org.iesvdm.dao.ClienteDAO;
import org.iesvdm.modelo.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service  //solo espero logica de negocios
public class ClienteService {

	@Autowired
	private ClienteDAO clienteDAO;
	
	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired
	//@Autowired
	//public ClienteService(ClienteDAO clienteDAO) {
	//
	//	this.clienteDAO = clienteDAO;
	//}
	
	public List<Cliente> listAll() {
		
		return clienteDAO.getAll();
		
	}
	
	

}
