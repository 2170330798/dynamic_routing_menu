<template>
<div class="table-container">
        <el-table class="table" :data="filteredTraffic" :header-cell-class-name="'table-header-cell'" border>
            <el-table-column prop="frameId" label="框架ID" width="80" align="center"/>
            <el-table-column prop="frameName" label="框架名字" width="200" align="center"/>
            <el-table-column prop="frameDescribe" label="框架描述" width="200" align="center"/>
            <el-table-column prop="frameCharacter" label="框架特性" width="200" align="center"/>
            <!-- 操作列 -->
            <el-table-column label="操作" width="150" align="center" fixed="right">
                <template #default="{ row }">
                    <el-button class="delete-btn" type="danger" size="small" :icon="Delete" @click="handleDeleteFrame(row)" circle>
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script setup lang="ts">
import { Delete } from '@element-plus/icons-vue';
import { onMounted, ref, computed } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { deleteFrame, getFrameData } from '../../api/frame';
import { useFrameStore } from '../../store/frame/frame';
import type { IFrame } from '../../components/frame/frame';

const store = useFrameStore();
const searchQuery = ref('');
const frameList = ref<IFrame[]>([]);

//加载表格数据
const loadData = () => {
    frameList.value = store.getIFrame;
    console.log('menu:', frameList.value);
}


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

// const handleEdit = async (row: IFrame) => {
//   try {
//     await ElMessageBox.confirm(
//       `确定要删除"${row.frameName}" 吗？`,
//       "提示",
//       {
//         confirmButtonText: "确定",
//         cancelButtonText: "取消",
//         type: "warning",
//       }
//     );
//     const frame = {...row};
//     // 更新数据
//     await updateFrame(frame);

//     // 重新加载数据
//     await getFrameData();
//     loadData();

//     ElMessage.success(`已更新 ${row.frameName}`);
//   } catch (error) {
//     // 用户取消操作，恢复开关状态
//     ElMessage.info("已取消操作");
//   }
// };


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
.search-input{
   width: 300px;
   margin-right: 20px;
   margin-left: 1%;
}

.search-btn{
    width: 100px;
    margin-right: 45%;
}

.table {
    width: 1180px;
    height: 300px;
    margin-bottom: 15%;
    text-align: center;
    color: #000;
}


.table-header-cell{
    font-weight: bold;
    font-size: 14px;
    color: #333;
    background-color: #f5f7fa !important;
}

.input-container{
    width: 100%;
    height: 50px;
    display: flex;
    align-items: center;
    /* background-color: rgb(227, 237, 237); */
}

.table-container{
     width: 100%;
     height: 410px;
     display: flex;
     justify-content: center;
     align-items: center;
     background-color: azure;
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

.switch-btn{
    width: 32px;
     --el-switch-on-color: #409EFF;
     --el-switch-off-color: #909399;
     margin-right: 15px;
}
</style>