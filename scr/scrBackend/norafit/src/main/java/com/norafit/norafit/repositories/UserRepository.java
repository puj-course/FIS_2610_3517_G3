package com.norafit.norafit.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.norafit.norafit.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);
}

//Estas interfaces ayudan a comunicarse con la BD porque extiende User que es la entidad y la llave Integer 
//con esto se puede usar : 
/*userRepository.save(user);
userRepository.findAll();
userRepository.findById(id);
userRepository.deleteById(id);*/
