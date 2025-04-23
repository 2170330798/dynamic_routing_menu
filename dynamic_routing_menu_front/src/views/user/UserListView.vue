<template>       
    <div class="user-input-container">
        <el-input class="user-search-input" v-model="searchQuery" placeholder="输入用户名称/ID/电话搜索" clearable @keyup.enter="handleSearch" />
        <el-button class="user-search-btn" type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
        <el-button class="user-add-btn" type="primary" :icon="Edit" @click="openDialog">添加</el-button>        
    </div>

    <div class="user-table-container">
        <el-table class="user-table" :data="filteredTraffic" :header-cell-class-name="'table-header-cell'" border>
            <el-table-column prop="userId" label="用户ID" width="200" align="center"/>
            <el-table-column prop="userName" label="用户名字" width="200" align="center"/>
            <el-table-column prop="phone" label="电话" width="200" align="center"/>
            <el-table-column prop="password" label="用户密码" width="200" align="center"/>
            <el-table-column prop="role" label="用户角色" width="200" align="center">
                <template #default="{row}">
                    {{ mapRole(row.role) }}
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="240" align="center"/>
            <el-table-column prop="updateTime" label="更新时间" width="240" align="center"/>
            <!-- 操作列 -->
            <el-table-column label="操作" width="150" align="center" fixed="right">
                <template #default="{ row }">
                    <el-button class="user-edit-btn" type="primary" size="small" :icon="Edit" @click="handleEditMenu(row)" ></el-button>
                    <el-button class="user-delete-btn" type="danger" size="small" :icon="Delete" @click="handleDeleteUser(row)" >
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
    <el-dialog class="user-dialog" v-model="dialogVisible" draggable>
        <FormView  :currentUser="currentUser" @submit="handleSubmit"  @close="dialogVisible = false" />
    </el-dialog>
</template>

<script lang="ts" setup>
import { Search, Delete, Edit } from '@element-plus/icons-vue';
import { computed, onMounted, ref } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { addUser, deleteUser, getUser, updateUser } from '../../api/user';
import { useUserStore } from '../../store/user/user';
import { tmpUserData, type User } from '../../components/login/login';
import FormView from './FormView.vue';

const store = useUserStore();
const searchQuery = ref('');
const userList = ref<User[]>([]);
const dialogVisible = ref(false);
const currentUser = ref<User>({...tmpUserData});

// 使用computed实现实时搜索
const filteredTraffic = computed(() => {
    if (!searchQuery.value.trim()) {
        return userList.value;
    }
    const query = searchQuery.value.toLowerCase();
    const searchableKeys: Array<keyof User> = ['userId','userName', 'phone'];    
    return userList.value.filter(item =>
        searchableKeys.some(
            key => item[key]?.toString().toLowerCase().includes(query)
        )
    )
});
//加载表格数据
const loadData = () => {
    userList.value = store.getUserData;
    console.log('users:', userList.value);
}

// 打开弹窗（添加模式）
const openDialog = () => {
    store.setIsUpdate(false); // 设置为添加模式
    currentUser.value = {
        ...tmpUserData,
        createTime: getCurrentFormattedTime(),
        updateTime: getCurrentFormattedTime()
    }; // 重置为初始值
    dialogVisible.value = true;
};

onMounted(async () => {
    await getUser();
    loadData();
});

const handleSearch = () => {
    // computed属性会自动更新，无需额外操作
};

const getCurrentFormattedTime = (): string => {
  const now = new Date();
  
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, '0');
  const day = String(now.getDate()).padStart(2, '0');
  
  const hours = String(now.getHours()).padStart(2, '0');
  const minutes = String(now.getMinutes()).padStart(2, '0');
  
  return `${year}-${month}-${day} : ${hours}:${minutes}`;
}

const mapRole = (role: boolean) => {
      //console.log('role:', role);
      if(role)
            return "管理员";
      else
            return "员工";
}

// 提交处理
const handleSubmit = async (formData: User) => {
    try {
        if(store.getIsUpdate) {
            const req = await updateUser(formData);
            if(req == 200)
                ElMessage.success('更新成功');
            else
                ElMessage.error('更新失败');
        } else {
            const req = await addUser(formData);
            if(req == 200)
                ElMessage.success('添加成功');
            else
                ElMessage.error('添加失败');
        }
        await getUser();
        loadData();
        dialogVisible.value = false;
    } catch (error) {
        ElMessage.error('操作失败');
        console.error('提交菜单数据失败:', error);
    }
};


// 编辑菜单方法
const handleEditMenu = (row: User) => {
    store.setIsUpdate(true);
    dialogVisible.value = true;
    currentUser.value = {
        ...row,
        updateTime: getCurrentFormattedTime()
    }; // 复制当前行数据
    
};

// 删除模型
const handleDeleteUser = async(row: any) => {
    try {
        await ElMessageBox.confirm(
            `确定要删除 "${row.userName}" 吗?`,
            '警告',
            {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }
        );
        await deleteUser(row.userId);
        await getUser();
        loadData();
        ElMessage.success('删除'+`${row.userName}`+'成功');
    } catch(error) {
        ElMessage.info('已取消');
    }
}
</script>

<style>
.user-search-input{
   width: 300px;
   margin-right: 20px;
   margin-left: 1%;
}

.user-search-btn{
    width: 100px;
    margin-right: 50%;
}

.user-table {
    width: 100%;
    height: 100%;
    text-align: center;
    color: #000;
}


.user-table-header-cell{
    font-weight: bold;
    font-size: 14px;
    color: #333;
    background-color: #f5f7fa !important;
}

.user-input-container{
    width: 100%;
    height: 50px;
    display: flex;
    align-items: center;
    /* background-color: rgb(227, 237, 237); */
}

.user-table-container{
     width: 100%;
     height: 410px;
     display: flex;
     justify-content: center;
     align-items: center;
     background-color: azure;
}

.user-dialog{
    text-align: center;
    width: 650px;
    height: 420px;
    /* background-color: rgb(165, 154, 154); */
}

.user-delete-btn{
    background-color: rgb(255, 0, 8);
}

/* .user-edit-btn{

} */

.user-pagination-container{
    width: 100%;
    height: 50px;
    margin-bottom: 10%;
    display: flex;
    justify-content: right;
    background-color: rgb(241, 246, 246);
}
</style>