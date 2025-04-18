<template>       
    <div class="input-container">
        <el-input class="search-input" v-model="searchQuery" placeholder="输入菜单名称/路径/标题等搜索" clearable @keyup.enter="handleSearch" />
        <el-button class="search-btn" type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
        <el-button class="add-btn" type="primary" :icon="Edit" @click="openDialog">添加菜单</el-button>
    </div>

    <div class="table-container">
        <el-table class="table" :data="filteredTraffic" :header-cell-class-name="'table-header-cell'" border>
            <el-table-column prop="menuId" label="菜单ID" width="80" align="center"/>
            <el-table-column prop="menuName" label="路由名称" width="200" align="center"/>
            <el-table-column prop="menuPath" label="路由路径" width="200" align="center"/>
            <el-table-column prop="menuTitle" label="菜单标题" width="200" align="center"/>
            <el-table-column prop="menuIcon" label="菜单图标" width="200" align="center"/>
            <el-table-column prop="parentId" label="父菜单" width="200" align="center">
                <template #default="{ row }">
                    {{ row.parentId === -1 ? '顶级菜单' : getParentName(row.parentId)}}
                </template>
            </el-table-column>
            <el-table-column prop="menuComponent" label="组件路径" width="200" show-overflow-tooltip />
            <el-table-column prop="isDirectory" label="菜单类型" width="100" align="center" >
                <template #default="{ row }">
                    <el-tag :type="row.isDirectory ? 'primary' : 'success'">
                                {{ row.isDirectory ? '目录' : '菜单' }}
                    </el-tag>
                </template>
            </el-table-column>
            <!-- 新增操作列 -->
            <el-table-column label="操作" width="150" align="center" fixed="right">
                <template #default="{ row }">
                    <el-button class="edit-btn" type="primary" size="small" :icon="Edit" @click="handleEditMenu(row)">
                    </el-button>
                    <el-button class="delete-btn" type="danger" size="small" :icon="Delete" @click="handleDeleteMenu(row)">
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
    
    <el-dialog class="dialog" v-model="dialogVisible" draggable>
        <FormView ref="menuFormRef" @submit="handleSubmit" @close="handleClose"/>
    </el-dialog>
</template>

<script lang="ts" setup>
import { Search, Edit, Delete } from '@element-plus/icons-vue';
import { onMounted, ref, computed, nextTick } from 'vue';
import { type ICurrentMenuItem } from '../../components/menu/menu';
import { useMenuStore } from '../../store/menu';
import FormView from './FormView.vue';
import { addMenu, deleteMenu, getMenuData, updateMenu } from '../../api/menu';
import { setDynamicRouter } from '../../router';
import { ElMessage, ElMessageBox } from 'element-plus';

const dialogVisible = ref(false)
const store = useMenuStore();
const searchQuery = ref('');
const menu = ref<ICurrentMenuItem[]>([]);
const menuFormRef = ref(); // 添加对子组件的引用

// 打开弹窗（添加模式）
const openDialog = () => {
    store.setIsUpdate(false); // 设置为添加模式
    dialogVisible.value = true;
};

// 使用computed实现实时搜索
const filteredTraffic = computed(() => {
    if (!searchQuery.value.trim()) {
        return menu.value;
    }
    const query = searchQuery.value.toLowerCase();
    const searchableKeys: Array<keyof ICurrentMenuItem> = ['menuId','menuName', 'menuPath', 'menuTitle'];
    return menu.value.filter(item =>
        searchableKeys.some(
            key => item[key]?.toString().toLowerCase().includes(query)
        )
    )
});

//加载表格数据
const loadData = () => {
    menu.value = store.getCurMenu;
    console.log('menu:', menu.value);
}

onMounted(() => {
    loadData();
});

//查找父菜单ID对应的父菜单title
const getParentName = (parentId: number) => {
    return menu.value.find((item)=> item.menuId === parentId)?.menuTitle;
};

const handleSearch = () => {
    // computed属性会自动更新，无需额外操作
};

// 提交处理
const handleSubmit = async (formData: ICurrentMenuItem) => {
    try {
        if(store.getIsUpdate) {
            await updateMenu(formData);
            ElMessage.success('更新成功');
        } else {
            await addMenu(formData);
            ElMessage.success('添加成功');
        }
        await getMenuData();
        loadData();
        await setDynamicRouter(true); // 强制重新加载路由
        dialogVisible.value = false;
    } catch (error) {
        ElMessage.error('操作失败');
        console.error('提交菜单数据失败:', error);
    }
};

// 处理子组件关闭事件
const handleClose = () => {
    store.setIsUpdate(false);
    dialogVisible.value = false;
};

// 删除菜单方法
const handleDeleteMenu = async(row: any) => {
    try {
        await ElMessageBox.confirm(
            `确定要删除菜单 "${row.menuTitle}" 吗?`,
            '警告',
            {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }
        );
        await deleteMenu(row.menuId);
        await getMenuData();
        loadData();
        await setDynamicRouter(true); // 强制重新加载路由
        ElMessage.success('删除成功');
    } catch(error) {
        ElMessage.info('已取消删除');
    }
}

// 编辑菜单方法
const handleEditMenu = (row: ICurrentMenuItem) => {
    store.setIsUpdate(true);
    dialogVisible.value = true;
    nextTick(() => {
        // 编辑模式下传入当前行数据
        menuFormRef.value.initForm(row);
    });
};
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
    height: 100%;
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
.add-btn{
     margin-right: 0%;
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
</style>