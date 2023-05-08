package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.TokenGenerator;
import com.bean.CustomResponse;
import com.entity.UserEntity;
import com.repository.UserRepository;

@CrossOrigin
@RestController
public class SessionController {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	TokenGenerator tokenGenerator;
	
	
	@PostMapping("/signup")
	public ResponseEntity<CustomResponse<UserEntity>> signUp(@RequestBody UserEntity userEntity) {
		 UserEntity email = userRepo.findByEmail(userEntity.getEmail());
		 if(email==null) {
			 userRepo.save(userEntity);
			 CustomResponse<UserEntity> resp = new CustomResponse<>();
			 
			 resp.setData(userEntity);
			 resp.setMsg("User added Successfully!");
			 return ResponseEntity.ok(resp);
		 }
		 else {
			 CustomResponse<UserEntity> resp = new CustomResponse<>();
			 resp.setData(userEntity);
			 resp.setMsg("User Already Exists!");
			 
			 return ResponseEntity.unprocessableEntity().body(resp);
		 }
	}
	
  
	@PostMapping("/login")
	public ResponseEntity<CustomResponse<UserEntity>> login(@RequestBody UserEntity userEntity)
	{
		UserEntity emailpass = userRepo.findByEmailAndPwd(userEntity.getEmail(), userEntity.getPwd());
		
		CustomResponse<UserEntity> resp = new CustomResponse<>();
		
		if(emailpass == null)
		{
			resp.setMsg("Invalid Credentials !");
			return ResponseEntity.unprocessableEntity().body(resp);
		}
		else 
		{
			
			String token = tokenGenerator.generateToken(16);
			
			emailpass.setToken(token);
			userRepo.save(emailpass);
			
			resp.setData(emailpass);
		    resp.setMsg("Login Successfull!");
			return ResponseEntity.ok(resp);
			
		}
	}
	
	@GetMapping("/forgotPassword")
	public ResponseEntity<CustomResponse<UserEntity>> forgotPasseord(@RequestParam("email") String email){
		UserEntity tempUserByEmail = userRepo.findByEmail(email);
		CustomResponse<UserEntity> usr = new CustomResponse<>();
		if(tempUserByEmail == null)
		{
			usr.setData(null);
			usr.setMsg("Otp will share to your Email, if email is exists !!");
			return ResponseEntity.ok(usr);
		}
		else
		{
			Integer min = 100000;
			Integer max = 999999;
			Integer temp = (int) (Math.random() * (max - min + 1) + min)  ;
			System.out.println("-----------------"+temp+"-----------------");
			tempUserByEmail.setOtp(temp);
			userRepo.save(tempUserByEmail);
			usr.setData(tempUserByEmail);
			usr.setMsg("Otp will share to your Email, if email is exists (OTP set)!!");
			return ResponseEntity.ok(usr);
		}
	}
	
	
	@GetMapping("/ResetPassword")
	public ResponseEntity<CustomResponse<UserEntity>> resetPassword(@RequestParam("otp") Integer otp, @RequestParam("password") String password, @RequestParam("confirmpassword") String confirmpassword)
	{
		UserEntity user = userRepo.findByOtp(otp);
			if((password.equals(confirmpassword)) && !(user.getPwd().equals(password)))
			{
				CustomResponse<UserEntity> usr = new CustomResponse<>();
				user.setPwd(confirmpassword);
				userRepo.save(user);
				usr.setData(user);
				usr.setMsg("Password sucessfully reset !!");
				return ResponseEntity.ok(usr);
			}
			else
			{
				CustomResponse<UserEntity> usr = new CustomResponse<>();
				usr.setData(null);
				usr.setMsg("Please enter password and conformpassword same !!");
				return ResponseEntity.ok(usr);
			}
		}
	
}
