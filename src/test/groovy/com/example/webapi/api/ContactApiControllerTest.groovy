package com.example.webapi.api

import com.example.webapi.Application
import com.example.webapi.mapper.ContactMapper
import com.example.webapi.service.ContactService
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
@WebMvcTest(ContactApiController)
@Import(ContactApiDelegateImpl)
@WithMockUser
class ContactApiControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    @SpringBean
    private ContactService contactService = Mock()

    @SpringBean
    private ContactMapper contactMapper = Mock()

    def "when get is performed then the response has status 200 and has a json HAL contacts content"() {
        given:
        contactService.findContactsBySkills(_ as Pageable, null, null) >> Page.empty()

        expect: "Status is 200 and the response is a json HAL contacts"
        mvc.perform(get("/contacts"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"_links\":null,\"page\":null,\"_embedded\":{\"contacts\":[]}}"))
    }
}
