package com.example.rsqtech.domain.visit

import com.example.rsqtech.domain.patient.dto.form.PatientFormDto
import com.example.rsqtech.domain.visit.dto.form.VisitFormDto
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
import com.example.rsqtech.domain.config.H2JpaConfiguation
import com.example.rsqtech.domain.visit.controller.VisitController
import com.example.rsqtech.domain.visit.service.VisitService

import java.time.LocalDateTime

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner.class)
@SpringBootTest(classes = [
        H2JpaConfiguation,
        VisitController, VisitService])
@EnableAutoConfiguration(exclude= FlywayAutoConfiguration.class)
@AutoConfigureMockMvc
class VisitControllerTest extends Specification {

    private static final String DELETE_VISIT = "DELETE FROM VISIT"
    private static final String DELETE_DOCTOR = "DELETE FROM DOCTOR"
    private static final String DELETE_PATIENT = "DELETE FROM PATIENT"
    private static final String DELETE_ORGANISATION = "DELETE FROM ORGANISATION"
    private static final String INSERT_ORGANISATION = "INSERT INTO ORGANISATION(id, name) VALUES (1, 'Medicover')"
    private static final String INSERT_PATIENT = "INSERT INTO PATIENT(id, address, name, surname, organisation_id) VALUES (1, 'ul. Marsyliańska 9', 'Arkadiusz', 'Milik', 1)"
    private static final String INSERT_DOCTOR = "INSERT INTO DOCTOR(id, major, name, surname, organisation_id) VALUES (1, 'Fizjoterapeuta', 'Piotr', 'Zieliński', 1)"
    private static final String INSERT_VISIT = "INSERT INTO VISIT(id, date, doctor_id, patient_id, organisation_id) VALUES (1, '2021-03-26T12:00:00.000', 1, 1, 1)"


    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper mapper

    @Test
    @SqlGroup([
            @Sql(statements = [INSERT_ORGANISATION, INSERT_PATIENT, INSERT_DOCTOR]),
            @Sql(statements = [DELETE_VISIT, DELETE_PATIENT, DELETE_DOCTOR, DELETE_ORGANISATION], executionPhase = AFTER_TEST_METHOD)
    ])
    def "should create visit"() throws Exception {
        given:
        def requestBody = asJsonString(new VisitFormDto(LocalDateTime.MAX, 1L, 1L))
        when:
        def result = mockMvc.perform(MockMvcRequestBuilders
                .post("/visits")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

        then:
        result.andExpect(status().isOk())
        and:
        result.andExpect(MockMvcResultMatchers.jsonPath('$.date').value(LocalDateTime.MAX.toString()))
    }

    @Test
    @SqlGroup([
            @Sql(statements = [INSERT_ORGANISATION, INSERT_PATIENT, INSERT_DOCTOR, INSERT_VISIT]),
            @Sql(statements = [DELETE_VISIT, DELETE_PATIENT, DELETE_DOCTOR, DELETE_ORGANISATION], executionPhase = AFTER_TEST_METHOD)
    ])
    def "should update visit"() throws Exception {
        given:
        LocalDateTime localDateTimeToSet = LocalDateTime.of(2021, 02, 26, 23, 30)
        def requestBody = asJsonString(new VisitFormDto(localDateTimeToSet, 1, 1))
        when:
        def result = mockMvc.perform(MockMvcRequestBuilders
                .put("/visits/1")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

        then:
        result.andExpect(status().isOk())
        and:
        result.andExpect(MockMvcResultMatchers.jsonPath('$.date').value("2021-02-26T23:30:00"))
    }

    @Test
    @SqlGroup([
            @Sql(statements = [INSERT_ORGANISATION, INSERT_PATIENT, INSERT_DOCTOR, INSERT_VISIT]),
            @Sql(statements = [DELETE_VISIT, DELETE_PATIENT, DELETE_DOCTOR, DELETE_ORGANISATION], executionPhase = AFTER_TEST_METHOD)
    ])
    def "should delete visit"() throws Exception {
        when:
        def result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/visits/1")
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
