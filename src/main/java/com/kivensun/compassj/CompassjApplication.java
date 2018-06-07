package com.kivensun.compassj;

import com.kivensun.compassj.system.JwtAuthenticationFilter;
import com.kivensun.compassj.util.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static com.kivensun.compassj.util.JwtUtil.USER_NAME;

@SpringBootApplication
@RestController
public class CompassjApplication {

	@GetMapping("/api/protected")
	public @ResponseBody Object hellWorld(@RequestParam(value = USER_NAME) String userId) {
		return "Your user id is '" + userId + "'" ;
	}

	@PostMapping("/login")
	public Object login(HttpServletResponse response, @RequestBody final Account account) throws IOException {
		System.out.println(account.username);
		if(isValidPassword(account)) {
			String jwt = JwtUtil.generateToken(account.username);
			return new HashMap<String,String>(){{
				put("token", jwt);
			}};
		}else {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
	}

	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
		registrationBean.setFilter(filter);
		return registrationBean;
	}

	private boolean isValidPassword(Account credentials) {
		return "admin".equals(credentials.username)
				&& "admin".equals(credentials.password);
	}


	public static class Account {
		public String username;
		public String password;
	}


	public static void main(String[] args) {
		SpringApplication.run(CompassjApplication.class, args);
	}
}
