<template>
    <div class="row">
        <div class="col-2"></div>
        <div class="col-8">
            <div class="alert alert-primary" role="alert">
                若在10秒内未开始下载请点击<button class="btn-primary btn" @click="download">这里</button>
            </div>
            <div class="alert alert-danger" role="alert">
                目前暂不支持avi视频,见谅
            </div>
        </div>
        <div class="col-2"></div>
    </div>
</template>

<script>
import { useRoute } from 'vue-router';
import { useStore } from 'vuex';
import $ from 'jquery'
import router from '@/router';

export default {

    setup() {
        const route = useRoute();
        const store = useStore();

        const download = () => {
            let path = "http://localhost:3000/download/" + route.params.dir_name + "/" + route.params.dir + "/" + route.params.media_name;
            const vueObj = this;
            const url = path;
            const xhr = new XMLHttpRequest();
            xhr.open('get', url, true);
            xhr.setRequestHeader('Content-Type', 'application/octet-stream');
            xhr.setRequestHeader('Authorization', "Bearer " + store.state.user.token);
            xhr.responseType = 'blob';
            // 请求的回调
            xhr.onload = function () {
                if (this.status === 200) {
                    const reader = new FileReader();
                    reader.readAsDataURL(this.response);
                    reader.onload = function (e) {
                        const a = document.createElement('a');
                        a.download = route.params.media_name;
                        a.href = e.target.result;
                        document.documentElement.appendChild(a);
                        a.click();
                        a.remove();
                    };
                } else {
                    vueObj.$message.error('您还未登录！');
                }
            };
            // 发送请求
            xhr.send();
        }
        $.ajax({
            url: "http://localhost:3000/check/allPath/",
            type: "post",
            data: {
                "dir_name": route.params.dir_name,
                "dir": route.params.dir,
                "media_name": route.params.media_name,
            },
            headers: {
                Authorization: "Bearer " + store.state.user.token,
            },
            success(resp) {
                if (resp.error_message === 'fail') {
                    router.push({ name: "404" });
                } else if (resp.error_message === 'success') {
                    download();
                }
            },
            error() {
                router.push({ name: "404" });
            }
        })

        return {
            download,
        }
    }
}
</script>