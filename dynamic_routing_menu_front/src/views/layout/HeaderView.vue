<template>
  <div class="header-avatar-container">
      <el-avatar :size="40" :src="avatarUrl" :key="avatarUrl" @error="errorHandler">
        <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png?v=1.0" alt="UserAvatar" />
      </el-avatar>
  </div>
  <el-button class="loginout-btn" type="primary" @click="logout">退出</el-button>

</template>

<script lang="ts" setup>
import { computed } from 'vue';
import { useAuthStore } from '../../store/auth/auth';
import router from '../../router';

const errorHandler = () => true
const store = useAuthStore();

const logout = () => {
      store.setIsAuthenticated(false);
      router.push('/login');
}

// const state = reactive({
//   circleUrl: store.getLoginInfo.avatar
// });

// const { circleUrl } = toRefs(state);
// 使用计算属性确保头像URL响应式更新
const avatarUrl = computed(() => store.getLoginInfo?.avatar || '');
console.log(store.getLoginInfo?.avatar);

</script>

<style lang="css">

.header-avatar-container {
  width: 50px;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.loginout-btn{
    margin-left: 90%;
}
</style>