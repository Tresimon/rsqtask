package com.example.rsqtech.domain.organisation.controller

import com.example.rsqtech.domain.organisation.model.Organisation
import com.example.rsqtech.domain.organisation.service.OrganisationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/organisations")
class OrganisationController(private val organisationService: OrganisationService) {

    @GetMapping
    fun getOrganisations(): List<Organisation> =
        organisationService.getOrganisations()

    @GetMapping("/{id}")
    fun getOrganisationById(@PathVariable("id") organisationId: Long): ResponseEntity<Organisation> =
        organisationService.getOrganisationById(organisationId)

    @PostMapping
    fun createOrganisation(@Valid @RequestBody organisation: Organisation): ResponseEntity<Organisation> =
        organisationService.createOrganisation(organisation)

    @DeleteMapping("/{id}")
    fun deleteOrganisation(@PathVariable("id") organisationId: Long): ResponseEntity<Void> =
        organisationService.deleteOrganisation(organisationId)
}