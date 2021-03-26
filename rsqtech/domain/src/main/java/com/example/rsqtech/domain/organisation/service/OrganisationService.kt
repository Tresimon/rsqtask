package com.example.rsqtech.domain.organisation.service

import com.example.rsqtech.domain.organisation.model.Organisation
import com.example.rsqtech.domain.organisation.repository.OrganisationRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class OrganisationService (private val organisationRepository: OrganisationRepository) {

    fun getOrganisations(): List<Organisation> =
        organisationRepository.findAll()

    fun createOrganisation(organisation: Organisation): ResponseEntity<Organisation> =
        ResponseEntity.ok(organisationRepository.save(organisation))

    fun getOrganisationById(organisationId: Long): ResponseEntity<Organisation> =
        organisationRepository.findById(organisationId).map { organisation ->
            ResponseEntity.ok(organisation)
        }.orElse(ResponseEntity.notFound().build())
    

    fun deleteOrganisation(organisationId: Long): ResponseEntity<Void> =
        organisationRepository.findById(organisationId).map { organisation ->
            organisationRepository.delete(organisation)
            ResponseEntity<Void>(HttpStatus.ACCEPTED)
        }.orElse(ResponseEntity.notFound().build())
}