<template>
    <div class="container main-header">
        <div class="row" style="margin-top: 20px">
            <!-- 3 份 -->
            <div class="col-3">
                <div class="card">
                    <div class="card-body">
                        <img :src="$store.state.user.photo" alt="" style="width:100%; display: inline;">
                        <ul style="display: inline;">
                            <li>用户名：{{ " " + $store.state.user.name }}</li>
                            <li>年级部: {{ $store.state.user.grp }}</li>
                            <li>有效时长: {{ formatSeconds($store.state.user.process) }}</li>
                            <li>创建时间: {{ $store.state.user.date }}</li>
                        </ul>
                    </div>
                </div>
                <div class="card" style="margin-top: 10px;">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-6">
                                <button type="button" class="btn btn-outline-danger float-start" data-bs-toggle="modal"
                                    data-bs-target="#ChangePassword">
                                    修改密码
                                </button>
                            </div>
                            <div class="col-6">
                                <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal"
                                    data-bs-target="#Moreinfo">更多信息</button>
                            </div>
                        </div>
                        <div class="row" style="margin-top: 10px;">
                            <div class="col-6">
                                <button type="button" class="btn btn-outline-secondary float-start"
                                    data-bs-toggle="modal" data-bs-target="#UploadImage">
                                    更新头像
                                </button>
                            </div>
                            <div class="col-6">
                                <button type="button" class="btn btn-secondary float-end" data-bs-toggle="modal"
                                    data-bs-target="#UploadImage" disabled>
                                    敬请期待
                                </button>
                            </div>
                        </div>
                    </div>
                    <!-- 修改密码 -->
                    <div class="modal fade" tabindex="-1" id="ChangePassword">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">修改密码</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <p style="color: red">{{ error_msg }}</p>
                                    <div class="form-group" style="margin: 10px auto;">
                                        <input v-model="oldpassword" type="password" class="form-control"
                                            placeholder="旧密码" autocomplete="off">
                                    </div>
                                    <div class="form-group" style="margin: 10px auto;">
                                        <input v-model="password" type="password" class="form-control" placeholder="新密码"
                                            autocomplete="off">
                                    </div>
                                    <div class="form-group" style="margin: 10px auto;">
                                        <input v-model="conformpassword" type="password" class="form-control"
                                            placeholder="确认新密码" autocomplete="off">
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"
                                        @click="clear()">取消</button>
                                    <button type="button" class="btn btn-primary" @click="change()">修改密码</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 详细信息 -->
                    <div class="modal fade" id="Moreinfo" tabindex="-1" aria-labelledby="exampleModalLabel"
                        aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">详细信息</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <p style="color: red">{{ error_msg }}</p>
                                    <div class="form-group" style="margin: 10px auto;">
                                        <label for="recipient-name" class="col-form-label">用户名:</label>
                                        <input type="text" class="form-control" v-model="name">
                                    </div>
                                    <div class="form-group" style="margin: 10px auto;">
                                        <label for="group" class="col-form-label">年级部:</label>
                                        <select class="form-control" v-model="group" id="group">
                                            <option>20级</option>
                                            <option>21级</option>
                                            <option>22级</option>
                                        </select>
                                    </div>
                                    <div class="form-group" style="margin: 10px auto;">
                                        <label for="floatingTextarea" class="col-form-label">个人简介:</label>
                                        <textarea class="form-control" v-model="represent" id="floatingTextarea"
                                            rows="3"></textarea>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"
                                        @click="clear()">关闭</button>
                                    <button type="button" class="btn btn-primary" @click="updateInfo()">保存修改</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 更新头像 -->
                    <div class="modal fade" tabindex="-1" id="UploadImage">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">更新头像</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <p style="color: red">{{ error_msg }}</p>
                                    <div class="text-center">
                                        <img :src="photo" alt="选择并上传头像" id="priview"
                                            style="width: 80%;height: 80%;border-radius: 50%;" />
                                    </div>
                                    <div class="form-group" style="margin: 10px auto;">
                                        <input type="file" class="form-control" id="fileInput"
                                            @change="previewImage($event)">
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"
                                        @click="PhotoClear()">取消</button>
                                    <button type="button" class="btn btn-primary" @click="uploadFile()">确定</button>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>

            <!-- 9 份 -->
            <div class="col-9">
                <div class="card">
                    <div class="card-header text-center">
                        <span style="font-size:130%; font-weight: bold;">历史观看记录</span>
                    </div>
                    <div class="card-body">
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>视频名称</th>
                                    <th>观看时长</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="(history, index) in histories" :key="history.id" class="position-relative">
                                    <td>{{ (now_page - 1) * 10 + index + 1 }}</td>
                                    <td>
                                        <router-link class="nav-link stretched-link"
                                            :to="{ name: 'media', params: { dir_name: splitString(history.video)[0], dir: splitString(history.video)[1], media_name: splitString(history.video)[2] } }">
                                            {{ splitString(history.video)[2] }}</router-link>
                                    </td>
                                    <td>{{ formatSeconds(history.process) }}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="card-footer">
                        <nav>
                            <ul class="pagination justify-content-center" style="">
                                <li class="page-item" @click="click_page(-2)">
                                    <a class="page-link" href="#">&laquo;</a>
                                </li>
                                <li :class="'page-item ' + page.is_active" v-for="page in pages" :key="page.number"
                                    @click="click_page(page.number)">
                                    <a class="page-link" href="#">{{ page.number }}</a>
                                </li>
                                <li class="page-item" @click="click_page(-1)">
                                    <a class="page-link" href="#">&raquo;</a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref } from 'vue';
import $ from 'jquery';
import { useStore } from 'vuex';
import { Modal } from 'bootstrap/dist/js/bootstrap'


export default {
    setup() {

        const store = useStore();

        let password = ref("");
        let conformpassword = ref("");
        let oldpassword = ref("");
        let error_msg = ref("");
        let name = ref(store.state.user.name);
        let represent = ref(store.state.user.represent);
        let group = ref(store.state.user.grp);
        let histories = ref([]);
        let pages = ref([]);
        let current_page = 1;
        let total_video = 0;
        let now_page = ref();
        let photo = ref(store.state.user.photo);
        let file = "";

        const change = () => {
            $.ajax({
                url: "http://localhost:3000/user/change/",
                type: "post",
                data: {
                    oldPassword: oldpassword.value,
                    password: password.value,
                    conformPassword: conformpassword.value,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === 'success') {
                        clear();
                        Modal.getInstance("#ChangePassword").hide();
                    } else {
                        error_msg.value = resp.error_message;
                    }
                },
                error() {
                    error_msg.value = "旧密码错误";
                }
            })
        }

        const updateInfo = () => {
            $.ajax({
                url: "http://localhost:3000/user/update/",
                type: "post",
                data: {
                    name: name.value,
                    represent: represent.value,
                    group: group.value,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === 'success') {
                        Modal.getInstance("#Moreinfo").hide();
                        store.commit("updateName", name.value);
                        store.commit("updateGroup", group.value);
                        if (represent.value === '') {
                            store.commit("updateRepresent", "这个人很懒");
                        } else {
                            store.commit("updateRepresent", represent.value);
                        }
                        clear();
                    } else {
                        error_msg.value = resp.error_message;
                    }
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        const click_page = page => {
            if (page === -2) page = current_page - 1;
            else if (page === -1) page = current_page + 1;
            let max_pages = parseInt(Math.ceil(total_video / 10));

            if (page >= 1 && page <= max_pages) {
                pull_page(page);
            }
        }

        const update_pages = () => {
            let max_pages = parseInt(Math.ceil(total_video / 10));
            let new_pages = [];
            for (let i = current_page - 2; i <= current_page + 2; i++) {
                if (i >= 1 && i <= max_pages) {
                    new_pages.push({
                        number: i,
                        is_active: i === current_page ? "active" : "",
                    })
                }
            }
            pages.value = new_pages;
        }

        const pull_page = page => {
            current_page = page;
            now_page.value = page;
            $.ajax({
                url: "http://localhost:3000/user/history/",
                type: "post",
                data: {
                    page,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    histories.value = resp.histories;
                    total_video = resp.histories_count;
                    update_pages();
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        const previewImage = (event) => {
            file = event.target.files;
            let reader = "";
            if (!(/^image\//).test(file[0].type)) {
                error_msg.value = "请选择图片！";
                document.getElementById('fileInput').value = "";
                return;
            }

            if (window.FileReader) {
                reader = new FileReader();
            } else {
                error_msg.value = "您的浏览器不支持图片预览功能，如需该功能请升级您的浏览器！";
                return;
            }
            reader.onload = function (event) {
                photo.value = event.target.result
            };
            reader.readAsDataURL(file[0]);
        }

        const uploadFile = () => {
            if (file.length === 0) {
                error_msg.value = "请选择上传图片";
                return false;
            } else if (!(/^image\//).test(file[0].type)) {
                error_msg.value = "请选择图片！";
                document.getElementById('fileInput').value = "";
                return;
            } else {
                let data = new FormData();
                data.append('file', file[0]);
                $.ajax({
                    type: "post",
                    url: "http://localhost:3000/user/UploadImage/",
                    data: data,
                    cache: false, //上传文件无需缓存
                    processData: false, //用于对data参数进行序列化处理 这里必须false
                    contentType: false, //必须
                    headers: {
                        Authorization: "Bearer " + store.state.user.token,
                    },
                    success(resp) {
                        if (resp.error_message === 'success') {
                            Modal.getInstance("#UploadImage").hide();
                            store.commit("updatePhoto", resp.photo);
                        }
                    },
                    error() {
                        console.log("false");
                    }
                });
            }
        }

        pull_page(current_page);

        const clear = () => {
            oldpassword.value = "";
            password.value = "";
            conformpassword.value = "";
            name.value = store.state.user.name;
            represent.value = store.state.user.represent;
            error_msg.value = "";
        }

        const PhotoClear = () => {
            error_msg.value = "";
            photo.value = store.state.user.photo;
            document.getElementById('fileInput').value = "";
        }

        return {
            password,
            conformpassword,
            oldpassword,
            error_msg,
            represent,
            histories,
            name,
            pages,
            now_page,
            group,
            photo,
            updateInfo,
            previewImage,
            uploadFile,
            change,
            clear,
            click_page,
            PhotoClear,
        }
    },
    methods: {
        formatSeconds(value) {
            let result = parseFloat(value);
            let res = result / 3600.0;
            res = res.toFixed(2);
            res += "时"
            return res;
        },
        splitString(value) {
            let string = value.split("/");
            return string;
        },
    },
}
</script>

<style scoped>
li {
    list-style: none;
}

textarea {
    resize: none;
}
</style>
