package com.example.rsqtech.domain.doctor.model

import com.example.rsqtech.domain.organisation.model.Organisation
import com.example.rsqtech.domain.visit.model.Visit
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class Doctor(
    var name: String,
    var surname: String,
    var major: String,
    @ManyToOne val organisation: Organisation
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_generator")
    @SequenceGenerator(name="doctor_generator", sequenceName = "doctor_seq", allocationSize = 1)
    private val id: Long = 0L

    @JsonIgnore
    @OneToMany(mappedBy = "doctor", cascade = [CascadeType.REMOVE])
    val visits: List<Visit> = listOf()
}
