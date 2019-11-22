package com.qhc.steigenberger.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ContractServiceTest {
	
	@Autowired
	ContractService contractService;

	@Test
	void testExportToPDF() {
		File f = null;
		try {
			System.out.println(System.getProperty("java.library.path"));
			f = contractService.exportToPDF(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(f);
	}

}
