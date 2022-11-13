<template>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container">
			<router-link class="navbar-brand" :to="{ name: 'home' }">
				<img src="@/assets/logo.png" alt="" width="28" height="28" class="d-inline-block align-text-top">
				HNIEACM
			</router-link>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText"
				aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarText">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item md-2">
						<router-link :class="route_name == 'home_index' ? 'nav-link active mleft' : 'mleft nav-link'"
							aria-current="page" :to="{ name: 'home_index' }">主页</router-link>
					</li>
					<li class="nav-item">
						<router-link
							:class="route_name == 'media_index' || route_name == 'media_in' || route_name == 'media' || route_name == 'download' ? 'mleft nav-link active' : 'mleft nav-link'"
							:to="{ name: 'media_index' }">视频</router-link>
					</li>
					<li class="nav-item">
						<router-link
							:class="route_name == 'ranklist_index' ? 'mleft nav-link active' : 'mleft nav-link'"
							:to="{ name: 'ranklist_index' }">排行榜</router-link>
					</li>
				</ul>
				<ul class="navbar-nav" v-if="$store.state.user.is_login">
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
							data-bs-toggle="dropdown" aria-expanded="false">
							{{ $store.state.user.name }}
						</a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdown" style="margin: 0;">
							<li>
								<router-link class="dropdown-item" :to="{ name: 'user_info_index' }">个人信息</router-link>
							</li>
							<li>
								<hr class="dropdown-divider">
							</li>
							<li><a class="dropdown-item" @click="logout">退出登录</a></li>
						</ul>
					</li>
				</ul>
				<ul class="navbar-nav" v-else-if="!$store.state.user.pulling_info || route_name == 'user_register'">
					<li class="nav-item" style="margin:auto 10px;">
						<router-link class="btn btn-primary" :to="{ name: 'user_login' }" role="button">
							登录
						</router-link>
					</li>
					<li class="nav-item" style="margin:auto 10px;">
						<router-link class="btn btn-secondary" :to="{ name: 'user_register' }" role="button">
							注册
						</router-link>
					</li>
				</ul>
			</div>
		</div>
	</nav>
</template>

<script>
import { useRoute } from 'vue-router'
import { computed } from 'vue'
import { useStore } from 'vuex'
import router from '@/router/index'

export default {
	setup() {
		const store = useStore();
		const route = useRoute();
		let route_name = computed(() => route.name)
		const logout = () => {
			store.dispatch("logout");
			router.push({ name: 'user_login' })
		}
		return {
			route_name,
			logout,
		}
	}
}
</script>

<style scoped>
.navbar-nav>.nav-item>.active {
	border-bottom: 2px solid skyblue;
}

.mleft {
	margin-left: 40px;
}
</style>