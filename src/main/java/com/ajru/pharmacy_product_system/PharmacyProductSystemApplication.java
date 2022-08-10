package com.ajru.pharmacy_product_system;

import com.ajru.pharmacy_product_system.auth.ConfigProps;
import com.ajru.pharmacy_product_system.auth.model.User;
import com.ajru.pharmacy_product_system.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@EnableWebSecurity
@EnableConfigurationProperties(ConfigProps.class)
public class PharmacyProductSystemApplication {
//	@Value("${jna.userName}")
//	private String userName;
//	@Value("${jna.password}")
//	private String password;
//	@Value("${jna.role}")
//	private String role;
//	@Value("${jna.id}")
//	private int id;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@PostConstruct
//	public void initUser() {
//		List<User> users = Stream.of(
//				new User(id, userName, password, "", role)
//		).collect(Collectors.toList());
//		userRepository.saveAll(users);
//	}

	public static void main(String[] args) {
		SpringApplication.run(PharmacyProductSystemApplication.class, args);
	}

}
