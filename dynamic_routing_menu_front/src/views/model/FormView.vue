<template>
    
        <el-form ref="ruleFormRef" :model="trainData" :rules="getDynamicRules" label-width="180px"  class="el-form" :size="formSize" status-icon>
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
                        <!-- <el-input v-model="trainData.frame" style="width: 100%;" placeholder="" clearable/> -->
                        <el-select v-model="trainData.frame" placeholder="模型框架" style="width: 100%">
                            <el-option v-for="item in frameStore.getIFrame" :key="item.frameId" :label="item.frameName" :value="item.frameId"/>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="模型名字" prop="modelName">
                        <el-input v-model="trainData.modelName" style="width: 100%;" placeholder="" clearable/>
                    </el-form-item>
                </el-col>
                
            </el-row>

            <!-- 第四行：3个字段 -->
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
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="模型保存路径" prop="modelSavePath">
                        <el-input v-model="trainData.modelSavePath" style="width: 100%;"  placeholder="" clearable/>
                    </el-form-item>
                </el-col>
            </el-row>

            <!-- 第五行：2个字段 -->
            <el-row :gutter="20">
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="模型初始化模式" prop="initMode">
                        <el-select v-model.number="trainData.initMode" placeholder="模型初始化方式" style="width: 100%">
                            <el-option v-for="item in initModelOptions" :key="item.value" :label="item.label" :value="Number(item.value)"/>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="模型验证模式" prop="validateMode">
                        <el-select v-model="trainData.validateMode" placeholder="模型验证方式" style="width: 100%">
                            <el-option v-for="item in validateOptions" :key="item.value" :label="item.label" :value="Number(item.value)"/>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="模型路径" prop="modelPath" v-show="Number(trainData.initMode) === 2">
                        <el-input v-model="trainData.modelPath" style="width: 100%;" placeholder="" clearable/>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row :gutter="20">
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="学习率衰减比率" prop="gamma" v-show="props.currentTab !== 'rnn'">
                        <el-input v-model="trainData.gamma" step="any" style="width: 100%;" placeholder="" clearable/>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="学习率衰减步长" prop="stepSize" v-show="props.currentTab !== 'rnn'">
                        <el-input v-model.number="trainData.stepSize"   style="width: 100%;" placeholder="" clearable/>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="训练早停容忍度" prop="patience">
                        <el-input v-model.number="trainData.patience" style="width: 100%;"  placeholder="" clearable/>
                    </el-form-item>
                </el-col>
            </el-row>

            <!-- 第六行：3个字段 -->
            <el-row :gutter="20">
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="RNN学习率衰减比率" prop="factor" v-show="props.currentTab === 'rnn'">
                        <el-input v-model="trainData.factor" step="any" style="width: 100%;" placeholder="" clearable/>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="8">
                    <el-form-item label="RNN训练参数权重" prop="weightDecay" v-show="props.currentTab == 'rnn'">
                        <el-input v-model="trainData.weightDecay" style="width: 100%;" placeholder="" clearable/>
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
import { computed, reactive, ref } from 'vue';
import { ElMessage, type ComponentSize, type FormInstance, type FormRules } from 'element-plus';
import { INITIAL_TRAIN_DATA, type TRAIN } from '../../components/train/train';
import { useTrainStore } from '../../store/train';
import { useFrameStore } from '../../store/frame/frame';
const ruleFormRef = ref<FormInstance>();
const currentTaskId = ref();
const frameStore = useFrameStore();
// FormView.vue
const props = defineProps<{
  currentTab: string  // 明确声明currentTab
}>()
// watch(() => props.currentTab, (newVal) => {
//   console.log('Tab值变化:', newVal)
//   // 执行相应的逻辑
// })

const initModelOptions = [
  {
    value: 0,
    label: '训练新模型',
  },
  {
    value: 1,
    label: '训练旧模型',
  },
  {
    value: 2,
    label: '识别模式',
  }
];

const validateOptions = [
{
    value: 0,
    label: '训练时验证',
  },
  {
    value: 1,
    label: '识别验证',
  },
  {
    value: 2,
    label: '一般验证',
  }
];


currentTaskId.value = `task_${Date.now().toString().slice(-8)}`; // 取后8位减少长度
// 修改初始化方式
const trainData = reactive<TRAIN>({ 
    ...INITIAL_TRAIN_DATA,
    taskId: currentTaskId.value
 });
const formSize = ref<ComponentSize>('default');
const store = useTrainStore();
//表单校验规则

const getDynamicRules = computed(() => {
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
        modelName: [
            { required: true,message: '请输入模型名字',trigger: 'change'}
        ],
        frame: [
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
        ],
        modelPath: [
            { required: false, message: '请输入模型路径', trigger: 'change'}
        ],
        patience: [
            { required: true, message: '请输入训练早停容忍度', trigger: 'change'}
        ],
        initMode: [
            { required: true, message: '请输入初始化模型模式', trigger: 'change'}
        ],
        validateMode: [
            { required: true, message: '请输入模型训练验证模式', trigger: 'change'}
        ],
    })
    if(props.currentTab === 'rnn'){
        rules.factor = [
            { required: true, message: '请输入RNN学习率下降比率', trigger: 'change' }
        ];
        rules.weightDecay = [
            { required: true, message: '请输入RNN训练权重', trigger: 'change' }
        ];
    }
    if(props.currentTab !== 'rnn'){
        rules.gamma = [
            { required: true, message: '请输入模型训练学习率衰减比率', trigger: 'blur'},
        ];
        rules.stepSize = [
            {required: true, message: '请输入模型训练学习率衰减步长', trigger: 'change'},
        ];
    }
    return rules;
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
