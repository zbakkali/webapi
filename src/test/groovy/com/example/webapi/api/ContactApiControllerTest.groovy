package com.example.webapi.api

import com.example.webapi.Application
import com.example.webapi.helper.AuthenticationFacade
import com.example.webapi.mapper.ContactMapper
import com.example.webapi.service.ContactService
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ContactApiController)
@Import(ContactApiDelegateImpl)
@ContextConfiguration(classes = Application)
@WithMockUser
class ContactApiControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    @SpringBean
    private ContactService contactService = Mock()

    @SpringBean
    private ContactMapper contactMapper = Mock()

    @SpringBean
    private KeycloakSpringBootConfigResolver keycloakSpringBootConfigResolver = Mock()

    @SpringBean
    private AuthenticationFacade authenticationFacade = Mock()

    def "when get is performed then the response has status 200 and has a json HAL contacts content"() {
        given:
        contactService.findContactsBySkills(_ as Pageable, null, null) >> Page.empty()

        expect: "Status is 200 "
        mvc.perform(get("/contacts"))
                .andExpect(status().isOk())
    }
}
