import { createRouter, createWebHistory } from 'vue-router'
import UserLoginView from '@/views/user/UserLoginView.vue';
import UserRegisterView from '@/views/user/UserRegisterView.vue';
import UserInfoView from '@/views/user/UserInfoView.vue';
import HomeView from '@/views/home/HomeView.vue';
import MediaView from '@/views/media/MediaView.vue';
import RankListView from '@/views/ranklist/RankListView.vue';
import NotFoundView from '@/views/404/NotFoundView.vue';
import MediaListView from '@/views/media/MediaListView.vue';
import ChoiceMediaView from '@/views/media/ChoiceMediaView.vue';
import DownLoadView from '@/views/media/DownLoadView.vue';

import store from '@/store'


const routes = [{
        path: '/',
        name: "home",
        redirect: "/home/",
        meta: {
            requestAuth: true,
        }
    },
    {
        path: "/home/",
        name: "home_index",
        component: HomeView,
        meta: {
            requestAuth: true,
        }
    },
    {
        path: '/user/login/',
        name: "user_login",
        component: UserLoginView,
        meta: {
            requestAuth: false,
        }
    },
    {
        path: '/user/register/',
        name: "user_register",
        component: UserRegisterView,
        meta: {
            requestAuth: false,
        }
    },
    {
        path: '/user/info/',
        name: "user_info_index",
        component: UserInfoView,
        meta: {
            requestAuth: true,
        }
    },
    {
        path: "/media/",
        name: "media_index",
        component: MediaListView,
        meta: {
            requestAuth: true,
        }
    },
    {
        path: "/media/:dir_name/",
        name: "media_in",
        component: ChoiceMediaView,
        meta: {
            requestAuth: true,
        }
    },
    {
        path: "/media/:dir_name/:dir/:media_name/",
        name: "media",
        component: MediaView,
        meta: {
            requestAuth: true,
        }
    },
    {
        path: "/download/:dir_name/:dir/:media_name/",
        name: "download",
        component: DownLoadView,
        meta: {
            requestAuth: true,
        }
    },
    {
        path: "/ranklist/",
        name: "ranklist_index",
        component: RankListView,
        meta: {
            requestAuth: true,
        }
    },
    {
        path: "/404/",
        name: "404",
        component: NotFoundView,
        meta: {
            requestAuth: false,
        }
    },
    {
        path: "/:catchAll(.*)",
        redirect: "/404/",
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    if (to.meta.requestAuth && !store.state.user.is_login) {
        next({ name: "user_login" });
    } else {
        next();
    }
    if (from.name === 'media') {
        let path = from.params.dir_name + "/" + from.params.dir + "/" + from.params.media_name;
        store.dispatch("SetHistory", path);
    }
})

export default router