package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

	List<UserEntity> findAll();
	UserEntity findByEmail(String email);
	UserEntity findByEmailAndPwd(String email,String pwd);
	UserEntity findByOtp(Integer otp);
	Optional<UserEntity> findByToken(String token);
}
