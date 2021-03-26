package com.example.rsqtech.domain.patient

import com.example.rsqtech.domain.config.H2JpaConfiguation
import com.example.rsqtech.domain.doctor.dto.form.DoctorFormDto
import com.example.rsqtech.domain.patient.controller.PatientController
import com.example.rsqtech.domain.patient.dto.form.PatientFormDto
import com.example.rsqtech.domain.patient.service.PatientService
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
        PatientController, PatientService])
@EnableAutoConfiguration(exclude= FlywayAutoConfiguration.class)
@AutoConfigureMockMvc
class PatientControllerTest extends Specification {

    private static final String DELETE_PATIENT = "DELETE FROM PATIENT"
    private static final String DELETE_ORGANISATION = "DELETE FROM ORGANISATION"
    private static final String INSERT_PATIENT = "INSERT INTO PATIENT(id, address, name, surname, organisation_id) VALUES (1, 'ul. Pacjent', 'Jan', 'Nowak', 1)"
    private static final String INSERT_ORGANISATION = "INSERT INTO ORGANISATION(id, name) VALUES (1, 'Medicover')"

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper mapper

    @Test
    @SqlGroup([
            @Sql(statements = INSERT_ORGANISATION),
            @Sql(statements = [DELETE_PATIENT, DELETE_ORGANISATION], executionPhase = AFTER_TEST_METHOD)
    ])
    def "should create patient"() throws Exception {
        given:
        def requestBody = asJsonString(new PatientFormDto("Jan", "Nowak", "ul. Pacjent", 1))
        when:
        def result = mockMvc.perform(MockMvcRequestBuilders
                .post("/patients")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

        then:
        result.andExpect(status().isOk())
        and:
        result.andExpect(MockMvcResultMatchers.jsonPath('$.address').value("ul. Pacjent"))
    }

    @Test
    @SqlGroup([
            @Sql(statements = [INSERT_ORGANISATION, INSERT_PATIENT]),
            @Sql(statements = [DELETE_PATIENT, DELETE_ORGANISATION], executionPhase = AFTER_TEST_METHOD)
    ])
    def "should update patient"() throws Exception {
        given:
        def requestBody = asJsonString(new PatientFormDto("Jan", "Nowak", "ul. Chora", null))
        when:
        def result = mockMvc.perform(MockMvcRequestBuilders
                .put("/patients/1")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

        then:
        result.andExpect(status().isOk())
        and:
        result.andExpect(MockMvcResultMatchers.jsonPath('$.address').value("ul. Chora"))
    }

    @Test
    @SqlGroup([
            @Sql(statements = [INSERT_ORGANISATION, INSERT_PATIENT]),
            @Sql(statements = [DELETE_PATIENT, DELETE_ORGANISATION], executionPhase = AFTER_TEST_METHOD)
    ])
    def "should delete patient"() throws Exception {
        when:
        def result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/patients/1")
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
