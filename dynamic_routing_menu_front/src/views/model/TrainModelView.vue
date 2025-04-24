<template>
    <div class="train-container">
         <div class="header-container">
             <h1>训练模型</h1>
        </div>
        <div class="train-btn-container">   
            <el-button class="train-data-btn" type="primary" :icon="Setting" @click="setTrainParams">设置训练参数</el-button>
            <el-button class="train-btn" type="primary" :icon="VideoPlay" @click="startTrainModel">训练模型</el-button>
        </div>
        <div class="table-container1">
             <FrameList />
        </div>
    </div>
    <el-dialog class="train-params-dialog" v-model="dialogVisible1" title="设置训练参数" width="500" draggable>
        <FormView @close-dialog="closeDialog1"/>
    </el-dialog>
</template>

<script setup lang="ts">
import { Setting, VideoPlay } from '@element-plus/icons-vue';
import { ref, computed } from 'vue';
import FrameList  from './FrameList.vue';
import FormView from './FormView.vue';
import { useTrainStore } from '../../store/train';
import { trainModel } from '../../api/train';
import { ElMessage } from 'element-plus';

const dialogVisible1 = ref(false);
const dialogVisible2 = ref(false);
const currentTaskId = ref(''); // 新增任务ID
const shouldConnectWs = ref(false)

const store = useTrainStore();
const closeDialog1 = () => {
    dialogVisible1.value = false;
};

const closeDialog2 = () =>{
    dialogVisible2.value = false;
}

// ✅ 直接使用计算属性（保持响应式）

// const startTrainModel = async() => {
//      const result = await trainModel(store.getTrainData);
//      console.log(result);
//      dialogVisible2.value = false;
// }

const startTrainModel = async() => {
    // 生成唯一任务ID
    currentTaskId.value = `task_${Date.now().toString().slice(-8)}`; // 取后8位减少长度
    
    // 先重置连接状态
    shouldConnectWs.value = false;
    
    // 打开进度对话框
    dialogVisible2.value = true;

    try {
        const result = await trainModel({
        ...store.getTrainData,
        taskId: currentTaskId.value
        })
        
        // 只有返回200时才建立连接
        if (result.status === 200) {
        shouldConnectWs.value = true
        } else {
        dialogVisible2.value = false
        ElMessage.error('训练进度条启动失败')
        }
  } catch (error) {
    dialogVisible2.value = false
    ElMessage.error('训练进度条请求失败')
  }
}
const setTrainParams = () => {
    dialogVisible1.value = true;
}
</script>

<style lang="css">
.train-container{
    width: 100%;
    height: 90vh;
    background-color: rgba(251, 253, 252, 0.613);
}
.header-container{
    width: 100%;
    height: 30px;
    text-align: center;
    /* background-color: rgba(248, 250, 252, 0.098); */
}
.train-btn-container{
     width: 100%;
     height: 50px;
     display: flex;
     justify-content: right;
     align-items: center;
     background-color: rgba(219, 217, 214, 0.204);
}

/* .train-btn{
    margin-right: 0%;
} */

.train-params-dialog{
    width: 100%;
    max-width: 1200px;
    margin-right: 30px;
    padding: 20px;
    text-align: center;
    background-color: rgb(255, 255, 255);
}
</style>