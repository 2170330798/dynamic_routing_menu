<script setup lang="ts">
import FormView from './FormView.vue';

</script>

<template>
    <div class="login-container">
        <div class="login-left-container">
            <h1 class="header-title">Login</h1>
            <div class="log-container">
                <el-image src="/public/vite.svg" style="width: 100%; height: 100%"/>
            </div>
        </div>
        <div class="login-right-container">
            <FormView class="login-form-view"/>
        </div>
    </div>

</template>

<style lang="css">
.login-container{
    display: flex;
    background-image: image();
}
.login-left-container {
    width: 300px;
    height: 560px;
    color:azure;
    text-align: center;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background-color:   #dee1e0;
}

.login-right-container{
    width: 100%;
    height: 560px;
    color:azure;
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center; 
    background-color:   #f9f8f9;
}

.log-container{
    width: 120px;
    height: 120px;
    margin-bottom: 100%;
    
}

.header-title{
    margin-bottom: 10%;
}

.login-form-view{
  position: relative; /* 保持正常文档流 */
  z-index: 1; /* 确保表单在背景上方 */
}
</style>