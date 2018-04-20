import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const login = resolve => require(['@/page/sys/login'], resolve)
const manage = resolve => require(['@/page/sys/manage'], resolve)
const home = resolve => require(['@/page/common/home'], resolve)
const addShop = resolve => require(['@/page/res/addShop'], resolve)
const addGoods = resolve => require(['@/page/res/addGoods'], resolve)
const adminList = resolve => require(['@/page/sys/adminList'], resolve)
const userList = resolve => require(['@/page/sys/userList'], resolve)
const shopList =  resolve => require(['@/page/res/shopList'], resolve);
const foodList =  resolve => require(['@/page/res/foodList'], resolve);
const speciesList = resolve => require(['@/page/res/speciesList'], resolve)
const customerList = resolve => require(['@/page/res/customerList'], resolve)
const orderList = resolve => require(['@/page/order/orderList'], resolve)
const subOrderList = resolve => require(['@/page/order/subOrderList'], resolve)
const addSubOrder = resolve => require(['@/page/order/addSubOrder'], resolve)
const uploadImg = resolve => require(['@/page/common/uploadImg'], resolve)
const adminSet = resolve => require(['@/page/sys/adminSet'], resolve)

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
            // component:import('@/page/res/foodList'),
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
    mode:'history',
    routes: routes
    // strict: process.env.NODE_ENV !== 'production',
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
