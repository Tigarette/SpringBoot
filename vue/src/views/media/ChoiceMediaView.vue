<template>

    <select v-model="selected" @change="select">
        <option v-for="name in classification" :key="name">{{ name }}</option>
    </select>
    <div v-for="media in medium" :key="media">
        <router-link :to="{
            name: 'media', params: {
                dir_name: $route.params.dir_name, dir: selected, media_name: media
            }
        }" v-if="media.includes('.mp4')">{{ media }}</router-link>
        <router-link :to="{
            name: 'download', params: {
                dir_name: $route.params.dir_name, dir: selected, media_name: media
            }
        }" v-else>{{ media }}</router-link>
    </div>
    <select v-model="page" @change="select">
        <option v-for="page of SumPage" :key="page">{{ page }}</option>
    </select>

</template>
<script>
import $ from 'jquery';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router';
import { ref } from 'vue';

export default {


    setup() {
        const store = useStore();
        const route = useRoute();
        let medium = ref([]);
        let classification = ref([]);
        let selected = ref("");
        let page = ref("1");
        let SumPage = ref();
        const select = () => {
            $.ajax({
                url: "http://localhost:3000/GetMediaName/",
                type: "post",
                data: {
                    "path": route.params.dir_name + "/" + selected.value,
                    "page": page.value - 1,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    medium.value = resp;
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

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
                select();
                GetSumPage();
            },
            error(resp) {
                console.log(resp);
            }
        })
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
                    SumPage.value = resp;
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        return {
            selected,
            medium,
            select,
            classification,
            page,
            SumPage,
        }
    }
}
</script>