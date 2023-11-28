import HomePage from "@/pages/HomePage.vue";
import LoginPage from "@/pages/LoginPage.vue";
import CartPage from "@/pages/CartPage.vue";
import {createRouter, createWebHistory} from "vue-router";

const routes = [
    {path:'/', component:HomePage },
    {path:'/login', component:LoginPage },
    {path:'/cart', component:CartPage },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router;