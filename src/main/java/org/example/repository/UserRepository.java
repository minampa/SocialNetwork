package org.example.repository;

import org.example.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Integer> {
    UserModel findUserModelById(int id);
    UserModel save(UserModel userModel);
    UserModel findUserModelByUsername(String username);
    UserModel findUserModelByPhoneNumber(String phoneNumber);
}
