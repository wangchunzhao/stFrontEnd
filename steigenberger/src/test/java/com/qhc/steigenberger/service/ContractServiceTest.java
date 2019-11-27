package com.qhc.steigenberger.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

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

	@Test
	void testDoDownloadFromSignSystem() {
		fail("Not yet implemented");
	}

	@Test
	void testSendMailToCustomer() {
		fail("Not yet implemented");
	}

	@Test
	void testDoRefreshContractState() throws JsonMappingException, JsonProcessingException {
		this.contractService.doRefreshContractState();
	}

	@Test
	void testDoSignContract() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateSignId() {
		this.contractService.updateSignId(1, "abcde", 2);
	}

	@Test
	void testUpdateFileHashCode() {
		this.contractService.updateFileHashCode(1, "34423jdlfk89sdf");
	}

	@Test
	void testUpdateStatus() {
		this.contractService.updateStatus(1, 3);
	}

}
