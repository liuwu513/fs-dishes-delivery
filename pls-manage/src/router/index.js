import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const login = r => require.ensure([], () => r(require('@/page/sys/login')), 'login');
const manage = r => require.ensure([], () => r(require('@/page/sys/manage')), 'manage');
const home = r => require.ensure([], () => r(require('@/page/common/home')), 'home');
const addShop = r => require.ensure([], () => r(require('@/page/res/addShop')), 'addShop');
const addGoods = r => require.ensure([], () => r(require('@/page/res/addGoods')), 'addGoods');
const adminList = r => require.ensure([], () => r(require('@/page/sys/adminList')), 'adminList');
const userList = r => require.ensure([], () => r(require('@/page/sys/userList')), 'userList');
const shopList = r => require.ensure([], () => r(require('@/page/res/shopList')), 'shopList');
const foodList = r => require.ensure([], () => r(require('@/page/res/foodList')), 'foodList');
const speciesList = r => require.ensure([], () => r(require('@/page/res/speciesList')), 'speciesList');
const customerList = r => require.ensure([], () => r(require('@/page/res/customerList')), 'customerList');
const orderList = r => require.ensure([], () => r(require('@/page/order/orderList')), 'orderList');
const subOrderList = r => require.ensure([], () => r(require('@/page/order/subOrderList')), 'subOrderList');
const addSubOrder = r => require.ensure([], () => r(require('@/page/order/addSubOrder')), 'addSubOrder');
const uploadImg = r => require.ensure([], () => r(require('@/page/common/uploadImg')), 'uploadImg');
const adminSet = r => require.ensure([], () => r(require('@/page/sys/adminSet')), 'adminSet');

const routes = [
	{
		path: '/',
		component: login,
        meta: {
		    auth:false
        }
	},
	{
		path: '/manage',
		component: manage,
		name: '',
        meta: {
            auth:true
        },
		children: [{
			path: '',
			component: home,
            meta: {
                auth:true,
                nabs: []
            }
		},{
			path: '/adminList',
			component: adminList,
            meta: {
                auth:true,
                nabs: ['系统管理', '管理员列表']
            }
		},{
			path: '/speciesList',
			component: speciesList,
            meta: {
                auth:true,
                nabs: ['资源管理', '食品分类']
            }
		},{
			path: '/foodList',
			component: foodList,
            meta: {
                auth:true,
                nabs: ['资源管理', '食品列表']
            }
		},{
            path: '/addGoods',
            component: addGoods,
            meta: {
                auth:true,
                nabs: ['资源管理','食品列表', '添加食品']
            }
        },{
            path: '/customerList',
            component: customerList,
            meta: {
                auth:true,
                nabs: ['资源管理','客户列表']
            }
        },{
			path: '/orderList',
			component: orderList,
            meta: {
                auth:true,
                nabs: ['配送管理', '配送单列表']
            }
		},{
            path: '/subOrderList',
            component: subOrderList,
            meta: {
                auth:true,
                nabs: ['配送管理', '配送单分单管理']
            }
        },{
            path: '/addSubOrder',
            component: addSubOrder,
            meta: {
                auth:true,
                nabs: ['配送管理', '添加配送分单']
            }
        }]
	}
]

const router = new VueRouter({
    routes: routes,
    strict: process.env.NODE_ENV !== 'production',
})

router.beforeEach((to, from, next) => {
    let token = window.localStorage.getItem('token')
    if (to.matched.some(record => record.meta.auth) && (!token || token === null)) {
        next({
            path: '/',
            query: { redirect: to.fullPath }
        })
    } else {
        next(()=>{
            window.location.reload()
        })
    }
})

export default router
