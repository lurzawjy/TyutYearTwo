import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/dept'
  },
  {
    path: '/emp',
    name: 'emp',
    component: () => import( '../views/element/EmpView.vue')
  },
  {
    path: '/dept',
    name: 'dept',
 
    component: () => import( '../views/element/DeptView.vue')
  },
  // {
  //   path: '/login',
  //   name: 'login',
 
  //   component: () => import( '../views/element/LoginView.vue')
  // },

]

const router = new VueRouter({
  routes
})

export default router
