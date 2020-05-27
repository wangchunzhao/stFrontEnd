package com.qhc.steigenberger.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qhc.steigenberger.domain.Result;
import com.qhc.steigenberger.domain.SpecialDelivery;
import com.qhc.steigenberger.domain.SpecialDeliveryQuery;

@Service
public class SpecialDeliveryService {

	@Autowired
	FryeService fryeService;

	public Result saveSpecialDelivery(String username, SpecialDelivery specialDelivery) {
		return fryeService.postInfo(specialDelivery, "specialdelivery/" + username, Result.class);
	}

	public Result findSpecialDeliveryById(Integer applyId) {
		return fryeService.getInfo("specialdelivery/" + applyId, Result.class);
	}

	public Result findSpecialDelivery(Map sdv) {
	    StringBuilder url = new StringBuilder(128);
	    for (Object key : sdv.keySet()) {
	        Object value = sdv.get(key);
	        url.append("&").append(key).append("=").append(value == null ? "" : value.toString());
        }
	    String strUrl = "specialdelivery?" + url.substring(1);
		return fryeService.getInfo(strUrl, Result.class);
	}
	
  private String trimToEmpty(Object str) {
    if (str == null) {
      return "";
    }
    return str.toString().trim();
  }

	public Result submit(String username, SpecialDelivery specialDelivery) {
		return fryeService.postInfo(specialDelivery, "specialdelivery/submit/" + username, Result.class);
	}

}
