import Vue from 'vue';
import Router from 'vue-router';

/**
 * Config the router for url navigation.
 *
 * @author Yingtong Zhou
 */

const originalReplace = Router.prototype.replace;
Router.prototype.replace = function replace(location) {
    return originalReplace.call(this, location).catch((err) => err);
};
const originalPush = Router.prototype.push;
Router.prototype.push = function push(location) {
    return originalPush.call(this, location).catch((err) => err);
};

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/index'
        },
        {
            path: '/index',
            /*Component Lazy Loading*/
            component: () => import('../components/page/index.vue'),
            meta: { title: 'Campus Exchange' }
        },
        {
            path: '/search',
            component: () => import('../components/page/search.vue'),
            meta: { title: 'Search Item | Campus Exchange' }
        },
        {
            path: '/me',
            component: () => import('../components/page/me.vue'),
            meta: { title: 'Dashboard | Campus Exchange' }
        },
        {
            path: '/message',
            component: () => import('../components/page/message.vue'),
            meta: { title: 'Message | Campus Exchange' }
        },
        {
            path: '/release',
            component: () => import('../components/page/release.vue'),
            meta: { title: 'Release Item | Campus Exchange' }
        },
        {
            path: '/details',
            component: () => import('../components/page/idle-details.vue'),
            meta: { title: 'Item Details | Campus Exchange' }
        },
        {
            path: '/order',
            component: () => import('../components/page/order.vue'),
            meta: { title: 'Order Details | Campus Exchange' }
        },
        {
            path: '/login',
            component: () => import('../components/page/login.vue'),
            meta: { title: 'Login | Campus Exchange' }
        },
        {
            path: '/register',
            component: () => import('../components/page/sign-in.vue'),
            meta: { title: 'Register | Campus Exchange' }
        },
        {
            path: '/login-admin',
            component: () => import('../components/page/login-admin.vue'),
            meta: { title: 'Login Admin | Campus Exchange' }
        },
        {
            path: '/platform-admin',
            component: () => import('../components/page/platform-admin.vue'),
            meta: { title: 'Platform Admin' }
        },
        {
            path: '*',
            redirect: '/'
        }
    ]
});
