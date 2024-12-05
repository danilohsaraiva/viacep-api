package com.studies.viacep;

import model.Address;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.ViaCepService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ConsumingViacepApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumingViacepApiApplication.class, args);
		ViaCepService service = new ViaCepService();
		try {
			Address endereco = service.getEndereco("38360000");
			System.out.println(endereco.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		try {
			List<Address> addresses = new ArrayList<>();
			addresses = service.findAddress("RS","Porto Alegre", "Domingos Jose");
			for (Address addres: addresses
				 ) {
				System.out.println(addres.toString());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
