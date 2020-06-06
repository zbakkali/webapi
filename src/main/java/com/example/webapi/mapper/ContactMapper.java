package com.example.webapi.mapper;

import com.example.webapi.domain.Contact;
import com.example.webapi.domain.Phone;
import com.example.webapi.model.HALContactDto;
import com.example.webapi.model.HALExtendedContactDto;
import com.example.webapi.model.PhoneDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = SkillMapper.class)
public interface ContactMapper {

    @Mapping(target = "skills", ignore = true)
    Contact dtoToEntity(HALExtendedContactDto contactDto);

    @Mapping(target = "id", ignore = true)
    Phone dtoToEntity(PhoneDto phoneDto);

    @Mapping(target = "fullname", source = "contact")
    @Mapping(target = "links", ignore = true)
    HALContactDto entityToDto(Contact contact);

    @Mapping(target = "fullname", source = "contact")
    @Mapping(target = "links", ignore = true)
    @Mapping(target = "embedded.skills", source = "skills")
    HALExtendedContactDto entityToExtendedDto(Contact contact);

    default String contactToFullname(Contact contact) {
        return contact.getFirstname() + " " + contact.getLastname();
    }
}
