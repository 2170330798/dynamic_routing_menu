<template>
        <el-form ref="ruleFormRef" :model="trainData" :rules="rules" label-width="180px"  class="el-form" :size="formSize" status-icon>
            <!-- 第一行：3个字段 -->
            <el-row :gutter="20">
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="数据集文件路径" prop="csvSourcePath">
                        <el-input v-model="trainData.csvSourcePath" style="width: 100%;"  placeholder="" clearable/>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="打乱的数据集保存路径" prop="csvDestPath">
                        <el-input v-model="trainData.csvDestPath"  style="width: 100%;" placeholder="" clearable/>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="数据集标签列" prop="labelColumn">
                        <el-input v-model="trainData.labelColumn" style="width: 100%;" placeholder="" clearable/>
                    </el-form-item>
                </el-col>
            </el-row>

            <!-- 第二行：3个字段 -->
            <el-row :gutter="20">
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="训练批次大小" prop="batchSize">
                        <el-input v-model.number="trainData.batchSize" style="width: 100%;" placeholder="" clearable/>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="读取数据集行数" prop="rows">
                        <el-input v-model.number="trainData.rows" style="width: 100%;" placeholder="" clearable/>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="训练次数" prop="numEpochs">
                        <el-input v-model.number="trainData.numEpochs" style="width: 100%;" placeholder="" clearable/>
                    </el-form-item>
                </el-col>
            </el-row>

            <!-- 第三行：1个字段（学习率独占一行） -->
            <el-row :gutter="20">
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="学习率" prop="lr">
                        <el-input v-model="trainData.lr" :step="0.001" :precision="4" :min="0" style="width: 100%" placeholder="" clearable/>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="框架ID" prop="frameId">
                        <el-input v-model="trainData.frameId" style="width: 100%;" placeholder="" clearable/>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="学习率衰减步长" prop="stepSize">
                        <el-input v-model="trainData.stepSize"   style="width: 100%;" placeholder="" clearable/>
                    </el-form-item>
                </el-col>
            </el-row>

            <!-- 第四行：3个字段 -->
            <el-row :gutter="20">
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="学习率衰减比率" prop="gamma">
                        <el-input v-model="trainData.gamma" step="any" style="width: 100%;" placeholder="" clearable/>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="模型名字" prop="modelName">
                        <el-input v-model="trainData.modelName" style="width: 100%;" placeholder="" clearable/>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="模型保存路径" prop="modelSavePath">
                        <el-input v-model="trainData.modelSavePath" style="width: 100%;"  placeholder="" clearable/>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row :gutter="20">
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="模型ID" prop="modelId">
                        <el-input v-model.number="trainData.modelId" style="width: 100%;" placeholder="" clearable/>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="模型版本" prop="modelVersion">
                        <el-input v-model="trainData.modelVersion" style="width: 100%;" placeholder="" clearable/>
                    </el-form-item>
                </el-col>
            </el-row>


            <!-- 按钮行 -->
            <el-row :gutter="20">
                <el-col :span="24">
                    <el-form-item class="btn-item">
                        <el-button type="primary" @click="saveForm(ruleFormRef)">保存</el-button>
                        <el-button @click="resetForm(ruleFormRef)">重置</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
       
</template>
  
<script lang="ts" setup>
import { reactive, ref } from 'vue';
import { ElMessage, type ComponentSize, type FormInstance, type FormRules } from 'element-plus';
import { INITIAL_TRAIN_DATA, type TRAIN } from '../../components/train/train';
import { useTrainStore } from '../../store/train';
const ruleFormRef = ref<FormInstance>();
// 修改初始化方式
const trainData = reactive<TRAIN>({ ...INITIAL_TRAIN_DATA });
const formSize = ref<ComponentSize>('default');
const store = useTrainStore();
//表单校验规则
const rules = reactive<FormRules <TRAIN>>({
    csvSourcePath: [
        { required: true, message: '请输入CSV数据集文件路径', trigger: 'blur' },
    ],
    csvDestPath: [
        {message: '请输入保存打乱CSV数据集文件路径', trigger: 'change'},
    ],
    labelColumn: [
        {required: true, message: '请输入CSV文件标签列', trigger: 'change'},
    ],
    batchSize: [
        {required: true, message: '请输入训练数据批次大小', trigger: 'change'},
    ],
    rows: [
        {required: true, message: '请输入读取CSV文件行数', trigger: 'change'},
    ],
    numEpochs: [
        {required: true, message: '请输入模型训练次数', trigger: 'change'},
    ],
    lr: [
        {required: true, message: '请输入模型训练学习率', trigger: 'change'},
    ],
    stepSize: [
        {required: true, message: '请输入模型训练学习率衰减步长', trigger: 'change'},
    ],
    gamma: [
        { required: true, message: '请输入模型训练学习率衰减比率', trigger: 'blur'},
    ],
    modelName: [
        { required: true,message: '请输入模型名字',trigger: 'change'}
    ],
    frameId: [
        { required: true, message: '请输入框架ID', trigger: 'change'}
    ],
    modelId: [
        { required: true, message: '请输入模型ID', trigger: 'change'}
    ],
    modelVersion: [
        { required: true, message: '请输入模型版本', trigger: 'change'}
    ],
    modelSavePath: [
        { required: true, message: '请输入模型保存路径', trigger: 'change'}
    ]
})
const emit = defineEmits(['close-dialog']); // 定义 emit 事件
const saveForm = (formEl: FormInstance | undefined) => {
        if (!formEl) return
            formEl.validate((valid, fields) => {
            if (valid) {
                console.log('save!');
                store.setTrainData(trainData);
                console.log('保存的表单数据',store.getTrainData);
                ElMessage.success('保存成功');
                emit('close-dialog'); // 触发父组件关闭弹窗
            } else {
                console.log('error save!', fields);
                ElMessage.error('保存失败');
            }
        })
}


const resetForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return;
    formEl.resetFields();
    // 修改初始化方式
    // 使用 Object.assign 保持响应性
    Object.assign(trainData, INITIAL_TRAIN_DATA);
    store.setTrainData(INITIAL_TRAIN_DATA);
}
</script>

<style lang="css">

/* 或者更精确的选择器 */
.btn-item .el-form-item__content {
  display: flex;
  justify-content: flex-end;
}
</style>
