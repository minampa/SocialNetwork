package org.example.repository;

import org.example.model.ActivationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivationCodeRepository extends JpaRepository<ActivationModel, Integer> {
    ActivationModel findActivationCodeByUserId(int id);
    void deleteByUserId(int id);
    List<ActivationModel> findAll();

}
