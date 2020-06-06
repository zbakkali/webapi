package com.example.webapi.service.impl;

import com.example.webapi.domain.Skill;
import com.example.webapi.repository.SkillRepository;
import com.example.webapi.service.SkillService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SkillServiceImpl  implements SkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public Page<Skill> findAllSkills(Pageable pageable) {
        return skillRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    @Override
    public Optional<Skill> findSkillById(long id) {
        return skillRepository.findById(id);
    }

    @Override
    @Transactional
    public Skill updateSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    @Override
    @Transactional
    public void deleteSkill(long id) {
        skillRepository.deleteById(id);
    }
}
