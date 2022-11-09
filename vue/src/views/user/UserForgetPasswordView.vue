<template>
    <div class="row justify-content-md-center" style="width: 100%;">
        <div class="col-sm-4">
            <div class="card">
                <div class="card-header">
                    <h4 class="card-title text-center">忘记密码</h4>
                </div>
                <form @submit.prevent="login">
                    <div class="card-body" id="model-body">
                        <p style="color: red">{{ error_message }}</p>
                        <div class="form-group" style="margin: 10px auto;">
                            <input v-model="username" type="text" class="form-control" placeholder="用户名"
                                autocomplete="off">
                        </div>
                        <div class="form-group" style="margin: 10px auto;">
                            <input v-model="password" type="password" class="form-control" placeholder="新密码"
                                autocomplete="off">
                        </div>
                        <div class="form-group" style="margin: 10px auto;">
                            <input v-model="confirm_password" type="password" class="form-control" placeholder="确认密码"
                                autocomplete="off">
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
                        <input type="submit" class="btn btn-primary form-control" value="提交" @click="forget">
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
        let confirm_password = ref('');
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

        const forget = () => {
            $.ajax({
                url: "http://localhost:3000/user/forget/",
                type: "post",
                xhrFields: { withCredentials: true },
                crossDomain: true,
                data: {
                    username: username.value,
                    password: password.value,
                    confirm: confirm_password.value,
                    email: email.value,
                    code: code.value,
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
            password,
            confirm_password,
            Sendcode,
            forget,
        }
    }
}
</script>

<style scoped>
.card {
    margin-top: 20%;
}
</style>