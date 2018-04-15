import fetch from '@/config/fetch'

/**
 * 登陆
 */

export const login = data => fetch('/api/sys/login', data, 'POST');

/**
 * 退出
 */

export const signout = () => fetch('/api/admin/singout');


/**
 * 获取系统用户列表
 */
export const getUserList = data => fetch('/api/sys/user/list', data);

/**
 * 获取用户信息
 */

export const getAdminInfo = () => fetch('/api/sys/user/info',{},'POST');


/**
 * 管理员列表
 */

export const adminList = data => fetch('/api/sys/user/list', data, 'POST');

/**
 * 获取食品列表
 */

export const getFoods = data => fetch('/api/food/list', data, 'POST');

/**
 * 添加食品
 */

export const addFood = data => fetch('/api/food/save', data, 'POST');

/**
 * 获取食品种类menu列表
 */
export const getSpecies = data => fetch('/api/species/listMenu',{},'POST');

/**
 * 获取分类分页列表
 * @param data
 */
export const getSpeciesList = data => fetch('/api/species/list',data,'POST');

/**
 * 删除食品分类信息
 * @param data
 */
export const deleteSpecies = data => fetch('/api/species/delete',data,'POST');

/**
 * 添加或者更新食品种类
 */
export const addSpecies = data => fetch('/api/species/save', data, 'POST');


/**
 * 客户分页查询
 * @param data
 */
export const listCustomers = data => fetch('/api/customer/list', data, 'POST');

/**
 * 客户数据
 * @param data
 */
export const getCustomers = data => fetch('/api/customer/listMenu', data, 'POST');

/**
 * 删除客户信息
 * @param data
 */
export const deleteCustomers = data => fetch('/api/customer/delete', data, 'POST');

/**
 * 添加或者更新客户
 * @param data
 */
export const addCustomer = data => fetch('/api/customer/save', data, 'POST');


/**
 * 主单分页查询
 * @param data
 */
export const listMainOrder = data => fetch('/api/order/main/list', data, 'POST');

/**
 * 删除主单信息
 * @param data
 */
export const deleteMainOrders = data => fetch('/api/order/main/delete', data, 'POST');

/**
 * 添加或者更新主单信息
 * @param data
 */
export const addMainOrder = data => fetch('/api/order/main/save', data, 'POST');


/**
 * 子单分页查询
 * @param data
 */
export const listSubOrder = data => fetch('/api/order/sub/list', data, 'POST');

/**
 * 获取子订单详情
 * @param data
 */
export const getSubOrder = data => fetch('/api/order/sub/info', data, 'POST');

/**
 * 删除子单信息
 * @param data
 */
export const deleteSubOrders = data => fetch('/api/order/sub/delete', data, 'POST');

/**
 * 添加或者更新子单信息
 * @param data
 */
export const addSubOrder = data => fetch('/api/order/sub/save', data, 'POST');


/**
 * 添加商铺
 */

export const addShop = data => fetch('/shopping/addShop', data, 'POST');

/**
 * 更新餐馆信息
 */

export const updateResturant = data => fetch('/shopping/updateshop', data, 'POST');

/**
 * 删除餐馆
 */

export const deleteResturant = restaurant_id => fetch('/shopping/restaurant/' + restaurant_id, {}, 'DELETE');







/**
 * 删除食品
 */

export const deleteFood = data => fetch('/api/food/delete',data, 'POST');


/**
 * 获取用户数量
 */

export const getUserCount = data => fetch('/v1/users/count', data);

/**
 * 获取订单列表
 */

export const getOrderList = data => fetch('/bos/orders', data);

/**
 * 获取订单数量
 */

export const getOrderCount = data => fetch('/bos/orders/count', data);

/**
 * 获取用户信息
 */

export const getUserInfo = user_id => fetch('/v1/user/' + user_id);

/**
 * 获取地址信息
 */

export const getAddressById = address_id => fetch('/v1/addresse/' + address_id);

/**
 * 获取用户分布信息
 */

export const getUserCity = () => fetch('/v1/user/city/count');
