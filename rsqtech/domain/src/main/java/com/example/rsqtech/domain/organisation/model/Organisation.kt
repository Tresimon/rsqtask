package com.example.rsqtech.domain.organisation.model

import javax.persistence.*

@Entity
data class Organisation(
    var name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organisation_generator")
    @SequenceGenerator(name="organisation_generator", sequenceName = "organisation_seq", allocationSize = 1)
    private val id: Long = 0L
}