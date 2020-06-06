package com.example.webapi.exception;

public class SkillNotFoundException extends AbstractNotFoundException {

    public SkillNotFoundException(long skillId) {
        super(skillId, "Skill");
    }
}
