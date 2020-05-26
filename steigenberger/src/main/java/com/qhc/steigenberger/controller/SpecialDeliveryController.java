package com.qhc.steigenberger.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qhc.steigenberger.domain.Operations;
import com.qhc.steigenberger.domain.Result;
import com.qhc.steigenberger.domain.SpecialDelivery;
import com.qhc.steigenberger.domain.SpecialDeliveryQuery;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.service.SpecialDeliveryService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("specialdelivery")
public class SpecialDeliveryController extends BaseController {
  static private Logger logger = LoggerFactory.getLogger(SpecialDeliveryController.class);

  @Autowired
  SpecialDeliveryService specialDeliveryService;

  String allorder = "全部";
  String self = "自己";
  String benqu = "大区";

  @GetMapping("")
  @ResponseBody
  public Result getOrderListPage(SpecialDeliveryQuery query) {
    // 获取页面时间段的查询条件
    String createTime = query.getCreateTime();
    if (createTime != null && !"".equals(createTime)) {
      String startTime = createTime.substring(0, 10);
      String endTime = createTime.substring(createTime.length() - 10);
      query.setStartTime(startTime);
      query.setEndTime(endTime);
    }
//    User user = getAccount();
//    List<Operations> userOperationInfoList = super.getPermissions();
//
//    for (int i = 0; i < userOperationInfoList.size(); i++) {
//      String operationName = userOperationInfoList.get(i).getName();
//      // String operationName = "全部";
//      if (operationName.equals(allorder)) {
//        break;
//      } else if (operationName.equals(benqu)) {
//        query.setOfficeCode(user.getOfficeCode());
//        break;
//      } else {
//        query.setSalesCode(user.getUserIdentity());
//      }
//    }

    Result result = null;
    try {
      // 查询当前页实体对象
      try {
        Map params = new ObjectMapper().convertValue(query, Map.class);
        params.put("list", "list");
        result = specialDeliveryService.findSpecialDelivery(params);
      } catch (Throwable e) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      result = Result.error(e.getMessage());
    }
    return result;
  }

  @GetMapping("/applyids/{orderInfoId}")
  @ResponseBody
  public Result getApplyListOfOrder(
      @PathVariable(name = "orderInfoId", required = true) Integer orderInfoId) {
    Result result = null;
    List<Integer> ids = new ArrayList<Integer>();
    // 查询当前页实体对象
    try {
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("pageNo", 1);
      params.put("pageSize", 1000);
      params.put("orderInfoId", orderInfoId);
      result = specialDeliveryService.findSpecialDelivery(params);
      Map data = (Map) result.getData();
      if (result.getStatus().equals("ok")) {
        Object rows = data.get("rows");
        if (rows != null) {
          List list = (List) rows;
          for (Object row : list) {
            Object id = ((Map) row).get("id");
            if (id != null && id.toString().trim().length() > 0) {
              ids.add(Integer.parseInt(id.toString()));
            }
          }
        }
        result.setData(ids);
      }
    } catch (Exception e) {
      result = Result.error(e.getMessage());
    }
    return result;
  }

  @ApiOperation(value = "根据申请ID查询 ", notes = "根据申请ID查询")
  @GetMapping("{applyId}")
  @ResponseStatus(HttpStatus.OK)
  public Result findById(@PathVariable Integer applyId) throws Exception {
    Result result = null;
    try {
      result = specialDeliveryService.findSpecialDeliveryById(applyId);
    } catch (Throwable e) {
      e.printStackTrace();
      result = Result.error(e.getMessage());
    }
    return result;
  }

  @PostMapping("")
  @ResponseBody
  public Result save(@RequestBody SpecialDelivery specialDelivery) {
    Result result = null;
    try {
      String username = super.getUserIdentity();
      result = specialDeliveryService.saveSpecialDelivery(username, specialDelivery);
    } catch (Throwable e) {
      e.printStackTrace();
      result = Result.error(e.getMessage());
    }
    return result;
  }

  @PostMapping("submit")
  @ResponseBody
  public Result submit(@RequestBody SpecialDelivery specialDelivery) {
    Result result = null;
    try {
      String username = super.getUserIdentity();
      result = specialDeliveryService.submit(username, specialDelivery);
    } catch (Throwable e) {
      logger.error("提交失败", e);
      result = Result.error(e.getMessage());
    }
    return result;
  }
}
