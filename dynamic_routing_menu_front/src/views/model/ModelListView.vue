<template>       
    <div class="input-container">
        <el-input class="model-search-input" v-model="searchQuery" placeholder="输入模型名称/ID/版本等搜索" clearable @keyup.enter="handleSearch" />
        <el-button class="model-search-btn" type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
    </div>

    <div class="model-table-container">
        <el-table class="model-table" :data="filteredTraffic" :header-cell-class-name="'table-header-cell'" border>
            <el-table-column prop="modelId" label="模型ID" width="80" align="center"/>
            <el-table-column prop="modelName" label="模型名字" width="200" align="center"/>
            <el-table-column prop="frameId" label="模型框架" width="200" align="center"/>
            <el-table-column prop="modelVersion" label="模型版本" width="200" align="center"/>
            <el-table-column prop="loss" label="模型损失率" width="200" align="center"/>
            <el-table-column prop="accuracy" label="模型精度" width="200" align="center"/>
            <el-table-column prop="createDate" label="模型创建时间" width="200" show-overflow-tooltip />
            <el-table-column prop="updateTime" label="模型更新时间" width="200" show-overflow-tooltip />
            <el-table-column prop="modelPath" label="模型路径" width="200" show-overflow-tooltip />
            <el-table-column prop="isApply" label="是否启用模型" width="120" align="center">
                 <template #default="{ row }">
                    <el-switch v-model="row.isApply"
                        :active-value="true"
                        :inactive-value="false"
                        @change="(val: boolean) => handleApplyModel(row, val)"
                        class="model-switch-btn"
                    />
                 </template>
            </el-table-column>
            <!-- 操作列 -->
            <el-table-column label="操作" width="150" align="center" fixed="right">
                <template #default="{ row }">
                    <el-button class="model-switch-btn" type="danger" size="small" :icon="Delete" @click="handleDeleteModel(row)" circle>
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script lang="ts" setup>
import { Search, Delete } from '@element-plus/icons-vue';
import { onMounted, ref, computed } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { useModelStore } from '../../store/model';
import { type IMODEL } from '../../components/model/model';
import { deleteModel, getModelData, updateModel } from '../../api/model';

const store = useModelStore();
const searchQuery = ref('');
const modelList = ref<IMODEL[]>([]);

// 使用computed实现实时搜索
const filteredTraffic = computed(() => {
    if (!searchQuery.value.trim()) {
        return modelList.value;
    }
    const query = searchQuery.value.toLowerCase();
    const searchableKeys: Array<keyof IMODEL> = ['modelId','modelName', 'modelPath', 'modelVersion'];    
    return modelList.value.filter(item =>
        searchableKeys.some(
            key => item[key]?.toString().toLowerCase().includes(query)
        )
    )
});

//加载表格数据
const loadData = () => {
    modelList.value = store.getModelList;
    console.log('modes:', modelList.value);
}

onMounted(async () => {
    await getModelData();
    loadData();
});

const handleSearch = () => {
    // computed属性会自动更新，无需额外操作
};

const handleApplyModel = async (row: IMODEL, newStatus: boolean) => {
  try {
    const action = newStatus ? "启用" : "禁用";
    await ElMessageBox.confirm(
      `确定要${action} "${row.modelName}" 模型吗？`,
      "提示",
      {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }
    );

    // 更新数据
    const updatedModel = { ...row, isApply: newStatus, updateTime: new Date() };
    const req = await updateModel(updatedModel);

    // 重新加载数据
    await getModelData();
    loadData();
    if(req === 200 && action === '启用')
        ElMessage.success(`已${action} ${row.modelName}`);
    else if(req !== 200 && action !== '禁用')
        ElMessage.error('启用模型失败，请确认无其他模型被使用');
    else if(req === 200 && action === '禁用')
        ElMessage.success(`已${action} ${row.modelName}`);
  } catch (error) {
    // 用户取消操作，恢复开关状态
    row.isApply = !newStatus; // 回滚状态
    ElMessage.info("已取消操作");
  }
};


// 删除模型
const handleDeleteModel = async(row: any) => {
    try {
        await ElMessageBox.confirm(
            `确定要删除 "${row.modelName}" 模型吗?`,
            '警告',
            {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }
        );
        await deleteModel(row.modelId);
        await getModelData();
        loadData();
        ElMessage.success('删除'+`${row.modelName}`+'成功');
    } catch(error) {
        ElMessage.info('已取消');
    }
}
</script>

<style>
.model-search-input{
   width: 300px;
   margin-right: 20px;
   margin-left: 1%;
}

.model-search-btn{
    width: 100px;
    margin-right: 45%;
}

.model-table {
    width: 100%;
    height: 100%;
    text-align: center;
    color: #000;
}


.model-table-header-cell{
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

.model-table-container{
     width: 100%;
     height: 410px;
     display: flex;
     justify-content: center;
     align-items: center;
     background-color: azure;
}

.model-dialog{
    text-align: center;
    width: 650px;
    height: 420px;
    /* background-color: rgb(165, 154, 154); */
}

.model-delete-btn{
    background-color: rgb(255, 0, 8);
}

.model-switch-btn{
    width: 32px;
     --el-switch-on-color: #409EFF;
     --el-switch-off-color: #909399;
     margin-right: 15px;
}
</style>