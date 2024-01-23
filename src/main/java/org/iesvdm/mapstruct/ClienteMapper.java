package org.iesvdm.mapstruct;

import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.modelo.Comercial;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ClienteMapper {

    public ComercialDTO comercialAComercialDTO(Comercial comercial);
    public Comercial comercialDTOAComercial(ComercialDTO comercial);

}
