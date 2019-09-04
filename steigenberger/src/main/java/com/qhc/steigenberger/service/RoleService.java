package com.qhc.steigenberger.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qhc.steigenberger.domain.Operations;
import com.qhc.steigenberger.domain.RestPageRole;
import com.qhc.steigenberger.domain.Role;
import com.qhc.steigenberger.util.CommonConstant;

@Service
public class RoleService{
	
	@Autowired
	OperationService operationService;
	
	@Autowired
	FryeService<Role> fryeService;
	
	@Autowired
	FryeService<RestPageRole> pageFryeService;
	
	private final static String URL_ROLES = "roles";
	private final static String URL_ROLES_PAGEABLELIST = "roles/paging";
	private final static String URL_ROLES_PERMESSION = "roles/permessions";
	
	public PageInfo<Role> selectAndPage(int pageNum, int pageSize, Role role) {
		PageHelper.startPage(pageNum, pageSize);
		List<Role> list=fryeService.getListInfo(URL_ROLES, Role.class);
		PageInfo<Role> pageInfo=new PageInfo<Role>(list);
		return pageInfo;
	}
	
	public RestPageRole getPageableList(int pageNum, int pageSize, Role role) {
		String url = URL_ROLES_PAGEABLELIST+"?pageNo="+pageNum+"&pageSize="+pageSize;
		
		return pageFryeService.getInfo(url, RestPageRole.class);
	}

	
	public Role selectRoleInfo(int id) {
		String url = URL_ROLES+"/"+id;
		return fryeService.getInfo(url, Role.class);
	}

	
	public Role saveRoleInfo(Role role) {
		return fryeService.postInfo(role,URL_ROLES, Role.class);
	}
	
	
	public Role updateRoleInfo(Role role) {
		
		return fryeService.postInfo(role,URL_ROLES, Role.class);
	}
	
	

	/**
	 * 不分页查询全部
	 */
	public List<Role> getListInfo() {
		
		return fryeService.getListInfo(URL_ROLES,Role.class);
	}

	
	public Map<String, Object> findInfos(int roleId) {
		Map<String, Object> map=new HashMap<>();
		Role role = selectRoleInfo(roleId);
		Set<Operations> operations = role.getOperations();
		List<Operations> operationsAll = operationService.getList();
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
	
	
	public Role updateRoleOperation(Role role) {
		
		return fryeService.postInfo(role, URL_ROLES_PERMESSION, Role.class);
	}
}

