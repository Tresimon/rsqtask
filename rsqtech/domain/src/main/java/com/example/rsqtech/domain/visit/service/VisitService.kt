package com.example.rsqtech.domain.visit.service

import com.example.rsqtech.domain.doctor.model.Doctor
import com.example.rsqtech.domain.doctor.repository.DoctorRepository
import com.example.rsqtech.domain.organisation.repository.OrganisationRepository
import com.example.rsqtech.domain.patient.model.Patient
import com.example.rsqtech.domain.patient.repository.PatientRepository
import com.example.rsqtech.domain.visit.dto.form.VisitFormDto
import com.example.rsqtech.domain.visit.model.Visit
import com.example.rsqtech.domain.visit.repository.VisitRepository
import javassist.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class VisitService(
    private val visitRepository: VisitRepository,
    private val patientRepository: PatientRepository,
    private val doctorRepository: DoctorRepository,
    private val organisationRepository: OrganisationRepository
) {

    fun getVisits(): List<Visit> =
        visitRepository.findAll()

    fun createVisit(visitForm: VisitFormDto): ResponseEntity<Visit> {
        val patient = getPatientById(visitForm.patientId)
        val doctor = getDoctorById(visitForm.doctorId)
        var newVisit = Visit(
            patient,
            doctor,
            visitForm.date,
            doctor.organisation
        )
        return ResponseEntity.ok(visitRepository.save(newVisit))
    }

    fun getVisitById(visitId: Long): ResponseEntity<Visit> =
        visitRepository.findById(visitId).map { visit ->
            ResponseEntity.ok(visit)
        }.orElse(ResponseEntity.notFound().build())

    fun getVisitsByOrganisationId(organisationId: Long): List<Visit> {
        val organisation = getOrganisationById(organisationId)
        return visitRepository.findByOrganisation(organisation)
    }

    private fun getOrganisationById(organisationId: Long) = organisationRepository.findById(organisationId)
        .orElseThrow { NotFoundException("Couldn't found Organisation with id: $organisationId") }

    fun getVisitsByPatientId(patientId: Long): List<Visit> =
        visitRepository.findAllByPatient(patientRepository.findById(patientId))

    fun updateVisit(visitId: Long, newVisit: VisitFormDto): ResponseEntity<Visit> =
        visitRepository.findById(visitId).map { currentVisit ->
            val updatedVisit: Visit =
                currentVisit
                    .copy(
                        date = newVisit.date
                    )
            ResponseEntity.ok().body(visitRepository.save(updatedVisit))
        }.orElse(ResponseEntity.notFound().build())

    fun deleteVisit(visitId: Long): ResponseEntity<Void> =
        visitRepository.findById(visitId).map { visit ->
            visitRepository.delete(visit)
            ResponseEntity<Void>(HttpStatus.ACCEPTED)
        }.orElse(ResponseEntity.notFound().build())

    private fun getPatientById(patientId: Long) : Patient =
        patientRepository.findById(patientId)
            .orElseThrow{NotFoundException("Couldn't found Patient with id: $patientId")}

    private fun getDoctorById(doctorId: Long) : Doctor =
        doctorRepository.findById(doctorId)
            .orElseThrow{NotFoundException("Couldn't found Doctor with id: $doctorId")}
}
