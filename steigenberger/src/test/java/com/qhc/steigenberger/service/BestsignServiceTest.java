package com.qhc.steigenberger.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.qhc.steigenberger.domain.ContractSignSys;

import cn.bestsign.ultimate.delta.api.client.BestSignClient;
import cn.bestsign.ultimate.delta.api.domain.contract.sign.SignContractVO;

@SpringBootTest
class BestsignServiceTest {

	@Autowired
	private BestsignService bestsignService;

	@Test
	void testDownloadContract() {
		byte[] bytes = bestsignService.downloadContract("2202086693413060617");
		System.out.println(new String(Hex.encodeHex(bytes)));
	}

	@Test
	void testDoSignContract() throws JsonMappingException, JsonProcessingException  {
        boolean r = bestsignService.doSignContract("2202086693413060617");
        System.out.println(r);
	}

	@Test
	void testSyncContractSignSysData() throws JsonMappingException, JsonProcessingException  {
		List<ContractSignSys> list = bestsignService.syncContractSignSysData();
		System.out.println(list);
	}

	@Test
	void testGenerateShA1Code()  {
		String hash = bestsignService.generateShA1Code(new File("C:\\Users\\zsu4\\Downloads\\123-1(1-1-2).pdf"));
		System.out.println(hash);
	}

	@Test
	void testGetContractStatus()  {
		String status = bestsignService.getContractStatus("2202086693413060617", "青岛海尔开利冷冻设备有限公司");
		System.out.println(status);
	}

	@Test
	void testGetContractInfo()  {
		Map map = bestsignService.getContractInfo("2202086693413060617");
		System.out.println(map);
	}

	@Test
	void testContainContractFileSha() {
		boolean contains = bestsignService.containContractFileSha("2202086693413060617", "fdd15f177282429c01b699fc0b991a97286041c9");
		System.out.println(contains);
	}

}
