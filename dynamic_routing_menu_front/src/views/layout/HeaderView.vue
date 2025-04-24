<template>
    <div class="home-header">
        <div class="avatar-container">
          <el-avatar :size="45" :src="circleUrl" @click="centerDialogVisible = true" >
          </el-avatar>
        </div>
        <el-button class="exit-btn" type="primary" @click="logout">退出</el-button>
        
        <el-dialog class="avatar-dialog" v-model="centerDialogVisible" :showClose="false" destroy-on-close :modal="false" :append-to-body="false">              
        </el-dialog> 
        
    </div>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref, toRefs } from 'vue';
import { useAuthStore } from '../../store/auth/auth';
import { useRouter } from 'vue-router'; 
const centerDialogVisible = ref(false);
const store = useAuthStore();
const router = useRouter();
const state = reactive({
  circleUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
  sizeList: ['small'] as const,
})

const { circleUrl} = toRefs(state)

const setAvatar = () => {
    state.circleUrl = store.getLoginInfo.avatar;
    console.log('url:', state.circleUrl);
}

const logout = () => {
      store.setIsAuthenticated(false);
      router.push('/login');
}

onMounted(()=>{
    
    setAvatar();
})

</script>


<style lang="css">
.home-header {
    width: 100%;
    height: 50px;
    display: flex;
    align-items: center;
    background-color: aliceblue;
    position: relative; /* 添加相对定位 */
}

.avatar-container {
    width: 45px;
    height: 45px;
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative; /* 添加相对定位 */
}

.exit-btn{
    margin-left: 90%;
}

.avatar-dialog{
   width: 200px;
   height: 300px;
   position: absolute;
   left: 5%;
   top: 0%;
   background-color: rgb(248, 249, 249);
}

/* 移除默认的遮罩层 */
.avatar-dialog .el-overlay {
  background-color: transparent !important;
  pointer-events: none;
}

.avatar-dialog .el-overlay-dialog {
  pointer-events: auto;
}


.popover-base-box {
  width: 600px;
}

.popover-base-box .row {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.popover-base-box .center {
  justify-content: center;
}

.popover-base-box .box-item {
  width: 110px;
  margin-top: 10px;
}
</style>