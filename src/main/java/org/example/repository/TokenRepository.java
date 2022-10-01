package org.example.repository;

import antlr.Token;
import org.example.model.TokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<TokenModel, Integer> {
    TokenModel save(TokenModel tokenModel);
    void deleteByUserId(int id);
    List<TokenModel> findAll();
    TokenModel findByUserId(int id);
}
