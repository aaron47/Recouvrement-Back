package com.example.recouvrement.dto;


import com.example.recouvrement.models.helpers.Cycle;
import com.example.recouvrement.models.helpers.Type;
import lombok.Data;

@Data
public class ClientDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String societe;
    private String addresse;
    private String numeroClient;
    private String addresseDeFacturation;
    private Type type;
    private Cycle cycle;
}
