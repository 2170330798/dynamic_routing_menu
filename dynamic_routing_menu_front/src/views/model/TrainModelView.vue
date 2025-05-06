<template>
    <div class="train-container">
         <div class="train-header-container">
             <h1>训练模型</h1>
        </div>
        <div class="train-btn-container">   
            <el-button class="train-data-btn" type="primary" :icon="Setting" @click="setTrainParams">设置训练参数</el-button>
            <el-button class="train-btn" type="primary" :icon="VideoPlay" @click="dialogVisible = true">训练模型</el-button>
        </div>
        <div class="frame-table-container">
             <FrameList />
        </div>
        <!--切换模型参数表单-->
          <el-dialog class="train-tabs-params-dialog" v-model="dialogVisible1" title="设置训练参数" width="500" draggable>
            <el-tabs v-model="activeName" class="train-tabs" @tab-click="handleClick">
                <el-tab-pane label="CNN" name="cnn">
                    <FormView :current-tab="activeName"  @close-dialog="closeDialog"/>
                </el-tab-pane>
                <el-tab-pane label="RNN" name="rnn">
                    <FormView :current-tab="activeName"  @close-dialog="closeDialog"/>
                </el-tab-pane>
                <el-tab-pane label="DCNN" name="dcnn">
                    <FormView :current-tab="activeName"  @close-dialog="closeDialog"/>
                </el-tab-pane>
                <el-tab-pane label="VGGN" name="vggn">
                    <FormView :current-tab="activeName"  @close-dialog="closeDialog"/>
                </el-tab-pane>
            </el-tabs>
        </el-dialog>
        <!--训练进展Dialog-->
        <el-dialog class="train-dialog" v-model="dialogVisible" width="500" draggable align-center>
          <h2 class="train-title">训练进度 [任务ID: {{ taskId }}]</h2>

          <div v-if="status">
            <!--训练信息-->
            <span class="train-item-label">训练进度</span>
            <el-progress :percentage="safePercentage(status.progress)" />
            <span class="train-item-label">训练损失</span>
            <el-progress :percentage="safePercentage(lossValue)" />
            <span class="train-item-label">训练准确率</span>
            <el-progress :percentage="safePercentage(accuracyValue)" />
            
            <!-- 状态和时间 -->
            <div class="status-info">
              <el-tag :type="statusTagType">{{ status.status || '训练中' }}</el-tag>
              <small class="update-time">更新时间: {{ formatTime(status.timestamp) }}</small>
            </div>
          </div>
          <div v-else class="waiting-connection">
            <el-icon class="loading-icon"><Loading /></el-icon>
            <p>等待连接训练任务...</p>
          </div>
          <template #footer>
            <div class="train-dialog-footer">
              <el-button @click="handleCancel">取消训练</el-button>
              <el-button type="primary" @click="connectWebSocket" :loading="connecting">
                开始训练
              </el-button>
            </div>
          </template>
        </el-dialog>
</div>
    
</template>

<script setup lang="ts">
import { Setting, VideoPlay } from '@element-plus/icons-vue';
import { ref } from 'vue';
import FrameList  from './FrameList.vue';
import FormView from './FormView.vue';
import { useTrainStore } from '../../store/train';
import type { TabsPaneContext } from 'element-plus'
import { Loading } from '@element-plus/icons-vue'
import { useWebSocket } from "../../components/websocket/websocket"

const activeName = ref('cnn');

const handleClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab, event)
}

const dialogVisible1 = ref(false);

const store = useTrainStore();
const closeDialog = () => {
    dialogVisible1.value = false;
};


const setTrainParams = () => {
    dialogVisible1.value = true;
}

// const connectWebSocket = () => {
//       console.log("训练参数:", store.getTrainData);
// }

// 使用组合式函数
const {
    taskId,
    status,
    dialogVisible,
    connecting,
    accuracyValue,
    lossValue,
    statusTagType,
    formatTime,
    handleCancel,
    connectWebSocket,
    safePercentage
  
} = useWebSocket(store.getTrainData);
</script>

<style lang="css">
.train-container{
    width: 100%;
    height: 80%;
}

.frame-table-container{
   width: 100%;
   height: 100%;
   margin-top: 0%;
}

.train-header-container{
    width: 100%;
    height: 35px;
    text-align: center;
    margin-top: 0%;
    background-color: #F2F6FC;
}

.train-btn-container{
     width: 100%;
     height: 50px;
     display: flex;
     justify-content: right;
     align-items: center;
}

.loading-icon {
  animation: rotating 2s linear infinite;
  font-size: 24px;
  color: var(--el-color-primary);
}

.train-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}

.train-tabs-params-dialog{

  width: 90%;
  height: 90%;
  text-align: center;
}


/* 保持原有的样式不变 */
.train-dialog {
  width: 800px;
  height: 400px;
  text-align: center;
  /* background-color: antiquewhite; */
}

.train-title {
  margin-bottom: 20px;
  color: #409EFF;
}

.status-info {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.update-time {
  color: #909399;
}

.waiting-connection {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.train-item-label{
   width: 50px;
   height: 25px;
   color: black;
   text-align: left;
   margin-right: 90%;
   /* background-color: aliceblue; */
}

.loading-icon {
  animation: rotating 2s linear infinite;
  margin-bottom: 10px;
  font-size: 24px;
  color: #409EFF;
}

@keyframes rotating {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

</style>


 
