<!-- src/components/TrainingMonitor.vue -->
<template>
    <div class="training-container">
        <el-card shadow="hover">
        <template #header>
            <div class="test-header-content">
                <h2>模型训练控制台</h2>
                <div class="controls"> 
                    
                    <el-button type="primary" :loading="isTraining" @click="startTraining" :disabled="isTraining">
                    开始训练
                    </el-button>
                    <!-- <el-button 
                    type="warning" 
                    @click="stopTraining"
                    :disabled="!isTraining"
                    >
                    停止训练
                    </el-button> -->
                </div>
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
            <el-tag :type="connectionTagType">
                WebSocket: {{ connectionStatus }}
                </el-tag>
                <el-button 
                v-if="connectionStatus === 'disconnected'" 
                @click="reconnect"
                >
                重新连接
            </el-button>
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
        
        <!-- 指标展示部分 -->
        <!-- <div v-if="currentMetrics" class="metrics-section">
          <el-descriptions :column="2" border>
            <el-descriptions-item label="Epoch">
              {{ currentMetrics.epoch }}/{{ currentMetrics.total_epochs }}
            </el-descriptions-item>
            <el-descriptions-item label="Progress">
              <el-progress :percentage="currentMetrics.progress" />
            </el-descriptions-item>
            <el-descriptions-item label="Loss">
              <el-tag :type="getLossTagType(currentMetrics.loss)">
                {{ currentMetrics.loss?.toFixed(4) || 'N/A' }}
              </el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="Accuracy">
              <el-tag :type="getAccuracyTagType(currentMetrics.accuracy)">
                {{ currentMetrics.accuracy ? (currentMetrics.accuracy * 100).toFixed(2) + '%' : 'N/A' }}
              </el-tag>
            </el-descriptions-item>
          </el-descriptions>
        </div> -->
        
        <!-- 控制台输出部分 -->
        <el-divider />
        <div class="console-output">
            <h1>Message:</h1>
                {{ consoleOutput }}
        </div>
      </el-card>
    </div>
</template>
  
<script setup lang="ts">
import { computed, onUnmounted, ref, watch } from 'vue'
import { useTrainingWebSocket } from '../../components/websocket/websocket'
import { trainModel } from '../../api/train'
import { ElMessage } from 'element-plus'
import { train_data } from '../../components/train/train';

const taskId = "task_"+Date.now();

const { messages, connectionStatus, connect, disconnect } = useTrainingWebSocket(taskId);
  
// 连接WebSocket
connect()

// 调试：监听 messages 变化
watch(messages, (newVal) => {
  console.log('messages updated:', newVal)
}, { deep: true })

// 添加重连方法
const reconnect = () => {
  disconnect();
  connect();
};

// 在组件中添加这个工具函数
const findLast = <T>(arr: T[], predicate: (item: T) => boolean): T | undefined => {
  for (let i = arr.length - 1; i >= 0; i--) {
    if (predicate(arr[i])) {
      return arr[i]
    }
  }
  return undefined
}

// 然后修改 currentMetrics 计算属性
const currentMetrics = computed(() => {
  return findLast(messages.value, msg => msg.status === 'epoch_summary')
})

// 修改onUnmounted
onUnmounted(() => { 
    disconnect() 
})

const consoleOutput = computed(() => {
  return messages.value
    .map(msg => {
      if (msg.status === 'error') return `[ERROR] ${msg.message}`
      if (msg.status === 'epoch_summary') {
        return `[Epoch ${msg.epoch}/${msg.total_epochs}] Loss: ${msg.loss?.toFixed(4)}, Acc: ${msg.accuracy ? (msg.accuracy * 100).toFixed(2) + '%' : 'N/A'}`
      }
      return msg.message
    })
    .join('\n')
})
  
const connectionTagType = computed(() => {
    switch (connectionStatus.value) {
      case 'connected': return 'success'
      case 'connecting': return 'warning'
      default: return 'danger'
  }
})
  
const getLossTagType = (loss?: number) => {
    if (loss === undefined) return ''
    if (loss < 0.1) return 'success'
    if (loss < 0.5) return 'warning'
    return 'danger'
}
  
const getAccuracyTagType = (accuracy?: number) => {
    if (accuracy === undefined) return ''
    if (accuracy > 0.9) return 'success'
    if (accuracy > 0.7) return 'warning'
    return 'danger'
}

const isTraining = ref(false)
const trainingTaskId = ref<string | null>(null)

const startTraining = async () => {
    try {
      isTraining.value = true
      const response = await trainModel(train_data);
      trainingTaskId.value = response.taskId;
      ElMessage.success('训练任务已启动');
    } catch (error) {
      ElMessage.error('启动训练失败');
      isTraining.value = false;
    }
}
</script>
  
<style scoped>
.training-container {
    padding: 20px;
    max-width: 1200px;
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