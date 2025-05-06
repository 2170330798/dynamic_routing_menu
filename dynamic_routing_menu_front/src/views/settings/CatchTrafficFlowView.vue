<template>
    <div class="catch_traffic_container">
        <h1 class="title">流量抓取与识别设置</h1>
        <div class="catch_btn_container">
            <el-button type="primary" @click="startCatch">开始</el-button>
            <el-button type="primary" @click="stoptCatch">暂停</el-button>
        </div>
    </div>
</template>

<script setup lang="ts">
import axios from 'axios';
import { ElMessage } from 'element-plus';


const startCatch = () => {
    axios.get("http://127.0.0.1:8000/start_scheduler/")
            .catch((error: Error) => {
              ElMessage.error(`请求失败: ${error.message}`);
              console.error('请求错误:', error);
    })
}

const stoptCatch = () => {
    axios.get("http://127.0.0.1:8000/stop_scheduler/")
            .catch((error: Error) => {
              ElMessage.error(`请求失败: ${error.message}`);
              console.error('请求错误:', error);
    })
}

</script>


<style lang="css" scoped>
.catch_traffic_container{
    width: 100%;
    height: 750px;
    color: black;
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: aquamarine;
}

.catch_btn_container{
    width: 100%;
    height: 60px;
    display: flex;
    justify-content: center;
    align-items: center;
}

</style>