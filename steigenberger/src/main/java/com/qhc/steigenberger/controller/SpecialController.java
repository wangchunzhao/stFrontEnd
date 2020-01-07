package com.qhc.steigenberger.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.qhc.steigenberger.Constants;
import com.qhc.steigenberger.domain.JsonResult;
import com.qhc.steigenberger.domain.SpecialDelivery;
import com.qhc.steigenberger.domain.SpecialDeliveryVoInfo;
import com.qhc.steigenberger.domain.User;
import com.qhc.steigenberger.domain.UserOperationInfo;
import com.qhc.steigenberger.service.OrderService;
import com.qhc.steigenberger.service.SpecialDeliveryService;
import com.qhc.steigenberger.service.SpecialDeliveryVoInfoService;
import com.qhc.steigenberger.service.UserOperationInfoService;
import com.qhc.steigenberger.service.UserService;
import com.qhc.steigenberger.util.FileUploadAndDown;
import com.qhc.steigenberger.util.PageHelper;

@RestController
@RequestMapping("special")
public class SpecialController extends BaseController {

	@Autowired
	SpecialDeliveryService specialDeliveryService;

	@Autowired
	SpecialDeliveryVoInfoService specialDeliveryVoInfoService;

	@Autowired
	UserService userService;

	@Autowired
	OrderService orderService;

	@Autowired
	UserOperationInfoService userOperationInfoService;

	String allorder = "全部";
	String self = "自己";
	String benqu = "大区";

	public static String fileName;

	@RequestMapping("/listData")
	@ResponseBody
	public PageHelper<SpecialDeliveryVoInfo> getOrderListPage(SpecialDeliveryVoInfo sdv, HttpServletRequest request) {
		PageHelper<SpecialDeliveryVoInfo> pageHelper = null;
		// 获取页面时间段的查询条件
		String createTime = request.getParameter("createTime1");
		if (createTime != null && !"".equals(createTime)) {
			String startTime = createTime.substring(0, 10);
			String endTime = createTime.substring(createTime.length() - 10);
			sdv.setStartTime(startTime);
			sdv.setEndTime(endTime);
		}

		try {
			String identityName = getUserIdentity();
			User user = getAccount();
			List<UserOperationInfo> userOperationInfoList = userOperationInfoService.findByUserId(user.id);

			for (int i = 0; i < userOperationInfoList.size(); i++) {
				String operationName = userOperationInfoList.get(i).getOperationName();
//				String operationName = "全部";
				if (operationName.equals(allorder)) {
					break;
				} else if (operationName.equals(benqu)) {
					sdv.setOfficeCode(userOperationInfoList.get(i).getAttachedCode());
					break;
				} else {
					sdv.setOwnerDomainId(user.id + "");
				}
			}
			// 查询当前页实体对象
			pageHelper = specialDeliveryVoInfoService.getListForSpecial(sdv.getPage() - 1, sdv.getLimit(), sdv);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return pageHelper;
	}

	@PostMapping("/add")
	@ResponseBody
	public JsonResult add(@RequestBody SpecialDelivery specialDelivery, HttpServletRequest request) {

		// 1.没有就是新增操作
		// 2.如果存在，就是更新操作
		String msg = "";
		int status = 0;
		SpecialDelivery result = specialDeliveryService.saveSpecialDelivery(specialDelivery);
		if (result != null) {
			status = 200;
			msg = "操作成功！";
		} else {
			status = 500;
			msg = "操作失败";
		}
		return JsonResult.build(status, "申请" + msg, "");

	}

	@RequestMapping("/queryApplyListByOrderId")
	@ResponseBody
	public JsonResult queryApplyListByOrderId(HttpServletRequest request) {

		String kOrderVersionId = request.getParameter("kOrderVersionId");
		// 1.没有就是新增操作
		// 2.如果存在，就是更新操作
		String msg = "";
		int status = 0;
		List<SpecialDelivery> result = specialDeliveryService.findListInfoByOrderId(Integer.valueOf(kOrderVersionId));
		if (result != null) {
			status = 200;
			msg = "操作成功！";
		} else {
			status = 500;
			msg = "操作失败";
		}
		return JsonResult.build(status, "申请" + msg, result);

	}

	@RequestMapping("/toAdd")
	public ModelAndView toAdd(@RequestParam String orderInfoId) {

//		Order order = orderService.findOrderDetail(orderInfoId);
		ModelAndView mv = new ModelAndView();
//		mv.addObject("order", order);
//		mv.addObject("kOrderVersionId", kOrderVersionId);
//		mv.setViewName("special/specialApply");
		return mv;
	}

	@RequestMapping("/toShow")
	public ModelAndView toShow(@RequestParam String applyId) {
//		SpecialDelivery sd = specialDeliveryService.findInfoById(applyId);
//		Order order = orderService.getInfoById(sd.getkOrderVersionId());
		// todo，查询合同得到合同流水

		ModelAndView mv = new ModelAndView();
//		mv.addObject("order", order);
//		mv.addObject("apply", sd);
//		mv.setViewName("special/showApply");
		return mv;
	}

	@RequestMapping("/saveApply")
	@ResponseBody
	public JsonResult saveApply(@RequestBody SpecialDelivery specialDelivery, HttpServletRequest request) {

		specialDelivery.setApplyer((String) request.getSession().getAttribute(Constants.IDENTITY));
		specialDelivery.setApplyId(0);
		specialDelivery.setApplyStatus(1);
		specialDelivery.setApplyTime(new Date());
		// System.out.println(fileName+"------------------------");
		Long dateLong = (new Date()).getTime();
		specialDelivery.setEnclosureName(dateLong + "_" + fileName);

		// 1.没有就是新增操作
		// 2.如果存在，就是更新操作
		String msg = "";
		int status = 0;
		SpecialDelivery result = specialDeliveryService.saveSpecialDelivery(specialDelivery);
		if (result != null) {
			status = 200;
			msg = "操作成功！";
		} else {
			status = 500;
			msg = "操作失败";
		}
		return JsonResult.build(status, "申请" + msg, result);

	}

	@RequestMapping(value = "/upload")
	@ResponseBody
	private JsonResult importExcel(@RequestParam(value = "myupload", required = false) MultipartFile file,
			HttpServletRequest request) {
		try {
			MultipartRequest multipartRequest = (MultipartRequest) request;
			MultipartFile myfile = multipartRequest.getFile("myupload");
			if (myfile != null) {
				fileName = myfile.getOriginalFilename();
//	        	 System.out.println(fileName);

				FileUploadAndDown fud = new FileUploadAndDown();
				JsonResult result = fud.upload(file, request.getSession());
				if (result.isOK()) {
					return JsonResult.build(200, "success", 1);
				}
			} else {
				return new JsonResult(false);
			}
		} catch (Exception e) {
			return new JsonResult(false);
		}
		return new JsonResult(false);
	}
}
