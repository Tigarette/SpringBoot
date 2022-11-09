<template>
    <div class="row" style="margin-top:30px; width: 100%;">
        <div class="col-2"></div>
        <div class="col-8">
            <div class="card">
                <div class="card-header text-center">
                    <span>{{ $route.params.media_name }}</span>
                </div>
                <div class="card-body">
                    <videoPlay v-bind="options" @timeupdate="onTimeupdate" @play="onplay" @pause="onpause">
                        <h1>111</h1>
                    </videoPlay>
                </div>
            </div>
        </div>
    </div>
    <div class="col-2"></div>
</template>
<script>
import "video.js/dist/video-js.css";
import { reactive } from "vue";
import { useRoute } from "vue-router";
import "vue3-video-play/dist/style.css";
import { videoPlay } from "vue3-video-play";
import { ref } from "vue";
import $ from 'jquery'
import store from "@/store";

// js部分
export default {
    components: {
        videoPlay,
    },

    setup() {

        const route = useRoute();
        let interval = null;
        let time = ref(0);
        let path = "http://localhost:3000/media/video/" + route.params.dir_name + "/" + route.params.dir + "/" + route.params.media_name;
        let path1 = route.params.dir_name + "/" + route.params.dir + "/" + route.params.media_name;

        const options = reactive({
            width: "100%", //播放器高度
            height: "100", //播放器高度
            color: "#409eff", //主题色
            title: route.params.media_name, //视频名称
            src: path, //视频源
            muted: false, //静音
            webFullScreen: false,
            speedRate: ["0.5", "1.0", "1.25", "1.5", "2.0"], //播放倍速
            autoPlay: false, //自动播放
            loop: false, //循环播放
            mirror: false, //镜像画面
            ligthOff: false, //关灯模式
            volume: 1, //默认音量大小
            control: true, //是否显示控制
            controlBtns: [
                "audioTrack",
                "quality",
                "speedRate",
                "volume",
                "setting",
                "pip",
                "pageFullScreen",
                "fullScreen",
            ], //显示所有按钮,
        });

        const onTimeupdate = () => {
            time = document.getElementById('dPlayerVideoMain').currentTime;
            store.commit("updateTime", time);
        };

        const GetHistory = () => {
            $.ajax({
                url: "http://localhost:3000/GetHistory/",
                type: "post",
                data: {
                    path: path1,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    store.commit("updateTime", resp.time);
                    document.getElementById('dPlayerVideoMain').currentTime = resp.time;
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }
        GetHistory();

        const onplay = () => {
            interval = setInterval(function () {
                $.ajax({
                    url: "http://localhost:3000/user/addTime/",
                    type: "post",
                    headers: {
                        Authorization: "Bearer " + store.state.user.token,
                    },
                    success() {
                        store.commit("updateTime", time);
                        store.dispatch("SetHistory", path1);
                    },
                    error(resp) {
                        console.log(resp);
                    }
                })
            }, 5000);
            store.commit("updateInterval", interval);
        }

        const onpause = () => {
            clearInterval(interval);
        }

        return {
            options,
            onTimeupdate,
            onplay,
            onpause,
        }
    }
}


</script>