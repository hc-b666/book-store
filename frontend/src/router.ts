import { createRouter, createWebHistory } from "vue-router";
import Home from "./pages/home.vue";
import About from "./pages/about.vue";
import NotFound from "./pages/not-found.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/about",
    name: "About",
    component: About,
  },
  {
    path: "/dynamic/:id",
    name: "DynamicRoute",
    component: () => import("./pages/DynamicRoute.vue"),
    props: true,
  },
  {
    path: "/:pathMatch(.*)*",
    name: "NotFound",
    component: NotFound,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
