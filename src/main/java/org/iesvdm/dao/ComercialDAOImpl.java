package org.iesvdm.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.iesvdm.domain.Comercial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

//Anotación lombok para logging (traza) de la aplicación
@Slf4j
@Repository
//Utilizo lombok para generar el constructor
//@AllArgsConstructor
public class ComercialDAOImpl implements ComercialDAO<Comercial> {

	//JdbcTemplate se puede inyectar por el constructor de la clase automáticamente
	// no necesita el @Autowired  ya que podemos poner  @AllArgsConstructor a la clase
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Comercial> getAll() {

		List<Comercial> listComer = jdbcTemplate.query(
				"SELECT * FROM comercial",
				(rs, rowNum) -> new Comercial(rs.getInt("id"),
						rs.getString("nombre"),
						rs.getString("apellido1"),
						rs.getString("apellido2"),
						rs.getFloat("comision"))

		);

		log.info("Devueltos {} registros.", listComer.size());

		return listComer;
	}

	@Override
	public synchronized void create(Comercial comercial) {
		// TODO Auto-generated method stub
		//Desde java15+ se tiene la triple quote """ para bloques de texto como cadenas. y no tendra en cuenta los saltos de linea
		String sqlInsert = """
							INSERT INTO comercial (nombre, apellido1, apellido2, comision) 
							VALUES  (     ?,         ?,         ?,       ?)
						   """;

		KeyHolder keyHolder = new GeneratedKeyHolder();
		//Con recuperación de id generado
		int rows = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[] { "id" });
			int idx = 1;
			ps.setString(idx++, comercial.getNombre());
			ps.setString(idx++, comercial.getApellido1());
			ps.setString(idx++, comercial.getApellido2());
			ps.setFloat(idx++, comercial.getComision());
			return ps;
		},keyHolder);

		comercial.setId(keyHolder.getKey().intValue());

		log.info("Insertados {} registros.", rows);
	}

	/**
	 * Devuelve Optional de Comercial con el ID dado.
	 */
	@Override
	public Optional<Comercial> find(int id) {
		// TODO Auto-generated method stub
		Comercial comer =  jdbcTemplate
				.queryForObject("SELECT * FROM comercial WHERE id = ?"
						, (rs, rowNum) -> new Comercial(rs.getInt("id"),
								rs.getString("nombre"),
								rs.getString("apellido1"),
								rs.getString("apellido2"),
								rs.getFloat("comision"))
						, id
				);

		if (comer != null) {
			return Optional.of(comer);}
		else {
			log.info("Comercial no encontrado.");
			return Optional.empty(); }

	}

	@Override
	public void update(Comercial comercial) {
		// TODO Auto-generated method stub
		int rows = jdbcTemplate.update("""
										UPDATE comercial SET 
														nombre = ?, 
														apellido1 = ?, 
														apellido2 = ?,
														comision = ?  
												WHERE id = ?
										""" , comercial.getNombre()
											, comercial.getApellido1()
											, comercial.getApellido2()
											, comercial.getComision()
											, comercial.getId());

		log.info("Update de Cliente con {} registros actualizados.", rows);

	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		int rows = jdbcTemplate.update("DELETE FROM comercial WHERE id = ?", id);

		log.info("Delete de Comercial con {} registros eliminados.", rows);


	}

}
