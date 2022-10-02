package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class PostModel {
    @Id
    private int id;
    private String description;
    private Date creationDate;




}
