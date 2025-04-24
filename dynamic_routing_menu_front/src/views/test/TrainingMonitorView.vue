<!-- src/components/TrainingMonitor.vue -->
<template>
    <div class="training-container">
        <el-card shadow="hover">
        <template #header>
            <div class="test-header-content">
                <h2>模型训练控制台</h2>
            </div>
        </template> 

        <div v-if="trainingTaskId">
            <TrainingMonitor :task-id="trainingTaskId" />
        </div>
        
        <div v-else class="empty-state">
            <div class="train_data">
                <span>训练数据：</span>
                {{ train_data }}
            </div>
            <el-tag :type="connectionTagType">WebSocket: {{ connectionStatus }}</el-tag>
            <el-empty description="请先开始训练任务">
            <el-button type="primary" @click="startTraining">
                开始新训练
            </el-button>
            </el-empty>
        </div>
        </el-card>
    </div>
    <div class="training-monitor">
      <el-card shadow="hover">
        <template #header>
          <div class="card-header">
            <span>Training Progress (Task ID: {{ taskId }})</span>
            <el-tag :type="connectionTagType">{{ connectionStatus }}</el-tag>
          </div>
        </template>
        
        <!-- 控制台输出部分 -->
        <el-divider />
        <div class="console-output">
            <h1>Message:</h1>
                <p>id:  {{ messages?.id }}</p>
        </div>
      </el-card>
    </div>
</template>
  
<script setup lang="ts">
import { computed, onUnmounted, ref } from 'vue'
import { trainModel } from '../../api/train'
import { ElMessage } from 'element-plus'
import { train_data } from '../../components/train/train';
import type {TrainingMessage} from '../../components/websocket/websocket'
const isTraining = ref(false)
const trainingTaskId = ref<string | null>(null)
const taskId = "task_"+Date.now();

const messages = ref<TrainingMessage>()
const connectionStatus = ref<'disconnected' | 'connecting' | 'connected'>('disconnected')
import { WS_HOST } from '../../vite-env.d'


const url = `ws://${WS_HOST}/ws/training/${taskId}/`;
connectionStatus.value = 'connecting';
const socket = new WebSocket(url);
  

const startTraining = async () => {
    try {
      isTraining.value = true
      const res = await trainModel(train_data);
      if(res == 200)
          ElMessage.success('训练任务完成');
      else
          ElMessage.error('启动训练失败');
      trainingTaskId.value = res.taskId;
    } catch (error) {
      disconnect(socket);
      ElMessage.error('启动训练失败');
      isTraining.value = false;
    }
}

onUnmounted(() => { 
    disconnect(socket) 
})

const connectionTagType = computed(() => {
    switch (connectionStatus.value) {
      case 'connected': return 'success'
      case 'connecting': return 'warning'
      default: return 'danger'
  }
})

// 添加连接超时
const timeout = setTimeout((socket: { close: () => void; } | null | undefined) => {
      if (connectionStatus.value === 'connecting') {
        console.error('WebSocket connection timeout');
        disconnect(socket);
      }
}, 1000)
    
socket.onopen = () => {
      clearTimeout(timeout);
      connectionStatus.value = 'connected';
      console.log('WebSocket connected');
}
    
socket.onmessage = (event) => {
    try {
            messages.value = JSON.parse(event.data);
        } catch (error) {
            console.error('Error parsing WebSocket message:', error);
        }
}

socket.onclose = () => {
      connectionStatus.value = 'disconnected';
      console.log('WebSocket disconnected');
}
    
socket.onerror = (error) => {
      console.error('WebSocket error:', error);
      connectionStatus.value = 'disconnected';
}

  
const disconnect = (socket: { close: () => void; } | null | undefined) => {
    if (socket) {
      socket.close();
      socket = null;
    }
}
</script>
  
<style scoped>
.training-container {
    padding: 20px;
    width: 100%;
    margin: 0 auto;
}
  
.test-header-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
  
.empty-state {
    padding: 40px 0;
}

.training-monitor {
    margin: 20px;
}
  
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
  
.metrics-section {
    margin-bottom: 20px;
}
  
.console-output {
    width: 100%;
    height: 500px;
    background-color: #d1dfdd;
    padding: 10px;
    border-radius: 4px;
    font-family: monospace;
    white-space: pre-wrap;
    overflow-y: auto;
}

.train_data{
    width: 150px;
    height: 600px;
    background-color: rgba(239, 246, 247, 0.36);
}
</style>