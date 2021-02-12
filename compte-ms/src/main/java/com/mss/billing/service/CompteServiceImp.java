package com.mss.billing.service;


import com.mss.billing.entities.Compte;
import com.mss.billing.entities.Operation;
import com.mss.billing.fiegn.ClientRestClient;
import com.mss.billing.rep.CompteRepository;
import com.mss.billing.rep.OperationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Date;

@Service
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
    @Override
    public Compte addCompte(Compte c){
        compteRepository.save(c);
        return c;
    }

    //@PostMapping(path = "/versement")
    @Override
    public Compte addVersement(Operation o){
        o.setType("DEBIT");
        o.setDate(new Date());
        operationRepository.save(o);
        Compte c = compteRepository.findById(o.getCompte().getId()).get();
        c.setSolde(c.getSolde()+o.getMontant());
        compteRepository.save(c);
        return c;
    }
    @Override
    public Compte addRetrait(Operation o){
        o.setType("CREDIT");
        o.setDate(new Date());
        operationRepository.save(o);
        Compte c = compteRepository.findById(o.getCompte().getId()).get();
        c.setSolde(c.getSolde()-o.getMontant());
        compteRepository.save(c);
        return c;
    }
    @Override
    public void addRetrait(Long id_compteDebiteur ,
                       Long id_compteCrediteur,
                       double montant
                      ){
        Compte compteDebiteur= compteRepository.findById(id_compteDebiteur).get();
        Compte compteCrediteur= compteRepository.findById(id_compteCrediteur).get();
        Operation opCredit = new Operation(null,montant,new Date(),"DEBIT",compteDebiteur);
        Operation opDebit = new Operation(null,montant,new Date(),"CREDIT",compteCrediteur);
        compteDebiteur.setSolde(compteDebiteur.getSolde()+montant);
        compteCrediteur.setSolde(compteCrediteur.getSolde()-montant);
        operationRepository.save(opCredit);
        operationRepository.save(opDebit);
        compteRepository.save(compteDebiteur);
        compteRepository.save(compteCrediteur);
    }

    @Override
    public Collection<Operation> getoperation( Long compte_id){
        Compte c  = compteRepository.findById(compte_id).get();
        return c.getOperations();
    }
    @Override
    public Compte consulterCompte(Long compte_id){
        Compte c  = compteRepository.findById(compte_id).get();
        c.setClient(clientRestClient.getCustomerById(c.getId()));

        return c;

    }

    @Override
    public Compte toggleCompte(Long compte_id){
        Compte c  = compteRepository.findById(compte_id).get();
        if(c.getEtat().equals("SUSPENDED"))
            c.setEtat("ACTIVE");
        else if(c.getEtat().equals("ACTIVE"))
            c.setEtat("SUSPENDED");
        return c;

    }
}
