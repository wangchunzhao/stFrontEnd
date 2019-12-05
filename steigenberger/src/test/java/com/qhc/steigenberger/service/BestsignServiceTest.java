package com.qhc.steigenberger.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

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
    final String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC1dJfnXe216cOBi9yY61iZXDouDgDMitwOM4gCUWPJ9KlXUsbOG0LnEt9jUxsBRMcvkfnFaVn2ZLC7KxieaIdnIIGw1rN2a0WFJIwBuFrH1yaUUfgMGNOUJjhiph0JLOhQroLntUh//WycHZy8Phw6tQ7S/5f5FKVQi6HCMtcHok70HwU72Axr+e56F4pVGAcaMp7YXjdOmpFPXx6j6GF8FYh2unXUYrBGRH/5rHqAo0Ag8A2zkL47aKCvkucfeZiu4tuehR3unrI4tdAm/CpntsJ2BYvmhRgqntBHK3CW6Gme88jjepbSEkhDjROzlK0QMJeqU+s5HUM1BtLFJtPtAgMBAAECggEACM6w9NT1Tcgb6jTMr0t1EHSOil+5oDP5PGM57crfihTrB0cISUa/d5HN7/c/r08UT/XI5tEXQcNfZKZR2pZ+Q/4q7VdufIf2ZuEAPrEhDuQdhkN4Q7YMxvsX74najPB2Ejx2NCLzcurtE6LIUca9/gd9wbYQPVHIOGGep1tqXn9oF4/QeuFsFZK9GTS5TLGCekREW5gH57Eo2DHSiDlKTgCqjir6+yF2mRPWGGiaLKtDIBsOM2qpxI3C8yjgD5DKy3SrZ/vBe9kBeJ6VZzJqNGYmC7A/FQhdTMD9mshZUPQD3g9z6kmLkxBs0Q3nSGN/j/QBkcuGCal5gO0na3BcbQKBgQD+/GGVBCsCNax/K7LL+MZsLDOrKW1jsWdO8X49Ep/CnF1Exuz38dvQiW+hFo2K3gRB5ZkENWWy+HL+Z0rnwSI2jbRttMxN9q9Sdb8dRG1TazZtLIsOvz1oMF9VZ5PXn68To3oKNVIm1TGh7TdhqMS0VXPvpBiRLsWHX28Xf/6tTwKBgQC2LViECiIjUlKxONb7P68bT1Bs+YMs9329ESEf2ohloW6pql4ZMro9nJr+QPt6mUAgUyWZWWaVLuC/54+a5Y5SGDYBYFVN3iSLVIsttjq1R/YJVD0u6BGo92hqEmCfimQqHaci5WjAGD1zRpVoI9vOSAKhR7u6MkyIstqUrG10AwKBgEK10Onlr0LivACBdEO9EFyYq+Pp8L6WWUrkD3z29Gk784Lc8H5l/nZuno/skJd2QnLjGMdrGPJb4eoBKC2976+KH1xcYt863N+cAqYrktayRAkIEFGJYw1xKl/zu1A8bNece39UN+wE9vlAUK7yMpRjjvNxYSQKso8aPrxNNlotAoGAU7FpZN+y5z8+tiRCv5J2Q7mgXTATz2iz31QrP5MJ7obHbDLUoAbqALwdiIkZ/yzAhRktwNGNiyPKJN+g3axwQc7VoLQ8/FT9vPTOK+X3+qhgo9CLey0qT9G5qmFe+mx9r8uHqURzZyy7rmXS5dDzfkUe0DFAUT4iYvqn6H1+mzkCgYBUGred7m5xXs96xaHHVM5Y2mqpLszHndSMqDMMKUKIWSFBuOsGIqHfua/f+ZWHX4IbvveG5sEtYdBy4xReZRrW/yYcHL+/dIkP4DwE9unlwFnpKQ8F7Ynv7sWx+nilI0RuveSqt3M6HwgpQwaE9WCbhMvr6Gai9yASJuDQYwSttQ==";

    private final BestSignClient bestSignClient = new BestSignClient(
            "https://api.bestsign.info",
            "l2ZxwSA6OBs6IgsQ6og9f7ixuPMR7hMQ",
            "3seTeUqUyhGlYZfD3CbGVXPDrcu1ieG4",
            privateKey
    );

	@Autowired
	private BestsignService bestsignService;

	@Test
	void testDownloadContract() {
		bestsignService.downloadContract("1234");
	}

	@Test
	void testDoSignContract() throws JsonMappingException, JsonProcessingException {
        SignContractVO signContractVO = new SignContractVO();
        signContractVO.setSealName("测试 是是");
        List<Long> contractIds = Arrays.asList(2042451531952816137L,2042451531952816136L,2042451531952816135L);
        signContractVO.setContractIds(contractIds);
        String string = bestSignClient.executeRequest("/api/contracts/sign","POST",signContractVO);

        System.out.println( string);
		bestsignService.doSignContract("1234");
	}

	@Test
	void testSyncContractSignSysData() throws JsonMappingException, JsonProcessingException {
		List<ContractSignSys> list = bestsignService.syncContractSignSysData();
		System.out.println(list);
	}

	@Test
	void testGetContractStatus() throws JsonMappingException, JsonProcessingException {
		bestsignService.getContractStatus("12", "");
	}

	@Test
	void testGenerateShA1Code() {
		fail("Not yet implemented");
	}

	@Test
	void testGetContractInfo() throws JsonMappingException, JsonProcessingException {
		bestsignService.getContractInfo("1234");
	}

	@Test
	void testContainContractFileSha() {
		fail("Not yet implemented");
	}

}
