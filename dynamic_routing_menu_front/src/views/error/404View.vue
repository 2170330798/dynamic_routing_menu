<template>
    <!-- <div class="title-container">
        <h1 class="title-h1">Not Found!</h1>
        <h2 class="title-h2">404</h2>
    </div> -->
    
    <el-result title="404" sub-title="Sorry, request error">
        <template #icon>
        <el-image
            src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
        />
        </template>
        <template #extra>
        <el-button type="primary" @click="back">返回</el-button>
        </template>
    </el-result>
</template>

<script lang="ts" setup>
import { useRouter } from 'vue-router';

const router = useRouter();
const back = () => {
    router.push('/')
}


</script>


<style lang="css" scoped>
    
    /* .title-container{
        width: 100%;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .title-h1{
        align-items: center;
        font-size: large;
    }
    .title-h2{
        font-size: large;
        display: flex;

    } */
</style>