package com.mss.billing.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Double montant;
    Date date;
    String type;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    Compte compte;


}
