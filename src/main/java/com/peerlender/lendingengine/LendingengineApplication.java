package com.peerlender.lendingengine;

import com.peerlender.lendingengine.domain.model.Balance;
import com.peerlender.lendingengine.domain.model.Currency;
import com.peerlender.lendingengine.domain.model.Money;
import com.peerlender.lendingengine.domain.model.User;
import com.peerlender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class LendingengineApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(LendingengineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User john=new User("john","john","doe",29,"swe", new Balance());
		User peter=new User("peter","peter","deckard",80,"veteran", new Balance());
		User kermit=new User("kermit","kermit","lepard",36,"gigolo", new Balance());
		john.topUp(new Money(Currency.USD, 100));
		peter.topUp(new Money(Currency.USD, 100));
		userRepository.save(john);
		userRepository.save(peter);
		userRepository.save(kermit);

	}
}
