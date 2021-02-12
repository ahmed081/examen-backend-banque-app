package com.mss.billing.service;

import com.mss.billing.entities.Compte;
import com.mss.billing.entities.Operation;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

public interface ICompteService {
    Compte addCompte(Compte c);
    public Compte addVersement(Operation o);
    public Compte addRetrait(Operation o);
    public void addRetrait(Long id_compteDebiteur ,Long id_compteCrediteur,double montan);
    public Collection<Operation> getoperation(Long compte_id);
    public Compte consulterCompte(Long compte_id);
    public Compte toggleCompte(Long compte_id);
}
