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
            <span>训练数据：</span>
            {{ train_data }}
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
        <div v-if="currentMetrics" class="metrics-section">
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
        </div>
        
        <!-- 控制台输出部分 -->
        <el-divider />
        <div class="console-output">
          <pre>{{ consoleOutput }}</pre>
        </div>
      </el-card>
    </div>
</template>
  
<script setup lang="ts">
import { computed, onUnmounted, ref } from 'vue'
import { useTrainingWebSocket } from '../../components/websocket/websocket'
import { trainModel } from '../../api/train'
import { ElMessage } from 'element-plus'
import { disconnectWebSocket } from '../../components/progress/progress';
const train_data = {
            csvSourcePath: "F:\\PythonWeb\\myDjango\\app\\dataset\\train_dataset_03.csv",//训练数据集csv文件路径
            csvDestPath: "",//保存打乱数据集csv文件路径
            labelColumn: "Laabel",//Label标签列
            batchSize: 32,//训练数据批次大小
            rows: 3000,//读取csv多少行
            numEpochs: 2,//训练次数
            lr: 0.01,//学习率
            stepSize: 32,//动态更新学习率的步长
            gamma: 0.1,//学习率衰减比率
            modelId: 1,//模型ID
            modelName: "model_test",//模型名字
            modelVersion: "v1.0.0",//模型版本
            frameId: 1,//框架ID
            modelSavePath: "F:\\PythonWeb\\myDjango\\app\\model",//模型保存路径
            isSaveCsvPath: false, //是否保存打乱的数据集
            taskId: "task_123" //任务进程ID
}
const taskId = "task_"+Date.now();

const { messages, connectionStatus, connect } = useTrainingWebSocket(taskId);
  
// 连接WebSocket
connect()
  
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

const consoleOutput = computed(() => {
    return messages.value
      .map(msg => {
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
  


// 在 setup 脚本中添加：
onUnmounted(() => {
    disconnectWebSocket()
})
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
    background-color: #f5f5f5;
    padding: 10px;
    border-radius: 4px;
    font-family: monospace;
    white-space: pre-wrap;
    max-height: 300px;
    overflow-y: auto;
}
</style>