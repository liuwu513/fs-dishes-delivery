import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const login = r => require.ensure([], () => r(require('@/page/sys/login')), 'login');
const manage = r => require.ensure([], () => r(require('@/page/sys/manage')), 'manage');
const home = r => require.ensure([], () => r(require('@/page/common/home')), 'home');
const addShop = r => require.ensure([], () => r(require('@/page/res/addShop')), 'addShop');
const addGoods = r => require.ensure([], () => r(require('@/page/res/addGoods')), 'addGoods');
const adminList = r => require.ensure([], () => r(require('@/page/sys/adminList')), 'adminList');
const userList = r => require.ensure([], () => r(require('@/page/sys/userList')), 'userList');
const shopList = r => require.ensure([], () => r(require('@/page/res/shopList')), 'shopList');
const foodList = r => require.ensure([], () => r(require('@/page/res/foodList')), 'foodList');
const orderList = r => require.ensure([], () => r(require('@/page/order/orderList')), 'orderList');
const uploadImg = r => require.ensure([], () => r(require('@/page/common/uploadImg')), 'uploadImg');
const adminSet = r => require.ensure([], () => r(require('@/page/sys/adminSet')), 'adminSet');

const routes = [
	{
		path: '/',
		component: login
	},
	{
		path: '/manage',
		component: manage,
		name: '',
		children: [{
			path: '',
			component: home,
			meta: [],
		},{
			path: '/adminList',
			component: adminList,
			meta: ['系统管理', '管理员列表'],
		},{
			path: '/shopList',
			component: shopList,
			meta: ['资源管理', '商家列表'],
		},{
			path: '/foodList',
			component: foodList,
			meta: ['资源管理', '食品列表'],
		},{
            path: '/addGoods',
            component: addGoods,
            meta: ['资源管理','食品列表', '添加食品'],
        },{
			path: '/orderList',
			component: orderList,
			meta: ['配送管理', '配送单列表'],
		},{
			path: '/uploadImg',
			component: uploadImg,
			meta: ['文本编辑', 'MarkDown'],
		},{
			path: '/adminSet',
			component: adminSet,
			meta: ['设置', '管理员设置'],
		}]
	}
]

export default new Router({
	routes,
	strict: process.env.NODE_ENV !== 'production',
})
