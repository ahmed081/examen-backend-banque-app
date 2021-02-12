package com.mss.billing.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Double solde;
    Date data_creation;
    String type;
    String etat;
    @OneToMany(mappedBy = "compte")
    private Collection<Operation> operations;
    private long clientId;
    @Transient
    Client client;

}
