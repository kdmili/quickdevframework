package org.lm.jpa.service;

import org.lm.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
