<template>
    <div class="train-tabs-container">
        <el-button class="train-tabs-btn" type="primary" :icon="VideoPlay" @click="dialogVisible = true">训练模型</el-button>
    </div>

    <el-dialog class="train-tabs-params-dialog" v-model="dialogVisible" title="设置训练参数" width="500" draggable>
        <el-tabs v-model="activeName" class="train-tabs" @tab-click="handleClick">
            <el-tab-pane label="CNN" name="cnn">
                 <FormView :current-tab="activeName"/>
            </el-tab-pane>
            <el-tab-pane label="RNN" name="rnn">
                <FormView :current-tab="activeName"/>
            </el-tab-pane>
            <el-tab-pane label="DCNN" name="dcnn">
                <FormView :current-tab="activeName"/>
            </el-tab-pane>
            <el-tab-pane label="VGGN" name="vggn">
                <FormView :current-tab="activeName"/>
            </el-tab-pane>
        </el-tabs>
    </el-dialog>
</template>
  
<script lang="ts" setup>
import { ref } from 'vue'
import type { TabsPaneContext } from 'element-plus'
import {  VideoPlay } from '@element-plus/icons-vue';
import FormView from './FormView.vue';
const dialogVisible = ref(false);
const activeName = ref('cnn');
  
const handleClick = (tab: TabsPaneContext, event: Event) => {
    console.log(tab, event)
}
</script>
  
<style>

.train-tabs-container{  
    width: 100%;
    height: 100%;
    background-color: azure;
}

.train-tabs > .el-tabs__content {
    padding: 32px;
    color: #6b778c;
    font-size: 32px;
    font-weight: 600;
}

.train-tabs-params-dialog{

    width: 80%;
    height: 90%;
    text-align: center;
    /* background-color: beige; */
}
</style>