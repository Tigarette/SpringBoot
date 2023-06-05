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
import UserForgetPasswordView from '@/views/user/UserForgetPasswordView.vue'
import MessageView from '@/views/message/MessageView.vue'

import store from '@/store'

const routes = [{
        path: '/',
        name: "home",
        redirect: "/home/",
        meta: {
            requestAuth: true,
            title: "拒收病婿",
        }
    },
    {
        path: "/home/",
        name: "home_index",
        component: HomeView,
        meta: {
            requestAuth: true,
            title: "拒收病婿",
        }
    },
    {
        path: '/user/login/',
        name: "user_login",
        component: UserLoginView,
        meta: {
            requestAuth: false,
            title: "安全登录",
        }
    },
    {
        path: '/user/register/',
        name: "user_register",
        component: UserRegisterView,
        meta: {
            requestAuth: false,
            title: "安全注册",
        }
    },
    {
        path: '/user/info/',
        name: "user_info_index",
        component: UserInfoView,
        meta: {
            requestAuth: true,
            title: "我的主页",
        }
    },
    {
        path: '/user/forget/',
        name: "user_change_index",
        component: UserForgetPasswordView,
        meta: {
            requestAuth: false,
            title: "忘记密码",
        }
    },
    {
        path: "/media/",
        name: "media_index",
        component: MediaListView,
        meta: {
            requestAuth: true,
            title: "挑选种类ing",
        }
    },
    {
        path: "/media/:dir_name/",
        name: "media_in",
        component: ChoiceMediaView,
        meta: {
            requestAuth: true,
            title: "挑选视频ing",
        }
    },
    {
        path: "/media/:dir_name/:dir/:media_name/",
        name: "media",
        component: MediaView,
        meta: {
            requestAuth: true,
            title: "观看视频",
        }
    },
    {
        path: "/download/:dir_name/:dir/:media_name/",
        name: "download",
        component: DownLoadView,
        meta: {
            requestAuth: true,
            title: "安全下载",
        }
    },
    {
        path: "/ranklist/",
        name: "ranklist_index",
        component: RankListView,
        meta: {
            requestAuth: true,
            title: "卷王争霸",
        }
    },
    {
        path: "/message/",
        name: "message_index",
        component: MessageView,
        meta: {
            requestAuth: true,
            title: "金口玉言",
        }
    },
    {
        path: "/404/",
        name: "404",
        component: NotFoundView,
        meta: {
            requestAuth: false,
            title: "页面被风吹跑啦",
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
    if (to.meta.title) {
        document.title = to.meta.title
    }
    if (to.meta.requestAuth && !store.state.user.is_login) {
        next({ name: "user_login" });
        store.commit("updateFromPage", to.path);
    } else {
        next();
    }
    if (from.name === 'media') {
        let path = from.params.dir_name + "/" + from.params.dir + "/" + from.params.media_name;
        store.dispatch("SetHistory", path);
        store.dispatch("destoryInterval");
    }
    if (to.name === 'media' && store.state.user.time !== 0) {
        let path = from.params.dir_name + "/" + from.params.dir + "/" + from.params.media_name;
        store.dispatch("SetHistory", path);
        store.dispatch("destoryInterval");
    }
})

export default router