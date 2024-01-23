package org.iesvdm.mapstruct;


import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.modelo.Comercial;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComercialMapper {


    public ComercialDTO comercialAComercialDTO(Comercial comercial);
    public Comercial comercialDTOAComercial(ComercialDTO comercialDTO);

}
