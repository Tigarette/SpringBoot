<template>
    <div v-for="(media) in MediaList" :key="media">
        <router-link :to="{
            name: 'media_in', params: { 'dir_name': media }
        }">
            {{ media }}
        </router-link>
    </div>

</template>

<script>
import $ from 'jquery';
import { useStore } from 'vuex';
import { ref } from 'vue';

export default {
    setup() {

        const store = useStore();
        let MediaList = ref([]);

        $.ajax({
            url: "http://localhost:3000/medialist/",
            type: "get",
            headers: {
                Authorization: "Bearer " + store.state.user.token,
            },
            success(resp) {
                MediaList.value = resp;
            },
            error(resp) {
                console.log(resp);
            }
        })

        return {
            MediaList,
        }
    }
}
</script>

<style scoped>

</style>