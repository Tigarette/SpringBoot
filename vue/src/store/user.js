import $ from 'jquery'

export default ({
    state: {
        id: "",
        name: "",
        username: "",
        mail: "",
        token: "",
        admin: 2,
        grp: "",
        is_login: false,
        pulling_info: true,
        process: 0,
        time: 0,
        interval: [], // 摧毁计时器
        fromPage: "",
        date: "",
        photo: "",
        represent: "",
    },
    getters: {},
    mutations: {
        updateUser(state, user) {
            state.id = user.id;
            state.name = user.name;
            state.mail = user.mail;
            state.admin = user.admin;
            state.grp = user.grp;
            state.is_login = user.is_login;
            state.username = user.username;
            state.process = user.process;
            state.date = user.date;
            state.photo = user.photo;
            state.represent = user.represent;
        },
        updateToken(state, token) {
            state.token = token;
        },
        logout(state) {
            state.id = "";
            state.name = "";
            state.mail = "";
            state.token = "";
            state.admin = 2;
            state.grp = "";
            state.is_login = false;
            state.time = "";
            state.interval = 0;
            state.fromPage = "";
            state.process = "";
            state.photo = "";
            state.represent = "";
        },
        updatePullingInfo(state, pulling_info) {
            state.pulling_info = pulling_info;
        },
        updateTime(state, time) {
            state.time = time;
        },
        updateInterval(state, interval) {
            state.interval = interval;
        },
        updateFromPage(state, fromPage) {
            state.fromPage = fromPage;
        },
        updateName(state, name) {
            state.name = name;
        },
        updateRepresent(state, represent) {
            state.represent = represent;
        }
    },
    actions: {
        login(context, data) {
            $.ajax({
                type: 'post',
                url: 'http://localhost:3000/user/login/',
                data: {
                    "username": data.username,
                    "password": data.password,
                },
                success(resp) {
                    if (resp.error_message === 'success') {
                        localStorage.setItem("jwt_token", resp.jwt_token);
                        context.commit("updateToken", resp.jwt_token);
                        data.success(resp);
                    } else {
                        data.error(resp);
                    }
                },
                error(resp) {
                    data.error(resp);
                },
            })
        },
        getinfo(context, data) {
            $.ajax({
                url: "http://localhost:3000/user/info/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + context.state.token,
                },
                success(resp) {
                    console.log(resp);
                    if (resp.error_message === 'success') {
                        context.commit("updateUser", {
                            ...resp,
                            is_login: true,
                        });
                        data.success(resp);
                    } else {
                        data.error(resp);
                    }
                },
                error(resp) {
                    data.error(resp);
                },
            })
        },
        logout(context) {
            context.commit("logout");
            localStorage.removeItem("jwt_token");
        },
        SetHistory(context, data) {
            $.ajax({
                url: "http://localhost:3000/SetHistory/",
                type: "post",
                data: {
                    time: context.state.time,
                    path: data,
                },
                headers: {
                    Authorization: "Bearer " + context.state.token,
                },
                success(resp) {
                    console.log(resp);
                },
                error(resp) {
                    console.log(resp);
                }
            })
        },
        destoryInterval(context) {
            clearInterval(context.state.interval);
        }
    },
    modules: {}
})