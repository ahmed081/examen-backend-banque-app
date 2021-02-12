package com.mss.billing.controller;

import com.mss.billing.entities.Compte;
import com.mss.billing.entities.Operation;
import com.mss.billing.service.CompteServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class serviceController {
    @Autowired
     CompteServiceImp compteServiceImp ;

    @PostMapping(path = "/retrait")
    Compte addRetrait(@RequestBody Operation o){
        return compteServiceImp.addRetrait(o);
    }
    @PostMapping(path = "/add-compte")
    Compte addCompte(@RequestBody Compte c){
        return compteServiceImp.addCompte(c);
    }

    @PostMapping(path = "/comptetocompte")
    void addRetrait(@RequestParam(name = "compte_debiteur") Long id_compteDebiteur ,
                    @RequestParam(name = "compte_crediteur") Long id_compteCrediteur,
                    @RequestParam(name = "montant") double montant
    ){
        compteServiceImp.addRetrait(id_compteDebiteur,id_compteCrediteur,montant);
    }


    @PostMapping(path = "/versement")
    public Compte addVersement(Operation o){
        return compteServiceImp.addVersement(o);
    }


    @GetMapping(path = "/getOpetration")
    Collection<Operation> getoperation(@RequestParam(name = "compte") Long compte_id){
        return compteServiceImp.getoperation(compte_id);
    }

    @GetMapping(path = "/consulter-compte")
    Compte consulterCompte(@RequestParam(name = "compte") Long compte_id){
        return compteServiceImp.consulterCompte(compte_id);
    }

    @PostMapping(path = "/toggle-compte")
    Compte toggleCompte(@RequestParam(name = "compte") Long compte_id){
        return compteServiceImp.toggleCompte(compte_id);
    }
}
