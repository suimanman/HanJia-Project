import { createRouter, createWebHistory } from "vue-router";
const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: "/",
            name: "welcome",
            component: () => import("@/views/WelcomeView.vue"),
            children: [
                {
                    path: "",
                    name: "welcome-login",
                    component: () => import("@/views/welcome/LoginPage.vue"),
                },
            ],
        },
    ],
});

export default router;
