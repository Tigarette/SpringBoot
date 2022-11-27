<template>
    <div class="row" style="width:100%; margin-top:30px">
        <div class="col-2"></div>
        <div class="col-8">
            <div class="card">
                <div class="card-header text-center">
                    <h3>视频分类</h3>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-8"></div>
                        <div class="col-4">
                            <div class="input-group mb-3">
                                <input type="text" class="form-control" placeholder="视频名称" aria-label="视频名称"
                                    aria-describedby="button-addon2" v-model="searchVal">
                                <button class="btn btn-outline-secondary" type="button" id="button-addon2"
                                    @click="search(1)">搜索</button>
                            </div>
                        </div>
                    </div>
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">序号</th>
                                <th scope="col">视频名称</th>
                            </tr>
                        </thead>
                        <tbody v-if="!searchFlag">
                            <tr class="position-relative" v-for="(media, index) in MediaList" :key="media">
                                <td>{{ (page - 1) * 10 + index + 1 }}</td>
                                <td>
                                    <router-link :to="{
                                        name: 'media_in', params: { 'dir_name': media }
                                    }" class="stretched-link">
                                        {{ media }}
                                    </router-link>
                                </td>
                            </tr>
                        </tbody>
                        <tbody v-else-if="MediaList.length !== 0">
                            <tr class="position-relative" v-for="(media, index) in MediaList" :key="media.id">
                                <td>{{ (page - 1) * 10 + index + 1 }}</td>
                                <td>
                                    <router-link
                                        :to="{ name: 'media', params: { 'dir_name': media.dir_name, 'dir': media.dir, 'media_name': media.media_name } }"
                                        class="stretched-link">
                                        {{ media.name }}
                                    </router-link>
                                </td>
                            </tr>
                        </tbody>
                        <div v-else>111</div>
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

</template>

<script>
import $ from 'jquery';
import { useStore } from 'vuex';
import { ref } from 'vue';

export default {
    setup() {

        const store = useStore();
        let total = 1;
        let pages = ref([]);
        let current_page = 1;
        let page = ref();
        let MediaList = ref([]);
        let searchVal = ref("");
        let searchFlag = ref(false);


        const select = (val) => {
            current_page = val;
            page.value = val;
            $.ajax({
                url: "http://localhost:3000/medialist/",
                type: "get",
                data: {
                    page: val - 1,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    MediaList.value = resp;
                    update_pages();
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        const search = (val) => {
            if (searchVal.value === "") {
                current_page = 1;
                searchFlag.value = false;
                getTotal();
            } else {
                current_page = val;
                page.value = val;
                $.ajax({
                    url: "http://localhost:3000/search/",
                    type: "post",
                    data: {
                        name: searchVal.value,
                        page: val - 1,
                    },
                    headers: {
                        Authorization: "Bearer " + store.state.user.token,
                    },
                    success(resp) {
                        MediaList.value = resp.mediaList;
                        console.log(MediaList.value);
                        MediaList.value.forEach(function (item) {
                            let arr = item.path.split('\\');
                            item.dir_name = arr[0];
                            item.dir = arr[1];
                            item.media_name = arr[2];
                        })
                        total = resp.media_count;
                        searchFlag.value = true;
                        update_pages();
                    },
                    error(resp) {
                        console.log(resp);
                    }
                })
            }
        }

        const getTotal = () => {
            $.ajax({
                url: "http://localhost:3000/media/getTotal/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    total = resp;
                    select(current_page);
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

            if (page >= 1 && page <= max_pages && !searchFlag.value) {
                select(page);
            } else {
                search(page);
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
        getTotal();

        return {
            page,
            pages,
            MediaList,
            searchVal,
            searchFlag,
            click_page,
            search,
        }
    },

}
</script>

<style scoped>

</style>