package com.example.rsqtech.domain.organisation.repository

import com.example.rsqtech.domain.organisation.model.Organisation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrganisationRepository : JpaRepository<Organisation, Long> {
}