package com.qhc.steigenberger.service;


	import com.github.pagehelper.PageInfo;
	import com.qhc.steigenberger.domain.User;


	public interface UserServiceI{
		/**
	         * 显示及分页
	     * @param pageNum
	     * @param pageSize
	     * @param user
	     * @return
	     */
		public PageInfo<User> selectAndPage(int number,int pageSize,User user);
		
		/**
		  *  通过id查询用户
		 * @param id
		 * @return
		 */
		public User selectUserInfo(int id);
		
		/**
		  * 保存保持用户信息
		 * @param user
		 * @return
		 */
		public String saveUserInfo(User user);

		/**
		  * 修改保持用户信息
		 * @param user
		 * @return
		 */
		String updateUserInfo(User user);
		
		/**
		  * 删除用户信息
		 * @param user
		 * @return
		 */
		boolean delete(int id);
		
		public User selectUserIdentity(String str);
}
