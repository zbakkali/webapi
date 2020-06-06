package com.example.webapi.mapper;

import com.example.webapi.domain.Skill;
import com.example.webapi.model.HALSkillDto;
import com.example.webapi.model.SkillDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    Skill dtoToEntity(SkillDto skillDto);

    @Mapping(target = "links", ignore = true)
    HALSkillDto entityToDto(Skill skill);
}
