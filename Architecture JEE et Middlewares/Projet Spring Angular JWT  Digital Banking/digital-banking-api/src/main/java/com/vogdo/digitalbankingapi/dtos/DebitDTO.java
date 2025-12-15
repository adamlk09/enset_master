package com.vogdo.digitalbankingapi.dtos;

import lombok.AllArgsConstructor; // <-- AJOUTER CET IMPORT
import lombok.Data;
import lombok.NoArgsConstructor; // <-- AJOUTER CET IMPORT (Bonne pratique)

@Data
@NoArgsConstructor // Ajoute un constructeur sans arguments
@AllArgsConstructor // Ajoute un constructeur avec TOUS les arguments
public class DebitDTO {
    private String accountId;
    private double amount;
    private String description;
}