<template>
    <div class="frame-table-container">
        <el-table class="frame-table" :data="filteredTraffic" :header-cell-class-name="'table-header-cell'" border>
            <el-table-column prop="frameId" label="框架ID" width="100" align="center"/>
            <el-table-column prop="frameName" label="框架名字" width="250" align="center"/>
            <el-table-column prop="frameDescribe" label="框架描述" width="250" align="center"/>
            <el-table-column prop="frameCharacter" label="框架特性" width="490" align="center"/>
            <!-- 操作列 -->
            <el-table-column label="操作" width="150" align="center" fixed="right">
                <template #default="{ row }">
                    <el-button class="delete-btn" type="danger" size="small" :icon="Delete" @click="handleDeleteFrame(row)" circle>
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
    <div class="frame-search-btn-container">
        <el-input class="frame-search-input" v-model="searchQuery" placeholder="输入框架名称/ID搜索" clearable @keyup.enter="handleSearch" />
        <el-button class="frame-search-btn" type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
    </div>
</template>

<script setup lang="ts">
import { Delete, Search } from '@element-plus/icons-vue';
import { onMounted, ref, computed } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { deleteFrame, getFrameData } from '../../api/frame';
import { useFrameStore } from '../../store/frame/frame';
import { type IFrame } from '../../components/frame/frame';

const store = useFrameStore();
const searchQuery = ref('');
const frameList = ref<IFrame[]>([]);

//加载表格数据
const loadData = () => {
    frameList.value = store.getIFrame;
    console.log('store:', frameList.value);
}

const handleSearch = () => {
    // computed属性会自动更新，无需额外操作
};

// 使用computed实现实时搜索
const filteredTraffic = computed(() => {
    if (!searchQuery.value.trim()) {
        return frameList.value;
    }
    const query = searchQuery.value.toLowerCase();
    const searchableKeys: Array<keyof IFrame> = ['frameId','frameName', 'frameCharacter'];    
    return frameList.value.filter(item =>
        searchableKeys.some(
            key => item[key]?.toString().toLowerCase().includes(query)
        )
    )
});

onMounted(async () => {
    await getFrameData();
    loadData();
});

// 删除模型
const handleDeleteFrame = async(row: any) => {
    try {
        await ElMessageBox.confirm(
            `确定要删除 "${row.frameName}" 模型吗?`,
            '警告',
            {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }
        );
        await deleteFrame(row.frameId);
        await getFrameData();
        loadData();
        ElMessage.success('删除'+`${row.frameName}`+'成功');
    } catch(error) {
        ElMessage.info('已取消');
    }
}
</script>

<style>
.table-header-cell{
    font-weight: bold;
    font-size: 14px;
    color: #333;
    background-color: #f5f7fa !important;
}

.dialog{
    text-align: center;
    width: 650px;
    height: 420px;
    /* background-color: rgb(165, 154, 154); */
}

.delete-btn{
    background-color: rgb(255, 0, 8);
}


.table {
  width: 100%;
  text-align: center;
  color: #000;
  background-color: rgb(240, 242, 244);
}

.table-header-cell {
  font-weight: bold;
  font-size: 14px;
  color: #333;
  background-color: #f5f7fa !important;
}

.frame-table-container {
  width: 100%;
  height: 180px;
  display: flex;
  justify-content: center;
  background-color: azure;
}

.table-wrapper {
  width: 100%;
  max-width: 1000px; /* 根据你的实际表格宽度调整 */
  display: flex;
  flex-direction: column;
}

.frame-search-btn-container {
  width: 100%;
  height: 50px;
  display: flex;
  justify-content: right;
  align-items: center;
  background-color: #fafafa;
  margin-top: 15%; /* 调整这个值来控制与表格的间距 */
}

.frame-search-input{
   width: 300px;
   margin-right: 20px;
}

.frame-search-btn{
    width: 100px;
    margin-right: 0%;
}

</style>