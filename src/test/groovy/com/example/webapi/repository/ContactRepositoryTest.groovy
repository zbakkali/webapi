package com.example.webapi.repository

import com.example.webapi.Application
import com.example.webapi.domain.Contact
import com.example.webapi.domain.Skill
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = Application)
@DataJpaTest
class ContactRepositoryTest extends Specification {

    @Autowired
    private ContactRepository contactRepository

    @Autowired
    private SkillRepository skillRepository

    def "looking for a contact with a skill"() {
        given: "create a skill"
        def skill = new Skill().builder()
                .name("Java")
                .level("Expert")
                .build()
        skillRepository.save(skill)

        and: "create a contact with the skill"
        def contact = new Contact()
        contact.setFirstname("Zoubair")
        contact.setLastname("Bakkali")
        contact.addSkill(skill)
        contactRepository.save(contact)

        when: "looking for contacts with the skill"
        def page = contactRepository.findBySkillsIdIn(List.of(skill.getId()), null)

        then: "only the created contact is found"
        page.getTotalElements() == 1
        page.getTotalPages() == 1
        page.hasContent()
        page.getContent().contains(contact)
    }
}
