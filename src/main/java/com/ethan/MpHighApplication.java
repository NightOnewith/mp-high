package com.ethan;

import com.ethan.utils.TenantContext;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.ethan.dao")
public class MpHighApplication {

	public static void main(String[] args) {
		SpringApplication.run(MpHighApplication.class, args);
	}

	@Bean
	public TenantContext tenantContext() {
		return new TenantContext();
	}

}
