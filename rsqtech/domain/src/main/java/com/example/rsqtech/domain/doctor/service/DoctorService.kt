package com.example.rsqtech.domain.doctor.service

import com.example.rsqtech.domain.doctor.dto.form.DoctorFormDto
import com.example.rsqtech.domain.doctor.model.Doctor
import com.example.rsqtech.domain.doctor.repository.DoctorRepository
import com.example.rsqtech.domain.organisation.repository.OrganisationRepository
import javassist.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class DoctorService (private val doctorRepository: DoctorRepository,
                     private val organisationRepository: OrganisationRepository
) {

    fun getDoctors(): List<Doctor> =
        doctorRepository.findAll()

    fun createDoctor(doctorFormDto: DoctorFormDto): ResponseEntity<Doctor> {
        val doctor = Doctor(
            doctorFormDto.name,
            doctorFormDto.surname,
            doctorFormDto.major,
            getOrganisationById(doctorFormDto.organisationId!!)
        )
        return ResponseEntity.ok(doctorRepository.save(doctor))
    }

    fun getDoctorById(doctorId: Long): ResponseEntity<Doctor> =
        doctorRepository.findById(doctorId).map { doctor ->
            ResponseEntity.ok(doctor)
        }.orElse(ResponseEntity.notFound().build())

    fun getDoctorsByOrganisationId(organisationId: Long): List<Doctor> {
        val organisation = getOrganisationById(organisationId)
        return doctorRepository.findByOrganisation(organisation)
    }

    private fun getOrganisationById(organisationId: Long) = organisationRepository.findById(organisationId)
        .orElseThrow { NotFoundException("Couldn't found Organisation with id: $organisationId") }

    fun updateDoctor(doctorId: Long, newDoctor: DoctorFormDto): ResponseEntity<Doctor> =
        doctorRepository.findById(doctorId).map { currentDoctor ->
            val updatedDoctor: Doctor =
                currentDoctor
                    .copy(
                        surname = newDoctor.surname,
                        major = newDoctor.major
                    )
            ResponseEntity.ok().body(doctorRepository.save(updatedDoctor))
        }.orElse(ResponseEntity.notFound().build())

    fun deleteDoctor(doctorId: Long): ResponseEntity<Void> =
        doctorRepository.findById(doctorId).map { doctor ->
            doctorRepository.delete(doctor)
            ResponseEntity<Void>(HttpStatus.ACCEPTED)
        }.orElse(ResponseEntity.notFound().build())
}