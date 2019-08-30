package com.qhc.steigenberger.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qhc.steigenberger.domain.Operations;
import com.qhc.steigenberger.domain.Role;
import com.qhc.steigenberger.service.RoleServiceI;
import com.qhc.steigenberger.service.WebServcieTool;
import com.qhc.steigenberger.util.CommonConstant;

@Service
public class RoleServiceImpl extends WebServcieTool<Role> implements RoleServiceI{
	
	static String BASEURL = "http://localhost:8801/frye/";

	public PageInfo<Role> selectAndPage(int pageNum, int pageSize, Role role) {
		PageHelper.startPage(pageNum, pageSize);
		String url = "";
//		String rolename = role.getName();
//		if(rolename!=null) {
//			url="role/findAll?name="+rolename;
//		}else {
			url="role/findAll";
//		}
		List<Role> list=findAll(CommonConstant.BASEURL, CommonConstant.URl_ROLE_FINDALL,Role.class);
		PageInfo<Role> pageInfo=new PageInfo<Role>(list);
		return pageInfo;
	}

	@Override
	public Role selectRoleInfo(int id) {
		
		return findOne(CommonConstant.BASEURL+CommonConstant.URl_ROLE_FINDBYID+"?id="+id,Role.class);
	}

	@Override
	public String saveRoleInfo(Role role) {
		
		return postInfo(role,CommonConstant.BASEURL+CommonConstant.URl_ROLE_ADD,Role.class);
	}
	
	@Override
	public String updateRoleInfo(Role role) {
		
		return postInfo(role,CommonConstant.BASEURL+CommonConstant.URl_ROLE_UPDATE,Role.class);
	}
	
	@Override
	public boolean remove(int id) {
		
		String str = delete(CommonConstant.BASEURL+CommonConstant.URl_ROLE_DELETE+"?id="+id);
		if("false".equals(str)) {
			return false;
		}else {
			return true;
		}
	}

	/**
	 * 不分页查询全部
	 */
	public List<Role> findAll() {
		
		String url ="operation/findAll";
		
		return  findAll(BASEURL, url,Operations.class);
	}
}

