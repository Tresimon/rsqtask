package com.example.rsqtech.domain.patient.controller

import com.example.rsqtech.domain.patient.dto.form.PatientFormDto
import com.example.rsqtech.domain.patient.model.Patient
import com.example.rsqtech.domain.patient.service.PatientService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/patients")
class PatientController(private val patientService: PatientService) {

    @GetMapping
    fun getPatients(): List<Patient> =
        patientService.getPatients()

    @GetMapping("/{id}")
    fun getPatientById(@PathVariable("id") patientId: Long): ResponseEntity<Patient> =
        patientService.getPatientById(patientId)

    @RequestMapping(method = [RequestMethod.GET], value = ["/organisation/{organisationId}"])
    fun getPatientsByOrganisationId(@PathVariable("organisationId") organisationId: Long): List<Patient> =
        patientService.getPatientsByOrganisationId(organisationId)

    @PostMapping
    fun createPatient(@Valid @RequestBody patientFormDto: PatientFormDto): ResponseEntity<Patient> =
        patientService.createPatient(patientFormDto)

    @PutMapping("/{id}")
    fun updatePatientById(
        @PathVariable("id") patientId: Long,
        @Valid @RequestBody newPatient: PatientFormDto
    ): ResponseEntity<Patient> =
        patientService.updatePatient(patientId, newPatient)

    @DeleteMapping("/{id}")
    fun deletePatient(@PathVariable("id") patientId: Long): ResponseEntity<Void> =
        patientService.deletePatient(patientId)
}