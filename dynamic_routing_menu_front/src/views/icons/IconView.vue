<template>
    <el-select
      v-model="selectedIcon"
      popper-class="icon-select"
      placeholder="请选择菜单图标"
      filterable
      clearable
      @change="$emit('update:modelValue', selectedIcon)"
    >
      <el-option
        v-for="icon in icons"
        :key="icon"
        :label="icon"
        :value="icon"
      >
        <div class="icon-option">
          <el-icon><component :is="icon" /></el-icon>
          <span>{{ icon }}</span>
        </div>
      </el-option>
    </el-select>
  </template>
  
  <script lang="ts" setup>
  import { ref, watch } from 'vue'
  import * as ElementPlusIconsVue from '@element-plus/icons-vue'
  
  const props = defineProps({
    modelValue: String
  })
  
  const emit = defineEmits(['update:modelValue'])
  
  const selectedIcon = ref(props.modelValue)
  const icons = Object.keys(ElementPlusIconsVue)
  
  watch(() => props.modelValue, (newVal) => {
    selectedIcon.value = newVal
  })
  </script>
  
  <style scoped>
  .icon-option {
    display: flex;
    align-items: center;
    gap: 8px;
  }
  </style>