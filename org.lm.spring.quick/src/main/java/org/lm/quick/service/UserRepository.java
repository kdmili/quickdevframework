package org.lm.quick.service;
 
import org.lm.quick.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

}
