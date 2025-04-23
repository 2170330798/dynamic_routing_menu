<template>
    
        <div class="menu-header-container">
             <h1 class="text-container">菜单管理</h1>
        </div>
        <el-form  ref="formRef" @validate="onFormValidate" class="menu-el-form" :rules="rules" :model="menu" label-width="auto">
        <el-row>
            <el-col :span="12">
                <el-form-item label="菜单名字" prop="menuName">
                    <el-input v-model="menu.menuName" placeholder="输入菜单名字"/>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="菜单标题" prop="menuTitle">
                    <el-input v-model="menu.menuTitle" placeholder="输入菜单标题"/>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="路由路径" prop="menuPath">
                    <el-input v-model="menu.menuPath" placeholder="输入路由路径"/>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="菜单VUE组件" prop="menuComponent">
                    <el-input v-model="menu.menuComponent" placeholder="请输入菜单组件路径" />
                </el-form-item>
            </el-col>
            
            <el-col :span="12">
                <el-form-item label="菜单标识" prop="menuId">
                    <el-input v-model="menu.menuId" placeholder="输入菜单唯一标识" />
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="父菜单" prop="parentId">
                    <el-select v-model="menu.parentId" placeholder="请选择父菜单">
                        <el-option v-if="menu.isDirectory" label="顶级菜单" :value="-1"/>
                        <el-option 
                            v-for="item in store.getCurMenu.filter(item => item.isDirectory)" 
                            v-else
                            :key="item.menuId"
                            :label="item.menuTitle" 
                            :value="item.menuId"
                        />
                    </el-select>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="菜单图标">
                    <IconView v-model="menu.menuIcon"/>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="菜单类型">
                    <el-radio-group v-model.number="menu.isDirectory">
                        <el-radio :value="true">父菜单</el-radio>
                        <el-radio :value="false">子菜单</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-col>
        </el-row>
            <el-form-item class="btn-group1">
                <el-button class="add-btn" type="primary" @click="onSubmit" :disabled="isSubmitDisabled">添加</el-button>
                <el-button class="cancel-btn" @click="onClose">取消</el-button>
            </el-form-item>
        </el-form>

</template>

<script lang="ts" setup>
import { reactive, ref, watch } from 'vue';
import { tempMenuList, type IMenuList } from '../../components/menu/menu';
import { useMenuStore } from '../../store/menu';
import pinia from '../../store';
import type { FormRules } from 'element-plus';

const menu = reactive<IMenuList>(tempMenuList);
const store = useMenuStore(pinia);

//存储当前ID
const tmpId:number[] = [];

store.getCurMenu.forEach((item) => {
     tmpId.push(item.menuId);
})
//console.log('tempId',tmpId);

const formRef = ref(); // 添加表单引用
const isSubmitDisabled = ref(true); // 初始禁用状态

// 定义验证状态对象
const validationStates = reactive({
  menuName: false,
  menuTitle: false,
  menuIcon: true,  
  menuPath: false,
  menuComponent: false,
  menuId: false,
  parentId: false,
});

// 定义验证规则
const rules: FormRules = {
  menuName: [
    {
      validator: (_rule, value, callback) => {
          if (!menu.menuName && !value) {
            validationStates.menuName = false;
            callback(new Error('请输入菜单名字'));
          } else {
            //ValidForm.validName = true,
            validationStates.menuName = true;
            callback();
          }
      }
    }
  ],
  menuTitle: [
    {
      validator: (_rule, value, callback) => {
          if (!menu.menuTitle && !value) {
            validationStates.menuName = false;
            callback(new Error('请输入菜单标题'));
          } else {
            validationStates.menuName = true;
            callback();
          }
      }
    }
  ],
  menuPath: [
    {
      validator: (_rule, value, callback) => {
          if (!menu.menuName && !value) {
            validationStates.menuName = false;
            callback(new Error('请输入菜单路由路径'));
          } else {
            validationStates.menuName = true;
            callback();
          }
      }
    }
  ],
  menuComponent: [
    { 
      validator: (_rule, value, callback) => {
        if (!menu.isDirectory && !value) {
          validationStates.menuName = false;
          callback(new Error('请输入子菜单组件路径'));
        } else {
          validationStates.menuName = true;
          callback();
        }
      },
    }
  ],
  menuId: [
    { required: true, message: '请输入菜单唯一标识', trigger: 'blur' },
    { type: 'number', message: '菜单标识必须为数字', transform: (value) => Number(value), trigger: 'blur'},
    { 
      validator: (_rule, value, callback) => {
        const menuId = Number(value);
         
        const existingMenu = tmpId.find(item => (item == menuId))
        if (menuId <= 0) {
          validationStates.menuName = false;
          callback(new Error('菜单标识必须大于0'));
        } else if(existingMenu && !store.getIsUpdate) {
          validationStates.menuName = false;
          callback(new Error('菜单ID已存在'));
        } else {
          validationStates.menuName = true;
          callback();
        }
      },
      trigger: 'blur'
    }
  ],
  parentId: [
    {
      validator: (_rule, value, callback) => {
          if (menu.isDirectory && value !== -1) {
            callback(new Error('父菜单必须选择"顶级菜单"'));
          } else if (!menu.isDirectory && !value) {
            callback(new Error('请选择父菜单'));
          } else {
            callback();
          }
        },
        trigger: 'change'
      }
  ]
};


//初始验证：在组件挂载时执行一次验证
import { onMounted } from 'vue';
import IconView from '../icons/IconView.vue';
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
  updateSubmitButtonState();
};
//监听菜单类型变化：当 isDirectory 变化时重新验证
watch(() => menu.isDirectory, () => {
  formRef.value?.validate();
});

// 更新提交按钮状态
const updateSubmitButtonState = () => {
  // 检查所有必填字段是否都通过了验证
  const allValid = Object.values(validationStates).every(state => state);
  isSubmitDisabled.value = !allValid;
  
  // 如果是目录，不需要验证组件路径
  if (menu.isDirectory) {
    const withoutComponent = Object.entries(validationStates)
      .filter(([key]) => key !== 'menuComponent')
      .every(([_, state]) => state);
    isSubmitDisabled.value = !withoutComponent;
  }
};

const emit = defineEmits<{
  (e: 'submit', data: IMenuList): void;
  (e: 'close'): void;
}>();

const onSubmit = async () => {
  emit('submit', menu);
};

const onClose = () => {
  emit('close');
};

</script>

<style lang="css">
.menu-form-container {
    width: 600px;
    height: 350px;
    /* background-color: rgb(184, 191, 191); */
    display: flex;
    flex-direction: column; /* 改为垂直排列 */
    justify-content: flex-start; /* 从顶部开始排列 */
    align-items: center; /* 水平居中 */
}

.menu-header-container {
    width: 100%;
    height: 50px;
    /* background-color: bisque; */
    display: flex;
    justify-content: center;
    align-items: center;
    /* border-bottom: 1px solid rgb(175, 176, 239); */
}

.menu-el-form{
   margin-top: 5%;
   max-width: 600px;
}

.btn-group1{
   width: 100%;
   height: 50px;
   display: flex;
   justify-content: center;
   align-items: center;
   margin-left: 20px;
   /* background-color: rgb(255, 255, 255); */
}

.add-btn{
    margin-left: 75%;
}
</style>