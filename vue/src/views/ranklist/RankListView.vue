<template>
    <div class="row" style="width:100%">
        <div class="col-2"></div>
        <div class="col-8">
            <div class="card" style="margin-top: 30px;">
                <div class="card-header text-center">
                    <span style="font-size: 26px;">谁是卷王</span>
                </div>
                <div class="card-body">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">排名</th>
                                <th scope="col">姓名</th>
                                <th scope="col">个人简介</th>
                                <th scope="col">年级部</th>
                                <th scope="col">观看总时长</th>
                            </tr>
                        </thead>
                        <tbody v-for="(user, index) in users" :key="user.id">
                            <tr>
                                <th>{{ (now_page - 1) * 10 + index + 1 }}</th>
                                <td v-if="user.name !== null">
                                    <img :src="user.photo">
                                    {{ user.name }}
                                </td>
                                <th v-else style="color: red;">
                                    <img :src="user.photo">
                                    他没名
                                </th>
                                <td v-if="user.represent !== null">{{ user.represent }}</td>
                                <td v-else>这个人很懒</td>
                                <td v-if="user.grp !== null">{{ user.grp }}</td>
                                <th v-else style="color: red;">这哪儿人</th>
                                <td>{{ formatSeconds(user.process) }}</td>
                            </tr>
                        </tbody>
                    </table>
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
import { ref } from 'vue';


export default {

    setup() {

        let users = ref([]);
        let pages = ref([]);
        let current_page = 1;
        let total_users = 0;
        let now_page = ref();
        const store = useStore();

        const click_page = page => {
            if (page === -2) page = current_page - 1;
            else if (page === -1) page = current_page + 1;
            let max_pages = parseInt(Math.ceil(total_users / 10));

            if (page >= 1 && page <= max_pages) {
                pull_page(page);
            }
        }

        const update_pages = () => {
            let max_pages = parseInt(Math.ceil(total_users / 10));
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
                url: "http://localhost:3000/ranklist/",
                type: "post",
                data: {
                    page,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    users.value = resp.users;
                    total_users = resp.users_count;
                    update_pages();
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        pull_page(current_page);

        return {
            users,
            now_page,
            pages,
            click_page,
        }
    },
    methods: {
        formatSeconds(value) {
            let result = parseInt(value);
            let y =
                Math.floor(result / 86400) < 10
                    ? "0" + Math.floor(result / 86400)
                    : Math.floor(result / 86400);
            let h =
                Math.floor((result / 3600) % 24) < 10
                    ? "0" + Math.floor((result / 3600) % 24)
                    : Math.floor((result / 3600) % 24);
            let m =
                Math.floor((result / 60) % 60) < 10
                    ? "0" + Math.floor((result / 60) % 60)
                    : Math.floor((result / 60) % 60);
            let s =
                Math.floor(result % 60) < 10
                    ? "0" + Math.floor(result % 60)
                    : Math.floor(result % 60);
            let res = "";
            if (y !== "00") res += `${y}天`;
            if (h !== "00") res += `${h}时`;
            if (m !== "00") res += `${m}分`;
            res += `${s}秒`;
            return res;
        },
    },
}

</script>

<style scoped>
img {
    width: 4vh;
    border-radius: 50%;
}
</style>