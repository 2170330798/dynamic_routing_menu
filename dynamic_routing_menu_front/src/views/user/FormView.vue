<template>
    
    <div class="user-header-container">
         <h1 class="user-text-container">用户管理</h1>
    </div>
    <el-form  ref="formRef" @validate="onFormValidate" class="user-el-form" :rules="rules" :model="user" label-width="auto">
    <el-row>
        <el-col :span="12">
            <el-form-item label="用户ID" prop="userId">
                <el-input v-model="user.userId" placeholder="输入用户ID"/>
            </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="用户名字" prop="userName">
                <el-input v-model="user.userName"  placeholder="输入用户名字"/>
            </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="用户电话" prop="phone">
                <el-input v-model="user.phone" placeholder="输入电话"/>
            </el-form-item>
        </el-col>
        <el-col :span="12">
            <el-form-item label="用户密码" prop="password">
                <el-input v-model="user.password" placeholder="输入密码 "/>
            </el-form-item> 
        </el-col>
        <el-col :span="12">
            <el-form-item label="用户角色" prop="role">
              <el-select v-model="user.role" placeholder="Select" style="width: 240px">
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"/>
              </el-select>
            </el-form-item>
        </el-col>
    </el-row> 
       
    <el-form-item class="user-btn-group1">
        <el-button class="user-submit-btn" type="primary" @click="onSubmit" :disabled="isSubmitDisabled">
          {{  store.getIsUpdate ? "更新":"添加"  }}
        </el-button>
        <el-button class="user-cancel-btn" @click="onClose">取消</el-button>
    </el-form-item>
    </el-form>

</template>

<script lang="ts" setup>
import { reactive, ref, watch } from 'vue';
import { ElMessage, type FormRules } from 'element-plus';
import { onMounted } from 'vue';
import { type User } from '../../components/login/login';
import { useUserStore } from '../../store/user/user';

const store = useUserStore();

const props = defineProps<{
    currentUser: User
}>();

const user = reactive<User>({...props.currentUser});

const formRef = ref(); // 添加表单引用
const isSubmitDisabled = ref(true); // 初始禁用状态
const options = [
  {
    value: 1,
    label: "管理员"
  },
  {
    value: 0,
    label: "普通员工"
  },
]
// 定义验证状态对象
const validationStates = reactive({
    userId: false,
    userName: false,
    phone: false, 
    password: false, 
    role: false
});

// 定义验证规则
const rules: FormRules = {
    userId: [
    {
      validator: (_rule, value, callback) => {
          if (!user.userId && !value) {
            validationStates.userId = false;
            callback(new Error('请输入用户ID'));
          } else {
            validationStates.userId = true;
            callback();
          }
      }
    }
    ],
    userName: [
    {
      validator: (_rule, value, callback) => {
          if (!user.userName && !value) {
            validationStates.userName = false;
            callback(new Error('请输入用户名'));
          } else {
            validationStates.userName = true;
            callback();
          }
      }
    }
    ],
    phone: [
    {
      validator: (_rule, value, callback) => {
          if (!user.phone && !value) {
            validationStates.phone = false;
            callback(new Error('请输入电话号码'));
          } else {
            validationStates.phone = true;
            callback();
          }
      }
    }
    ],
    password: [
    {
      validator: (_rule, value, callback) => {
          if (!user.password && !value) {
            validationStates.password = false;
            callback(new Error('请输入密码'));
          } else {
            validationStates.password = true;
            callback();
          }
      }
    }
    ],
    role: [
      {
        required: true,
        message: '请选择角色',
        trigger: 'change'
      }
    ]
};

onMounted(() => {
  formRef.value?.validate().then(() => {
        updateSubmitButtonState();
  }).catch(() => {
        updateSubmitButtonState();
  });
});
// 表单验证事件处理
const onFormValidate = (prop: keyof typeof validationStates, isValid: boolean) => {
  validationStates[prop] = isValid;
   // 特殊处理role字段
   if (prop === 'role') {
    validationStates.role = !!user.role;
  }
  updateSubmitButtonState();
};

// 更新提交按钮状态
const updateSubmitButtonState = () => {
    // 检查所有必填字段是否都通过了验证
    const allValid = Object.values(validationStates).every(state => state);
    isSubmitDisabled.value = !allValid;
}

console.log(validationStates);

const emit = defineEmits<{
    (e: 'submit', data: User): void;
    (e: 'close'): void;
}>();

const onSubmit = async () => {
    // 确保role值已设置
    if (user.role === undefined) {
        ElMessage.warning('请选择角色');
        return;
    }
    emit('submit', user);
};

const onClose = () => {
    emit('close');
};

// 监听currentUser变化，当编辑不同行时更新表单数据
watch(() => props.currentUser, (newVal) => {
    Object.assign(user, newVal);
}, { deep: true })

</script>


<style lang="css">
.user-form-container {
  width: 600px;
  height: 350px;
  /* background-color: rgb(184, 191, 191); */
  display: flex;
  flex-direction: column; /* 改为垂直排列 */
  justify-content: flex-start; /* 从顶部开始排列 */
  align-items: center; /* 水平居中 */
}

.user-header-container {
  width: 100%;
  height: 50px;
  /* background-color: bisque; */
  display: flex;
  justify-content: center;
  align-items: center;
  /* border-bottom: 1px solid rgb(175, 176, 239); */
}

.user-el-form{
  margin-top: 5%;
  max-width: 600px;
}

.user-btn-group1{
  width: 100%;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 20px;
  /* background-color: rgb(255, 255, 255); */
}

.user-submit-btn{
  margin-left: 75%;
}
</style>