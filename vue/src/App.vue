<template>
  <NavBar></NavBar>
  <router-view></router-view>
</template>
<script>
import NavBar from '@/components/NavBar.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
import 'font-awesome/css/font-awesome.css';

export default {

  components: {
    NavBar
  },


}
</script>
<style scoped>

</style>
