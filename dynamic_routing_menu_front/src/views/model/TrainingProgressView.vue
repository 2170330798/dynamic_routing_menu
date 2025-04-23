<!-- src/components/TrainingMonitor.vue -->
<template>
  <div class="training-monitor">
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <span>Training Progress (Task ID: {{ taskId }})</span>
          <el-tag :type="connectionTagType">{{ connectionStatus }}</el-tag>
        </div>
      </template>
      
      <el-steps direction="vertical" :active="activeStep" finish-status="success">
        <el-step 
          v-for="(msg, index) in filteredMessages" 
          :key="index"
          :title="getStepTitle(msg)"
          :description="getStepDescription(msg)"
          :status="getStepStatus(msg)"
        />
      </el-steps>
      
      <el-divider />
      
      <div class="progress-section">
        <div v-if="currentProgress > 0">
          <el-progress 
            :percentage="currentProgress" 
            :status="progressStatus"
            :text-inside="true"
            :stroke-width="24"
          />
          <div class="progress-details">
            <span>Epoch: {{ currentEpoch }}/{{ totalEpochs }}</span>
            <span v-if="currentBatch">Batch: {{ currentBatch }}/{{ totalBatches }} ({{ batchProgress }}%)</span>
            <span>Loss: {{ currentLoss?.toFixed(4) || '-' }}</span>
            <span>Accuracy: {{ currentAccuracy ? (currentAccuracy * 100).toFixed(2) + '%' : '-' }}</span>
          </div>
        </div>
        
        <el-alert
          v-if="lastMessage?.status === 'completed'"
          title="Training Completed"
          type="success"
          :closable="false"
          show-icon
        />
        <el-alert
          v-else-if="lastMessage?.status === 'early_stopping'"
          title="Training Stopped Early"
          type="warning"
          :closable="false"
          show-icon
        />
      </div>
    </el-card>
  </div>
</template>

<script lang="ts">
import { defineComponent, computed, watch, onMounted, onUnmounted } from 'vue'
import { useTrainingWebSocket } from '../../components/train/train'
import { ElMessage } from 'element-plus'

export default defineComponent({
  name: 'TrainingMonitor',
  props: {
    taskId: {
      type: String,
      required: true
    }
  },
  setup(props) {
    const { socket, messages, connectionStatus, connect, disconnect } = useTrainingWebSocket(props.taskId)
    
    const activeStep = computed(() => {
      return messages.value.length
    })
    
    const filteredMessages = computed(() => {
      return messages.value.filter(msg => 
        msg.status === 'started' || 
        msg.status === 'epoch_complete' || 
        msg.status === 'early_stopping' || 
        msg.status === 'completed'
      )
    })
    
    const lastMessage = computed(() => {
      return messages.value.length > 0 ? messages.value[messages.value.length - 1] : null
    })
    
    const currentProgress = computed(() => {
      if (!lastMessage.value) return 0
      return lastMessage.value.progress || 0
    })
    
    const currentEpoch = computed(() => {
      if (!lastMessage.value) return 0
      return lastMessage.value.epoch || 0
    })
    
    const totalEpochs = computed(() => {
      if (!lastMessage.value) return 0
      return lastMessage.value.total_epochs || 0
    })
    
    const currentBatch = computed(() => {
      if (!lastMessage.value) return 0
      return lastMessage.value.batch || 0
    })
    
    const totalBatches = computed(() => {
      if (!lastMessage.value) return 0
      return lastMessage.value.total_batches || 0
    })
    
    const batchProgress = computed(() => {
      if (!lastMessage.value) return 0
      return lastMessage.value.batch_progress || 0
    })
    
    const currentLoss = computed(() => {
      if (!lastMessage.value) return null
      return lastMessage.value.loss
    })
    
    const currentAccuracy = computed(() => {
      if (!lastMessage.value) return null
      return lastMessage.value.accuracy
    })
    
    const progressStatus = computed(() => {
      if (!lastMessage.value) return ''
      if (lastMessage.value.status === 'completed') return 'success'
      if (lastMessage.value.status === 'early_stopping') return 'warning'
      return ''
    })
    
    const connectionTagType = computed(() => {
      switch (connectionStatus.value) {
        case 'connected': return 'success'
        case 'connecting': return 'warning'
        default: return 'danger'
      }
    })
    
    const getStepTitle = (msg: any) => {
      switch (msg.status) {
        case 'started': return 'Training Started'
        case 'epoch_complete': return `Epoch ${msg.epoch} Complete`
        case 'early_stopping': return 'Early Stopping'
        case 'completed': return 'Training Completed'
        default: return msg.status
      }
    }
    
    const getStepDescription = (msg: any) => {
      switch (msg.status) {
        case 'started': return 'Training process has started'
        case 'epoch_complete': 
          return `Loss: ${msg.loss?.toFixed(4) || 'N/A'}, Accuracy: ${msg.accuracy ? (msg.accuracy * 100).toFixed(2) + '%' : 'N/A'}`
        case 'early_stopping': 
          return `Stopped at epoch ${msg.epoch}. Loss: ${msg.loss?.toFixed(4) || 'N/A'}`
        case 'completed': 
          return `Final loss: ${msg.loss?.toFixed(4) || 'N/A'}, Accuracy: ${msg.accuracy ? (msg.accuracy * 100).toFixed(2) + '%' : 'N/A'}`
        default: return msg.message
      }
    }
    
    const getStepStatus = (msg: any) => {
      switch (msg.status) {
        case 'completed': return 'success'
        case 'early_stopping': return 'warning'
        default: return 'process'
      }
    }
    
    onMounted(() => {
      connect()
      
      // 错误处理
      watch(connectionStatus, (status) => {
        if (status === 'disconnected' && messages.value.length === 0) {
          ElMessage.error('Failed to connect to training monitor')
        }
      })
    })
    
    onUnmounted(() => {
      disconnect()
    })
    
    return {
      socket,
      messages,
      connectionStatus,
      activeStep,
      filteredMessages,
      lastMessage,
      currentProgress,
      currentEpoch,
      totalEpochs,
      currentBatch,
      totalBatches,
      batchProgress,
      currentLoss,
      currentAccuracy,
      progressStatus,
      connectionTagType,
      getStepTitle,
      getStepDescription,
      getStepStatus
    }
  }
})
</script>

<style scoped>
.training-monitor {
  margin: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.progress-section {
  margin-top: 20px;
}

.progress-details {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
  color: #666;
  font-size: 14px;
}
</style>