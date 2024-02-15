package org.iesvdm.modelo;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comercial {

	private long id;

	@NotBlank(message="{msg.valid.blank}")
	//@NotNull(message="{msg.valid.null}")
	@Length(max=30, message="{msg.valid.maxLenght}")
	private String nombre;

	@NotBlank(message="{msg.valid.blank}")
	@NotNull(message="{msg.valid.null}")
	@Length(max=30, message="{msg.valid.maxLenght}")
	private String apellido1;

	@Length(max=30, message="{msg.valid.maxLenght}")
	private String apellido2;

	@DecimalMin(value="0.276", message="{msg.valid.min}")
	@DecimalMax(value="0.946", message="{msg.valid.max}")
	private BigDecimal comision;
	
}
