package org.example.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

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
