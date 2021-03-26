package com.example.rsqtech.domain.patient.repository

import com.example.rsqtech.domain.organisation.model.Organisation
import com.example.rsqtech.domain.patient.model.Patient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PatientRepository : JpaRepository<Patient, Long> {
    fun findByOrganisation(organisation: Organisation): List<Patient>
}