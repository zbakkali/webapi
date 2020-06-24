package com.example.webapi

import com.example.webapi.api.ContactApiController
import com.example.webapi.api.SkillApiController
import com.example.webapi.repository.ContactRepository
import com.example.webapi.repository.SkillRepository
import com.example.webapi.service.ContactService
import com.example.webapi.service.SkillService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = Application)
@SpringBootTest
class LoadContextTest extends Specification {

    @Autowired(required = false)
    private ContactApiController contactsApiController

    @Autowired(required = false)
    private SkillApiController skillsApiController

    @Autowired(required = false)
    private ContactService contactService

    @Autowired(required = false)
    private SkillService skillService

    @Autowired(required = false)
    private ContactRepository contactRepository

    @Autowired(required = false)
    private SkillRepository skillRepository

    def "when context is loaded then all expected beans are created"() {
        expect: "the ContactsApiController, SkillsApiController, ContactService, SkillService, ContactRepository and SkillRepository are created"
        contactsApiController
        skillsApiController
        contactService
        skillService
        contactRepository
        skillRepository
    }
}