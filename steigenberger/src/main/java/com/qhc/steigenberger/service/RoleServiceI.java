package com.qhc.steigenberger.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.qhc.steigenberger.domain.Role;


public interface RoleServiceI{
	/**
         * 显示及分页
     * @param pageNum
     * @param pageSize
     * @param role
     * @return
     */
	public PageInfo<Role> selectAndPage(int number,int pageSize,Role role);
	
	/**
	  *  通过id查询角色
	 * @param id
	 * @return
	 */
	public Role selectRoleInfo(int id);
	
	/**
	  * 保存保持角色信息
	 * @param role
	 * @return
	 */
	public String saveRoleInfo(Role role);

	/**
	  * 修改保持角色信息
	 * @param role
	 * @return
	 */
	String updateRoleInfo(Role role);
	
	/**
	  * 删除角色信息
	 * @param role
	 * @return
	 */
	boolean remove(int id);
	
	
	/**
	 * 不分页查询全部数据
	 * @return
	 */
	List<Role> findAll();

	public Map<String, Object> findInfos(int id);

	public String updateRoleOperation(Role role);
}