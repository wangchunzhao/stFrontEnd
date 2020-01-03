package com.qhc.steigenberger.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qhc.steigenberger.domain.Operations;
import com.qhc.steigenberger.domain.RestPage;
import com.qhc.steigenberger.domain.Role;

@Service
public class RoleService{
	
	@Autowired
	OperationService operationService;
	
	@Autowired
	FryeService fryeService;
	
	@Autowired
	FryeService pageFryeService;
	
	private final static String URL_ROLE = "role";
	private final static String URL_ROLE_PERMESSION = "role/permessions";

	public Role addRole(Role role) {
		return fryeService.postInfo(role, URL_ROLE, Role.class);
	}
	
	public PageInfo<Role> selectAndPage(int pageNum, int pageSize, Role role) {
		PageHelper.startPage(pageNum, pageSize);
		List<Role> list=(List)fryeService.getListInfo(URL_ROLE, Role.class);
		PageInfo<Role> pageInfo=new PageInfo<Role>(list);
		return pageInfo;
	}
	
	public RestPage getPageableList(int pageNum, int pageSize, Role role) {
		String url = URL_ROLE+"/"+pageNum+"/"+pageSize+"?isActive="+role.getIsActive();
		
		return pageFryeService.getInfo(url, RestPage.class);
	}

	public RestPage getPageableListByName(int pageNum, int pageSize, Role role) {
		String url = URL_ROLE+"/searchByName/" + role.getName();
		RestPage df = pageFryeService.getInfo(url, RestPage.class);
		return pageFryeService.getInfo(url, RestPage.class);
	}

	
	public Role selectRoleInfo(int id) {
		String url = URL_ROLE+"/"+id;
		return (Role)fryeService.getInfo(url, Role.class);
	}

	
	public Role saveRoleInfo(int id) {
		Role role = selectRoleInfo(id);
		int isActive=role.getIsActive();
		if(isActive == 1) {
			isActive = 0;
		}else {
			isActive= 1;
		}
		role.setIsActive(isActive);
		return fryeService.postInfo(role, URL_ROLE, Role.class);
	}
	
	
	public Role updateRoleInfo(Role role) {
		
		return fryeService.postInfo(role, URL_ROLE, Role.class);
	}
	
	

	/**
	 * 不分页查询全部
	 */
	public List<Role> getListInfo(Role role) {
		String url = URL_ROLE+"?isActive="+role.getIsActive();
		return fryeService.getListInfo(url,Role.class);
	}

	
	public Map<String, Object> findInfos(int roleId) {
		Map<String, Object> map=new HashMap<>();
		Role role = selectRoleInfo(roleId);
		List<Operations> operations = role.getOperations();
		List<Operations> operationsAll = operationService.getList();
		if(operations!=null&&operations.size()>0) {
			for(Operations operation:operationsAll) {
				for(Operations op: operations) {
					if(op.getId().equals(operation.getId())) {
						operation.setSelected(true);
					}
				}
			}
		}

		map.put("roleId", roleId);				
		map.put("role", role);				
		map.put("operationsAll", operationsAll);
		return map;
	}
	
	
	public Role updateRoleOperation(Role role) {
		
		return fryeService.putInfo(role, URL_ROLE, Role.class);
	}

	public Role deleteRole(Role role) {
		return fryeService.postInfo(role, "deleteRole", Role.class);
	}
}

