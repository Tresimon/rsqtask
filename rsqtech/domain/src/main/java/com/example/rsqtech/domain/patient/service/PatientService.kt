package com.example.rsqtech.domain.patient.service

import com.example.rsqtech.domain.organisation.repository.OrganisationRepository
import com.example.rsqtech.domain.patient.dto.form.PatientFormDto
import com.example.rsqtech.domain.patient.model.Patient
import com.example.rsqtech.domain.patient.repository.PatientRepository
import javassist.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class PatientService (private val patientRepository: PatientRepository,
                      private val organisationRepository: OrganisationRepository
) {

    fun getPatients(): List<Patient> =
        patientRepository.findAll()

    fun createPatient(patientFormDto: PatientFormDto): ResponseEntity<Patient> {
        val patient = Patient(
            patientFormDto.name,
            patientFormDto.surname,
            patientFormDto.address,
            getOrganisationById(patientFormDto.organisationId!!)
        )
        return ResponseEntity.ok(patientRepository.save(patient))
    }

    fun getPatientById(patientId: Long): ResponseEntity<Patient> =
        patientRepository.findById(patientId).map { patient ->
            ResponseEntity.ok(patient)
        }.orElse(ResponseEntity.notFound().build())

    fun updatePatient(patientId: Long, newPatient: PatientFormDto): ResponseEntity<Patient> =
        patientRepository.findById(patientId).map { currentPatient ->
            val updatedPatient: Patient =
                currentPatient
                    .copy(
                        surname = newPatient.surname,
                        address = newPatient.address
                    )
            ResponseEntity.ok().body(patientRepository.save(updatedPatient))
        }.orElse(ResponseEntity.notFound().build())

    fun deletePatient(patientId: Long): ResponseEntity<Void> =
        patientRepository.findById(patientId).map { patient ->
            patientRepository.delete(patient)
            ResponseEntity<Void>(HttpStatus.ACCEPTED)
        }.orElse(ResponseEntity.notFound().build())

    fun getPatientsByOrganisationId(organisationId: Long): List<Patient> {
        val organisation = getOrganisationById(organisationId)
        return patientRepository.findByOrganisation(organisation)
    }

    private fun getOrganisationById(organisationId: Long) = organisationRepository.findById(organisationId)
        .orElseThrow { NotFoundException("Couldn't found Organisation with id: $organisationId") }
}