package org.example.model;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ActivationModel {
    @Id
    private int userId;
    private String activationCode;
}
