package com.mss.billing.rep;

import com.mss.billing.entities.Compte;
import com.mss.billing.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CompteRepository extends JpaRepository<Compte,Long>
{



}
