package com.example.rsqtech.domain.doctor

import com.example.rsqtech.domain.config.H2JpaConfiguation
import com.example.rsqtech.domain.doctor.controller.DoctorController
import com.example.rsqtech.domain.doctor.dto.form.DoctorFormDto
import com.example.rsqtech.domain.doctor.service.DoctorService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner.class)
@SpringBootTest(classes = [
        H2JpaConfiguation,
        DoctorController, DoctorService])
@EnableAutoConfiguration(exclude= FlywayAutoConfiguration.class)
@AutoConfigureMockMvc
class DoctorControllerTest extends Specification {

    private static final String DELETE_DOCTOR = "DELETE FROM DOCTOR"
    private static final String DELETE_ORGANISATION = "DELETE FROM ORGANISATION"
    private static final String INSERT_ORGANISATION = "INSERT INTO ORGANISATION(id, name) VALUES (1, 'Medicover')"
    private static final String INSERT_DOCTOR = "INSERT INTO DOCTOR(id, major, name, surname, organisation_id) VALUES (1, 'Lekarz', 'Jan', 'Nowak', 1)"

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper mapper

    @Test
    @SqlGroup([
            @Sql(statements = INSERT_ORGANISATION),
            @Sql(statements = [DELETE_DOCTOR, DELETE_ORGANISATION], executionPhase = AFTER_TEST_METHOD)
    ])
    def "should create doctor"() throws Exception {
        given:
        def requestBody = asJsonString(new DoctorFormDto("Jan", "Nowak", "Lekarz", 1))
        when:
        def result = mockMvc.perform(MockMvcRequestBuilders
                .post("/doctors")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

        then:
        result.andExpect(status().isOk())
        and:
        result.andExpect(MockMvcResultMatchers.jsonPath('$.major').value("Lekarz"))
    }

    @Test
    @SqlGroup([
            @Sql(statements = [INSERT_ORGANISATION, INSERT_DOCTOR]),
            @Sql(statements = [DELETE_DOCTOR, DELETE_ORGANISATION], executionPhase = AFTER_TEST_METHOD)
    ])
    def "should update doctor"() throws Exception {
        given:
        def requestBody = asJsonString(new DoctorFormDto("Jan", "Nowak", "Doktor", null))
        when:
        def result = mockMvc.perform(MockMvcRequestBuilders
                .put("/doctors/1")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

        then:
        result.andExpect(status().isOk())
        and:
        result.andExpect(MockMvcResultMatchers.jsonPath('$.major').value("Doktor"))
    }

    @Test
    @SqlGroup([
            @Sql(statements = [INSERT_ORGANISATION, INSERT_DOCTOR]),
            @Sql(statements = [DELETE_DOCTOR, DELETE_ORGANISATION], executionPhase = AFTER_TEST_METHOD)
    ])
    def "should delete doctor"() throws Exception {
        when:
        def result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/doctors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

        then:
        result.andExpect(status().isAccepted())
    }

    String asJsonString(Object obj) {
        try {
            return mapper.writeValueAsString(obj)
        } catch (Exception e) {
            throw new RuntimeException(e)
        }
    }
}
