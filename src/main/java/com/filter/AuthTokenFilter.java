//package com.filter;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.entity.UserEntity;
//import com.google.gson.Gson;
//import com.repository.UserRepository;
//
//@Component
//public class AuthTokenFilter implements Filter{
//	
//	@Autowired
//	UserRepository userRepo;
//	
//	@Override
//	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
//			throws IOException, ServletException {
//		
//		HttpServletRequest request = (HttpServletRequest)(req);
//		String token = request.getHeader("token");
//		
//		if(token == null || token.trim().length() != 16)
//		{
//			String userJsonString = new Gson().toJson(token);
//			
//			PrintWriter out = resp.getWriter();
//			resp.setContentType("application/json");
//			resp.setCharacterEncoding("UTF-8");
//			out.print(userJsonString);
//			out.flush();
//			
//		}
//		else
//		{
//			UserEntity userExist = userRepo.findByToken(token).orElse(null);
//			if(userExist == null) {
//				String userJsonString = new Gson().toJson("Invalid Access");
//				
//				PrintWriter out = resp.getWriter();
//				resp.setContentType("application/json");
//				resp.setCharacterEncoding("UTF-8");
//				out.print(userJsonString);
//				out.flush();
//				}
//			chain.doFilter(request,resp);
//		}
//	}
//
//}
