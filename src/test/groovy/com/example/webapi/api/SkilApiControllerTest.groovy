package com.example.webapi.api

import com.example.webapi.Application
import com.example.webapi.mapper.SkillMapper
import com.example.webapi.service.SkillService
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ContextConfiguration(classes = Application)
@WebMvcTest(SkillApiController)
@Import(SkillApiDelegateImpl)
@WithMockUser
class SkilApiControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    @SpringBean
    private SkillService skillService = Mock()

    @SpringBean
    private SkillMapper skillMapper = Mock()

    def "when get is performed then the response has status 200 and has a json HAL skills content"() {
        given:
        skillService.findAllSkills(_ as Pageable) >> Page.empty()

        expect: "Status is 200 and the response is a json HAL skills"
        mvc.perform(get("/skills"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"_links\":null,\"page\":null,\"_embedded\":{\"skills\":[]}}"))
    }
}
