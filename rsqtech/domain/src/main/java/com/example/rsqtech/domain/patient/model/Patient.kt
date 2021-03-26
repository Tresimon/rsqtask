package com.example.rsqtech.domain.patient.model

import com.example.rsqtech.domain.organisation.model.Organisation
import com.example.rsqtech.domain.visit.model.Visit
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class Patient(
    var name: String,
    var surname: String,
    var address: String,
    @ManyToOne val organisation: Organisation
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_generator")
    @SequenceGenerator(name="patient_generator", sequenceName = "patient_seq", allocationSize = 1)
    private val id: Long = 0L

    @JsonIgnore
    @OneToMany(mappedBy = "patient", cascade = [CascadeType.REMOVE])
    val visits: List<Visit> = listOf()
}
