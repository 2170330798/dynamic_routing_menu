<template>
  <div class="log-container">
      <div class="log-header-container">
        <h1>日志管理</h1>
      </div>
    
      <div class="log-table-container">
        <FormView />
      </div>
    
      <div class="traffic-pagination-container">
        <el-pagination
          class="traffic-pagination"
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 30, 50]"
          :background="background"
          layout="sizes, prev, pager, next"
          :total="total"
          @update:page-size="handleSizeChange"
          @update:current-page="handleCurrentChange"
        />
      </div>
  </div>
</template>
  
<script lang="ts" setup>
import FormView from './FormView.vue';
import { ref, onMounted } from 'vue';
import { getTrafficDataByPage } from '../../api/traffic';
import { useTrafficStore } from '../../store/traffic';

const currentPage = ref(1);  // 默认从第1页开始
const pageSize = ref(10);   // 默认每页10条
const background = ref(true);
const total = ref(0);       // 默认总数0
const store = useTrafficStore();

const loadData = async () => {
  try {
    await getTrafficDataByPage(currentPage.value, pageSize.value);
    
    // 从 store 获取最新数据更新 total
    const pages = store.getPage;
    if (pages) {
      total.value = pages.total;
    }
    
  } catch (error) {
    console.error('获取数据失败:', error);
  }
};

const refreshKey = ref(0);

const handleSizeChange = (size: number) => {
  pageSize.value = size;
  currentPage.value = 1; // 重置为第一页
  loadData();
  // 通过改变key强制重新渲染FormView组件
  refreshKey.value++;
};

const handleCurrentChange = (page: number) => {
  currentPage.value = page;
  loadData();
  // 通过改变key强制重新渲染FormView组件
  refreshKey.value++;
};

onMounted(() => {
  loadData();
});

</script>

<style lang="css">
.log-container{
   width: 100%;
   height: 95%;
}

.log-header-container{
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: rgb(216, 224, 224);
}

.log-table-container{
    width: 100%;
    height: 70%;
    display: flex;
    justify-content: center;
}

.traffic-pagination-container{
    width: 100%;
    height: 50px;
    display: flex;
    margin-bottom: 0%;
    justify-content: right;
    align-items: center;
    background-color: rgb(241, 246, 246);
}

.traffic-pagination{
    margin-bottom: 0%;
}
</style>