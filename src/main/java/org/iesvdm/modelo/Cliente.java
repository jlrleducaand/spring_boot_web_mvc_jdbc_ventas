package org.iesvdm.modelo;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
//La anotación @Data de lombok proporcionará el código de: 
//getters/setters, toString, equals y hashCode
//propio de los objetos POJOS o tipo Beans

@Data
//Para generar un constructor con lombok con todos los args
@AllArgsConstructor
//@RequiredArgsConstructor(onConstructor = {"id","nombre", "apellido1","apellido2","ciudad","categoria" })
@NoArgsConstructor
public class Cliente {

	private long id;

	@NotBlank(message="{msg.valid.blank}")
	@NotNull(message="	{msg.valid.null}")
	@Length(max=30, message="{msg.valid.maxLenght}")
	private String nombre;

	@NotBlank(message="{msg.valid.blank}")
	@NotNull(message="{msg.valid.null}")
	@Length(max=30, message="{msg.valid.maxLenght}")
	private String apellido1;

	@Length(max=30, message="{msg.valid.maxLenght}")
	private String apellido2;

	@NotBlank(message="{msg.valid.blank}")
	@Email(message="{msg.valid.email}")
	@Length(max=100, message="{msg.valid.maxLenght}")
	private String email;

	@NotBlank(message="{msg.valid.blank}")
	@NotNull(message="{msg.valid.null}")
	@Length(max=30, message="{msg.valid.maxLenght}")
	private String ciudad;

	@Pattern(regexp = "\\d00", message="{msg.valid.pattern}")
	//@Min(value=100, message="{msg.valid.min}")
	//@Max(value=1000, message="{msg.valid.max}")
	private int categoria;

	public Cliente(int id, String nombre, String apellido1, String apellido2, String ciudad, int categoria) {
		this.id = id;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.ciudad = ciudad;
		this.categoria = categoria;

	}
}
