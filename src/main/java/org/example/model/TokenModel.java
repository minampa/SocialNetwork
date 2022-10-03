package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class TokenModel {
    private String token;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;




}
