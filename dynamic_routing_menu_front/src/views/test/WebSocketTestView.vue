<template>
  <el-button plain @click="dialogVisible = true">
    开始训练
  </el-button>

  <el-dialog class="train-dialog" v-model="dialogVisible" width="500" draggable align-center>
    <h2 class="train-title">训练进度 [任务ID: {{ taskId }}]</h2>
    <div v-if="status">
      <!--训练信息-->
      <span class="train-item-label">训练进度</span>
      <el-progress :percentage="safePercentage(status.progress)" />
      <span class="train-item-label">训练损失</span>
      <el-progress :percentage="Number(lossValue.toFixed(2))" />
      <span class="train-item-label">训练准确率</span>
      <el-progress :percentage="Number(accuracyValue.toFixed(2))" />
      
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
  <div class="train-data-container">
    'csvSourcePath':  {{ data.csvSourcePath }}, <br/>
    'csvDestPath': {{ data.csvDestPath }}, <br/>
    'labelColumn': {{ data.labelColumn }}, <br/>
    'batchSize': {{ data.batchSize }}, <br/>
    'rows': {{ data.rows }}, <br/>
    'numEpochs': {{ data.numEpochs }}, <br/>
    'lr': {{ data.lr }}, <br/>
    'stepSize': {{ data.stepSize }}, <br/>
    'gamma': {{ data.gamma }}, <br/>
    'modelName': {{ data.modelName }}, <br/>
    'frame': {{ data.frame }}, <br/>
    'modelSavePath': {{ data.modelSavePath }}, <br/>
    'isSaveCsvData': {{ data.isSaveCsvData }}, <br/>
    'modelVersion': {{ data.modelVersion }}, <br/>
    'modelId': {{ data.modelId }}, <br/>
    'taskId': {{ data.taskId }}, <br/>
    'modelPath': {{ data.modelPath }}, <br/>
    'patience': {{ data.patience }}, <br/>
    'initMode': {{ data.initMode }}, <br/>
    'factor': {{ data.factor }}, <br/>
    'weightDecay': {{ data.weightDecay }}, <br/>
    'validateMode': {{ data.validateMode }} 
  </div>
</template>


<script setup lang="ts">
import { Loading } from '@element-plus/icons-vue'
import { useWebSocket } from "../../components/websocket/websocket"
import { useTrainStore } from '../../store/train';
import pinia from '../../store';


const data = {
    'csvSourcePath': 'F:\\PythonWeb\\myDjango\\app\\dataset\\train_dataset_03.csv', 
    'csvDestPath': '', 
    'labelColumn': 'Label', 
    'batchSize': 32, 
    'rows': 3000, 
    'numEpochs': 5, 
    'lr': 0.001, 
    'stepSize': 30, 
    'gamma': 0.1, 
    'modelName': 'model-20', 
    'frame': 1, 
    'modelSavePath': 'F:\\PythonWeb\\myDjango\\app\\model\\cnn_models', 
    'isSaveCsvData': false, 
    'modelVersion': 'v1.0.0', 
    'modelId': 20, 
    'taskId': 'task_85781393', 
    'modelPath': '', 
    'patience': 10, 
    'initMode': 0, 
    'factor': 0.5, 
    'weightDecay': 1e-5, 
    'validateMode': 0
}

const store = useTrainStore(pinia);

store.setTrainData(data);

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

.train-data-container{
   width: 100%;
   height: 70vh;
   display: flex;
   justify-content: center;
   align-items: center;
   background-color: azure;
}


@keyframes rotating {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}
</style>