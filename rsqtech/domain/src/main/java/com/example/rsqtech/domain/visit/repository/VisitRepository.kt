package com.example.rsqtech.domain.visit.repository

import com.example.rsqtech.domain.organisation.model.Organisation
import com.example.rsqtech.domain.patient.model.Patient
import com.example.rsqtech.domain.visit.model.Visit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface VisitRepository : JpaRepository<Visit, Long> {
    fun findAllByPatient(findById: Optional<Patient>): List<Visit>
    fun findByDateAndDoctorId(Date: LocalDateTime, DoctorId: Long): Optional<Visit>
    fun findByOrganisation(organisation: Organisation): List<Visit>
}