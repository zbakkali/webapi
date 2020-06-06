package com.example.webapi.service;

import com.example.webapi.domain.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SkillService {
    Page<Skill> findAllSkills(Pageable pageable);
    Skill createSkill(Skill skill);
    Optional<Skill> findSkillById(long id);
    Skill updateSkill(Skill skill);
    void deleteSkill(long id);
}
