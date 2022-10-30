<template>
    <div class="row justify-content-md-center" style="width: 100%;">
        <div class="col-sm-4">
            <div class="card">
                <div class="card-header">
                    <h4 class="card-title text-center">注册</h4>
                </div>
                <form @submit.prevent="login">
                    <div class="card-body" id="model-body">
                        <p style="color: red">{{ error_message }}</p>
                        <div class="form-group" style="margin: 10px auto;">
                            <input v-model="username" type="text" class="form-control" placeholder="用户名"
                                autocomplete="off">
                        </div>
                        <div class="form-group" style="margin: 10px auto;">
                            <input v-model="name" type="text" class="form-control" placeholder="真实姓名"
                                autocomplete="off">
                        </div>
                        <div class="form-group" style="margin: 10px auto;">
                            <input v-model="password" type="password" class="form-control" placeholder="密码"
                                autocomplete="off">
                        </div>
                        <div class="form-group" style="margin: 10px auto;">
                            <input v-model="confirm_password" type="password" class="form-control" placeholder="确认密码"
                                autocomplete="off">
                        </div>
                        <div class="form-group" style="margin: 10px auto;">
                            <select class="form-select" aria-label="Default select example" v-model="group"
                                placeholder="选择你的年级部">
                                <option value="default">选择你的年级部</option>
                                <option value="20级">20级</option>
                                <option value="21级">21级</option>
                                <option value="22级">22级</option>
                            </select>
                        </div>
                        <div class="row">
                            <div class="col-8">
                                <div class="form-group" style="margin: 10px auto;">
                                    <input v-model="email" type="email" class="form-control" placeholder="邮箱"
                                        autocomplete="off">
                                </div>
                            </div>
                            <div class="col-4">
                                <button class="btn btn-secondary" style="width: 100%;margin: 10px auto;"
                                    @click="Sendcode">获取验证码</button>
                            </div>
                        </div>
                        <div class="form-group" style="margin: 10px auto;">
                            <input v-model="code" type="text" class="form-control" placeholder="验证码" autocomplete="off">
                        </div>
                    </div>
                    <div class="card-footer">
                        <input type="submit" class="btn btn-primary form-control" value="注册" @click="register">
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import router from '@/router';
import $ from 'jquery';
import { ref } from 'vue';


export default {

    setup() {
        let error_message = ref('');
        let email = ref('');
        let code = ref('');
        let username = ref('');
        let password = ref('');
        let name = ref('');
        let confirm_password = ref('');
        let group = ref('default');
        const Sendcode = () => {
            if (email.value === '') {
                error_message.value = "邮箱为空,发送验证码失败!";
            } else {
                $.ajax({
                    url: "http://localhost:3000/user/sendemail/",
                    type: "post",
                    xhrFields: { withCredentials: true },
                    crossDomain: true,
                    data: {
                        email: email.value,
                    },
                    success(resp) {
                        error_message.value = resp.error_message;
                    },
                    error(resp) {
                        console.log(resp);
                    }
                })
            }
        }

        const register = () => {
            $.ajax({
                url: "http://localhost:3000/user/register/",
                type: "post",
                xhrFields: { withCredentials: true },
                crossDomain: true,
                data: {
                    username: username.value,
                    name: name.value,
                    password: password.value,
                    conPassword: confirm_password.value,
                    email: email.value,
                    code: code.value,
                    group: group.value,
                },
                success(resp) {
                    if (resp.error_message != 'success') {
                        error_message.value = resp.error_message;
                    } else {
                        router.push({ name: 'user_login' });
                    }
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        return {
            error_message,
            email,
            code,
            username,
            name,
            password,
            confirm_password,
            group,
            Sendcode,
            register,
        }
    }
}

</script>

<style scoped>
.card {
    margin-top: 20%;
}
</style>