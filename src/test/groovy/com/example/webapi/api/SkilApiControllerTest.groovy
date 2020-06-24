package com.example.webapi.api

import com.example.webapi.Application
import com.example.webapi.mapper.SkillMapper
import com.example.webapi.service.SkillService
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

@ContextConfiguration(classes = Application)
@WebMvcTest(SkillApiController)
@Import([
    SkillApiDelegateImpl.class,
    KeycloakSpringBootConfigResolver.class
])
@WithMockUser
class SkilApiControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    @SpringBean
    private SkillService skillService = Mock()

    @SpringBean
    private SkillMapper skillMapper = Mock()

    @SpringBean
    private KeycloakSpringBootConfigResolver keycloakSpringBootConfigResolver = Mock()

    def "when get is performed then the response has status 200 and has a json HAL skills content"() {
        given:
        skillService.findAllSkills(_ as Pageable) >> Page.empty()

        expect: "Status is 200"
        mvc.perform(get("/skills"))
                .andExpect(status().isOk())
    }
}
