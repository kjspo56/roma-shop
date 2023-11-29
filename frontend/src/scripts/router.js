import HomePage from "@/pages/HomePage.vue";
import LoginPage from "@/pages/LoginPage.vue";
import CartPage from "@/pages/CartPage.vue";
import {createRouter, createWebHistory} from "vue-router";
import OrderPage from "@/pages/OrderPage.vue";

const routes = [
    {path:'/', component:HomePage },
    {path:'/login', component:LoginPage },
    {path:'/cart', component:CartPage },
    {path:'/order', component: OrderPage }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router;