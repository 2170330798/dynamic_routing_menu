<template>
    <div class="login-form-container">
        <el-form ref="ruleFormRef" :model="loginForm" :rules="rules" status-icon label-width="auto" class="login-ruleForm">
        <el-form-item prop="userName" >
            <el-icon class="login-input-icon"><User /></el-icon>
            <el-input size="small" v-model="loginForm.userName" style="width: 180px;" autocomplete="off" placeholder="请输入用户名"/>
        </el-form-item>
        <el-form-item prop="password" >
            <el-icon class="login-input-icon"><Lock /></el-icon>
            <el-input type="password" v-model="loginForm.password" style="width: 180px;" size="small" autocomplete="off" placeholder="请输入密码"/>
        </el-form-item>
        <el-form-item class="login-btn-container">
            <el-button class="login-btn" type="primary" size="small" @click="submitForm(ruleFormRef)">
            登入
            </el-button>
            <el-button class="reset-btn" size="small" @click="resetForm(ruleFormRef)">重置</el-button>
        </el-form-item>
        </el-form>
    </div>
</template>
  

  
<script lang="ts" setup>
import { reactive, ref } from 'vue'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import {  tmpUserData, type User } from '../../components/login/login'
import { useAuthStore } from '../../store/auth/auth';
import { login } from '../../api/login';
import { useRouter } from 'vue-router';
const router = useRouter();
const ruleFormRef = ref<FormInstance>();
const store = useAuthStore();
const loginForm = reactive(tmpUserData);

//表单校验规则
const rules = reactive<FormRules <User>>({
    userName: [
        { required: true, message: '请输入用户名/账号/手机号', trigger: 'blur' },
    ],
    password: [
        {required: true, message: '请输入密码', trigger: 'blur'},
    ]
})

const submitForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate(async (valid) => {
      console.log(loginForm);
      if (valid) {
        
        store.setLoginInfo(loginForm);
        const req = await login(store.getLoginInfo);
        if(req == 200){
            store.setIsAuthenticated(true);
            ElMessage.success('登入成功');
            router.push('/'); // 手动跳转到首页
        }else {
            store.setIsAuthenticated(false);
            ElMessage.error('登入失败');
        }
      } else {
        ElMessage.error('登入失败');
      }
    })
}
  
const resetForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.resetFields()
}
</script>

<style lang="css">
.login-form-container {
    width: 250px;
    height: 300px;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 8%;
    box-shadow: 0px 0px 5px 5px rgba(161, 170, 170, 0.387);
}
.login-ruleForm{
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
  
.login-input-icon {
  color: black;
  margin-right: 8px; /* 给图标和输入框之间添加一些间距 */
}
 
.login-btn{
   margin-left: 44.0%;
}

/* 按钮靠右对齐 */
.login-btn-container {
    width: 200px;
    display: flex;
    justify-content: right;
    align-items: center;
    margin-top: 25%;
}
</style>