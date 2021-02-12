package com.mss.billing.service;


import com.mss.billing.entities.Compte;
import com.mss.billing.entities.Operation;
import com.mss.billing.fiegn.ClientRestClient;
import com.mss.billing.rep.CompteRepository;
import com.mss.billing.rep.OperationRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Date;

@RestController
@Transactional
public class CompteServiceImp implements ICompteService {
    final CompteRepository compteRepository;
    final OperationRepository operationRepository;
    final ClientRestClient clientRestClient;

    public CompteServiceImp(CompteRepository compteRepository,
                            OperationRepository operationRepository, ClientRestClient clientRestClient) {
        this.compteRepository = compteRepository;
        this.operationRepository = operationRepository;
        this.clientRestClient = clientRestClient;
    }

    @PostMapping(path = "/add-compte")
    Compte addCompte(Compte c){
        compteRepository.save(c);
        return c;
    }

    @PostMapping(path = "/versement")
    Compte addVersement(Operation o){
        o.setType("DEBIT");
        o.setDate(new Date());
        operationRepository.save(o);
        Compte c = compteRepository.findById(o.getCompte().getId()).get();
        c.setSolde(c.getSolde()+o.getMontant());
        compteRepository.save(c);
        return c;
    }
    @PostMapping(path = "/retrait")
    Compte addRetrait(Operation o){
        o.setType("CREDIT");
        o.setDate(new Date());
        operationRepository.save(o);
        Compte c = compteRepository.findById(o.getCompte().getId()).get();
        c.setSolde(c.getSolde()-o.getMontant());
        compteRepository.save(c);
        return c;
    }
    @PostMapping(path = "/comptetocompte")
    void addRetrait(@RequestParam(name = "compte_debiteur") Compte compteDebiteur ,
                      @RequestParam(name = "compte_crediteur") Compte compteCrediteur,
                      @RequestParam(name = "montant") double montant
                      ){
        Operation opCredit = new Operation(null,montant,new Date(),"DEBIT",compteDebiteur);
        Operation opDebit = new Operation(null,montant,new Date(),"CREDIT",compteCrediteur);
        compteDebiteur.setSolde(compteDebiteur.getSolde()+montant);
        compteCrediteur.setSolde(compteCrediteur.getSolde()-montant);
        operationRepository.save(opCredit);
        operationRepository.save(opDebit);
        compteRepository.save(compteDebiteur);
        compteRepository.save(compteCrediteur);
    }

    @GetMapping(path = "/getOpetration")
    Collection<Operation> addRetrait(@RequestParam(name = "compte") Long compte_id){
        Compte c  = compteRepository.findById(compte_id).get();
        return c.getOperations();
    }
    @GetMapping(path = "/consulter-compte")
    Compte consulterCompte(@RequestParam(name = "compte") Long compte_id){
        Compte c  = compteRepository.findById(compte_id).get();
        c.setClient(clientRestClient.getCustomerById(c.getId()));

        return c;

    }

    @PostMapping(path = "/toggle-compte")
    Compte toggleCompte(@RequestParam(name = "compte") Long compte_id){
        Compte c  = compteRepository.findById(compte_id).get();
        if(c.getEtat().equals("SUSPENDED"))
            c.setEtat("ACTIVE");
        else if(c.getEtat().equals("ACTIVE"))
            c.setEtat("SUSPENDED");
        return c;

    }
}
