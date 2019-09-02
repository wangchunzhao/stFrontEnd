package com.qhc.steigenberger.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qhc.steigenberger.domain.Operations;
import com.qhc.steigenberger.domain.Role;
import com.qhc.steigenberger.service.OperationServiceI;
import com.qhc.steigenberger.service.RoleServiceI;
import com.qhc.steigenberger.service.WebServcieTool;
import com.qhc.steigenberger.util.CommonConstant;

@Service
public class RoleServiceImpl extends WebServcieTool<Role> implements RoleServiceI{
	
	@Autowired
	OperationServiceI operationServiceImpl;

	
	public PageInfo<Role> selectAndPage(int pageNum, int pageSize, Role role) {
		PageHelper.startPage(pageNum, pageSize);
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
		String result = null;
		try {
			result = postInfo(role,CommonConstant.BASEURL+CommonConstant.URl_ROLE_ADD,Role.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public String updateRoleInfo(Role role) {
		
		String result = null;
		try {
			result = postInfo(role,CommonConstant.BASEURL+CommonConstant.URl_ROLE_UPDATE,Role.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public boolean remove(int id) {
		
		String result = null;
		try {
			result = delete(CommonConstant.BASEURL+CommonConstant.URl_ROLE_DELETE+"?id="+id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(result!=null&&"false".equals(result)) {
			return false;
		}else {
			return true;
		}
	}

	/**
	 * 不分页查询全部
	 */
	public List<Role> findAll() {
		
		return  findAll(CommonConstant.BASEURL, CommonConstant.URl_ROLE_FINDALL,Role.class);
	}

	@Override
	public Map<String, Object> findInfos(int roleId) {
		Map<String, Object> map=new HashMap<>();
		Role role = findOne(CommonConstant.BASEURL+CommonConstant.URl_ROLE_FINDBYID+"/"+roleId, Role.class);
		Set<Operations> operations = role.getOperations();
		List<Operations> operationsAll = operationServiceImpl.findAll();
		if(!operations.isEmpty()) {
			for(Operations operation:operationsAll) {
				for(Operations op: operations) {
					if(op.getId().equals(operation.getId())) {
						operation.setSelected(true);
					}
				}
			}
		}

		map.put("roleId", roleId);				
		map.put("operationsAll", operationsAll);
		return map;
	}
	
	
	public String updateRoleOperation(Role role) {
		
		String result = null;
		try {
			result = postInfo(role,CommonConstant.BASEURL+CommonConstant.URl_ROLE_UPDATE_OPERATIONS,Role.class);
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}
		return result;
	}
}

