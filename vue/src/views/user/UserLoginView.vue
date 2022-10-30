<template>
    <div class="row justify-content-md-center" v-if="!$store.state.user.pulling_info" style="width: 100%;">
        <div class="col-sm-4">
            <div class="card">
                <div class="card-header">
                    <h4 class="card-title text-center">登录</h4>
                </div>
                <form @submit.prevent="login">
                    <div class="card-body" id="model-body">
                        <p style="color: red">{{ error_msg }}</p>
                        <div class="form-group" style="margin: 10px auto;">
                            <input v-model="username" type="text" class="form-control" placeholder="用户名"
                                autocomplete="off" id="username">
                        </div>
                        <div class="form-group" style="margin: 10px auto;">
                            <input v-model="password" type="password" class="form-control" placeholder="密码"
                                autocomplete="off" id="password">
                        </div>
                    </div>
                    <div class="card-footer">
                        <input type="submit" class="btn btn-primary form-control" value="登录">
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import { useStore } from 'vuex'
import { ref } from 'vue'
import router from '@/router/index'
export default {
    components: {
    },
    setup() {
        const store = useStore();
        let username = ref('');
        let password = ref('');
        let error_msg = ref('');
        const jwt_token = localStorage.getItem("jwt_token");
        if (jwt_token) {
            store.commit("updateToken", jwt_token);
            store.dispatch("getinfo", {
                success() {
                    router.push({ name: 'home' });
                    store.commit("updatePullingInfo", false);
                },
                error() {
                    store.commit("updatePullingInfo", false);
                },
            })
        } else {
            store.commit("updatePullingInfo", false);
        }

        const login = () => {
            store.dispatch("login", {
                username: username.value,
                password: password.value,
                success() {
                    store.dispatch("getinfo", {
                        success() {
                            router.push({ name: 'home' });
                        },
                        error() {
                        }
                    })
                },
                error(resp) {
                    if (resp.error_message === 'password') {
                        error_msg.value = "密码不得为空!";
                    } else if (resp.error_message === 'username') {
                        error_msg.value = "用户名不得为空!";
                    } else {
                        error_msg.value = "用户名或密码错误";
                    }
                }
            })
        }

        return {
            username,
            password,
            error_msg,
            login,
        }

    }
}
</script>

<style scoped>
input {
    width: 100%;
}

.card {
    margin-top: 40%;
}
</style>