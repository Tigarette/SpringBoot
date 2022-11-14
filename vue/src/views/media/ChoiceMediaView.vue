<template>
    <div class="row" style="width:100%; margin-top:30px">
        <div class="col-2"></div>
        <div class="col-8">
            <div class="card">
                <div class="card-header">
                    {{ $route.params.dir_name }}
                    <select v-model="selected" @change="select(1)" style="float: right;width: 25%;" class="form-select">
                        <option v-for="name in classification" :key="name">{{ name }}</option>
                    </select>
                </div>
                <div class="card-body">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>序号</th>
                                <th>视频名称</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="position-relative" v-for="(media, index) in medium" :key="media">
                                <td>{{ (page - 1) * 10 + index + 1 }}</td>
                                <td v-if="media.includes('.mp4')">
                                    <router-link :to="{
                                        name: 'media', params: {
                                            dir_name: $route.params.dir_name, dir: selected, media_name: media
                                        }
                                    }" class="stretched-link nav-link">{{ media }}</router-link>
                                </td>
                                <td v-else>
                                    <router-link :to="{
                                        name: 'download', params: {
                                            dir_name: $route.params.dir_name, dir: selected, media_name: media
                                        }
                                    }" class="stretched-link nav-link">{{ media }}</router-link>
                                </td>
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
        <div class="col-2"></div>
    </div>
</template>
<script>
import $ from 'jquery';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router';
import { ref } from 'vue';
import router from '@/router';

export default {

    setup() {
        const store = useStore();
        const route = useRoute();
        let medium = ref([]);
        let classification = ref([]);
        let selected = ref("");
        let total = 0;
        let pages = ref([]);
        let current_page = 1;
        let page = ref();

        $.ajax({
            url: "http://localhost:3000/check/onePath/",
            type: "post",
            data: {
                dir_name: route.params.dir_name,
            },
            headers: {
                Authorization: "Bearer " + store.state.user.token,
            },
            success(resp) {
                if (resp.error_message === 'success') {
                    getList();
                } else {
                    router.push({ name: "404" });
                }
            },
            error() {
                router.push({ name: "404" });
            }
        })

        const select = (val) => {
            current_page = val;
            page.value = val;
            $.ajax({
                url: "http://localhost:3000/GetMediaName/",
                type: "post",
                data: {
                    "path": route.params.dir_name + "/" + selected.value,
                    "page": val - 1,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    medium.value = resp;
                    update_pages();
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        const click_page = page => {
            if (page === -2) page = current_page - 1;
            else if (page === -1) page = current_page + 1;
            let max_pages = parseInt(Math.ceil(total / 10));

            if (page >= 1 && page <= max_pages) {
                select(page);
            }
        }

        const update_pages = () => {
            let max_pages = parseInt(Math.ceil(total / 10));
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

        const getList = () => {
            $.ajax({
                url: "http://localhost:3000/getMediaList/",
                type: "post",
                data: {
                    "path": route.params.dir_name,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    classification.value = resp;
                    selected.value = classification.value[0];
                    GetSumPage();
                    select(current_page);
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        const GetSumPage = () => {
            $.ajax({
                url: "http://localhost:3000/getSumPage/",
                type: "post",
                data: {
                    "path": route.params.dir_name + "/" + selected.value,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    total = resp;
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        return {
            selected,
            medium,
            classification,
            pages,
            page,
            click_page,
            select,
        }
    }
}
</script>
<style scoped>

</style>