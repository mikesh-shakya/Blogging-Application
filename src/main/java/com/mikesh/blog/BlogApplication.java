package com.mikesh.blog;

import com.mikesh.blog.configuration.AppConstants;
import com.mikesh.blog.entities.Role;
import com.mikesh.blog.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}


	// This is used to convert one object into another class object...
	@Bean
	public ModelMapper modelMapper(){
		return  new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("Akhil@31"));

		try{
			Role userRole = new Role();
			userRole.setId(AppConstants.NORMAL_USER);
			userRole.setRoleName("ROLE_NORMAL");

			Role adminRole = new Role();
			adminRole.setId(AppConstants.ADMIN_USER);
			adminRole.setRoleName("ROLE_ADMIN");

			List<Role> roles = List.of(userRole, adminRole);
			List<Role> result = this.roleRepository.saveAll(roles);

			result.forEach(r->{
				System.out.println(r.getRoleName());
			});

		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
