package com.example.rsqtech.domain.visit.model

import com.example.rsqtech.domain.patient.model.Patient
import com.example.rsqtech.domain.doctor.model.Doctor
import com.example.rsqtech.domain.organisation.model.Organisation
import com.fasterxml.jackson.annotation.JsonManagedReference
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Visit(
    @JsonManagedReference
    @ManyToOne
    val patient: Patient,

    @JsonManagedReference
    @ManyToOne
    val doctor: Doctor,

    var date: LocalDateTime,

    @ManyToOne val organisation: Organisation
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visit_generator")
    @SequenceGenerator(name="visit_generator", sequenceName = "visit_seq", allocationSize = 1)
    private val id: Long = 0L
}