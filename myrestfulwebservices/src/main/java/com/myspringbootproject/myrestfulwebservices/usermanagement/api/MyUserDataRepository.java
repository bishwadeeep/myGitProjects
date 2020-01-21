package com.myspringbootproject.myrestfulwebservices.usermanagement.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserDataRepository extends JpaRepository<MyUsers, Integer>{

}
