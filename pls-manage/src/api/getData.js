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
