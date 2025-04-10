<template>
    <el-select v-model="selectedIcon" popper-class="icon-select" :teleported="false" filterable placeholder="请选择图标" clearable>
      <el-option v-for="(icon,index) in iconList" :key="index" :label="icon" :value="icon">
        <el-tooltip :content="icon">
          <component :is="icon" style="width:auto; height: 80%;"/>
        </el-tooltip>
      </el-option>
      <template #prefix>
        <component :is="selectedIcon"/>
      </template>
    </el-select>
  </template>
   
<script lang="ts" setup>
import { getCurrentInstance, computed } from 'vue'

const selectedIcon = defineModel()
const instance = getCurrentInstance()

// 安全获取全局图标列表
const iconList = computed(() => {
  return instance?.appContext.config.globalProperties.$icons || []
})
</script>

<style lang="scss" scoped>
:deep(.icon-select) {
    max-width: 500px;
    ul.el-select-dropdown__list{
        display: flex !important;
        flex-wrap: wrap;
        overflow: hidden;

        li.el-select-dropdown__item{ 
            width: 20%;
            padding: 5px 0;
            text-align: center;
        }
    }
}
</style>