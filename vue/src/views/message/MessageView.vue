<template>
    <div class="row" style="margin-top:30px">
        <div class="col-3"></div>
        <div class="col-6">
            <div class="card">
                <div class="card-header">
                    <h2>留言 <button class="btn btn-primary float-end" @click="send()">发表</button></h2>
                </div>
                <div class="card-body">
                    <p style="color:red">{{ error_message }}</p>
                    <textarea class="form-control" placeholder="留下金口玉言"
                        style="height: 100px; resize: none;width:80%; margin: 0 auto;" v-model="message"></textarea>
                </div>
            </div>
            <div class="card" style="margin-top: 30px;" v-for="comment in comments" :key="comment.id">
                <div class="card-header"
                    v-if="(comment.userid == $store.state.user.id || $store.state.user.admin === '0')">
                    <button class="btn btn-danger float-end" data-bs-toggle="modal" data-bs-target="#delete">删除</button>

                    <!-- deleteModal -->
                    <div class="modal fade" id="delete" tabindex="-1">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">删除</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <p style="color: red;">{{ modal_message }}</p>
                                    是否确认删除此条留言
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                                    <button type="button" class="btn btn-danger" @click="Delete(comment.id)">确认</button>
                                </div>
                            </div>
                        </div>
                    </div>


                </div>
                <div class="card-body">
                    <h4><img :src="comment.photo"> {{ comment.name }} <h6 class="float-end">{{ comment.time }}</h6>
                    </h4>
                    <hr>
                    <div class="container" style="white-space: pre-line">
                        {{ comment.message }}
                    </div>
                </div>
            </div>


            <nav style="margin-top: 20px;">
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
        <div class="col-3"></div>
    </div>
</template>
<script>

import $ from 'jquery';
import { ref } from 'vue';
import { useStore } from 'vuex';
import { postRequest } from '@/utils/http'
import { Modal } from 'bootstrap/dist/js/bootstrap'

export default {

    setup() {

        const store = useStore();

        let message = ref("");
        let error_message = ref("");
        let comments = ref([]);
        let pages = ref([]);
        let current_page = 1;
        let total_comments = 0;
        let now_page = ref();
        let modal_message = ref("");
        const send = () => {
            $.ajax({
                url: "http://localhost:3000/message/send/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    message: message.value,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        message.value = "";
                        error_message.value = "";

                        current_page = 1;
                        pull_page(current_page);
                    } else {
                        error_message.value = resp.error_message;
                    }
                }
            })
        }

        const click_page = page => {
            if (page === -2) page = current_page - 1;
            else if (page === -1) page = current_page + 1;
            let max_pages = parseInt(Math.ceil(total_comments / 10));

            if (page >= 1 && page <= max_pages) {
                pull_page(page);
            }
        }

        const update_pages = () => {
            let max_pages = parseInt(Math.ceil(total_comments / 10));
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
                url: "http://localhost:3000/message/get/",
                type: "post",
                data: {
                    page,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    comments.value = resp.comments;
                    total_comments = resp.comments_count;
                    update_pages();
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        pull_page(current_page);

        const Delete = (id) => {
            postRequest("/message/delete/",
                { messageId: id },
                function success(resp) {
                    if (resp.error_message === 'success') {
                        modal_message.value = "";
                        current_page = 1;
                        pull_page(current_page);
                        Modal.getInstance("#delete").hide();
                    } else {
                        modal_message.value = resp.error_message;
                    }
                }, function error() {
                    console.log(2);
                })
        }

        return {
            message,
            error_message,
            comments,
            pages,
            modal_message,
            send,
            click_page,
            Delete,
        }


    }
}
</script>
<style scoped>
img {
    width: 5vh;
    border-radius: 50%;
}
</style>
