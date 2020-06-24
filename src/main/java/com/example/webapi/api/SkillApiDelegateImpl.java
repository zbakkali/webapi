package com.example.webapi.api;

import com.example.webapi.domain.Skill;
import com.example.webapi.mapper.SkillMapper;
import com.example.webapi.model.HALEmbeddedSkillsEmbeddedDto;
import com.example.webapi.model.HALSkillDto;
import com.example.webapi.model.HALSkillsDto;
import com.example.webapi.model.SkillDto;
import com.example.webapi.service.SkillService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SkillApiDelegateImpl implements SkillApiDelegate {

    private final SkillService skillService;
    private final SkillMapper skillMapper;

    public SkillApiDelegateImpl(SkillService skillService, SkillMapper skillMapper) {
        this.skillService = skillService;
        this.skillMapper = skillMapper;
    }

    @Override
    public ResponseEntity<HALSkillDto> createSkill(String authorization, SkillDto skillDto) {
        Skill skill = skillService.createSkill(skillMapper.dtoToEntity(skillDto));
        return new ResponseEntity<>(skillMapper.entityToDto(skill), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteSkillById(Long skillId, String authorization, String ifMatch) {
        skillService.deleteSkill(skillId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<HALSkillsDto> findAllSkills(String authorization, Integer limit, Integer page, String ifNoneMatch) {
            List<HALSkillDto> skills = skillService.findAllSkills(PageRequest.of(page, limit))
                .getContent()
                .stream()
                .map(skillMapper::entityToDto)
                .collect(Collectors.toList());
        HALEmbeddedSkillsEmbeddedDto halEmbeddedSkillsEmbeddedDto = new HALEmbeddedSkillsEmbeddedDto();
        halEmbeddedSkillsEmbeddedDto.setSkills(skills);
        HALSkillsDto halSkillsDto = new HALSkillsDto();
        halSkillsDto.setEmbedded(halEmbeddedSkillsEmbeddedDto);
        return ResponseEntity.ok(halSkillsDto);
    }

    @Override
    public ResponseEntity<HALSkillDto> getSkillById(Long skillId, String authorization, String ifNoneMatch) {
        Optional<HALSkillDto> halSkillDto = skillService.findSkillById(skillId).map(skillMapper::entityToDto);
        return ResponseEntity.of(halSkillDto);
    }

    @Override
    public ResponseEntity<HALSkillDto> updateSkillById(Long skillId, String authorization, String ifMatch, SkillDto skillDto) {
        skillDto.setId(skillId);
        Skill skill = skillService.updateSkill(skillMapper.dtoToEntity(skillDto));
        return ResponseEntity.ok(skillMapper.entityToDto(skill));
    }
}
