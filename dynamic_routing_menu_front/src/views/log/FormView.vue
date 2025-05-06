<template>
    <el-table class="traffic-table" :data="tableData" header-row-class-name="traffic-header-row" style="width: 100%">
        <el-table-column type="selection" width="55" />
        <el-table-column property="protocol" label="协议" width="120">
            <template #default="{ row }">
                {{ getProtocolName(row.protocol) }}
            </template>
        </el-table-column>
        
        <el-table-column property="sourceIp" label="源IP" width="180" />
        <el-table-column property="sourcePort" label="源端口" width="120"/>
        <el-table-column property="destinationIp" label="目的IP" width="180" />
        <el-table-column property="destinationPort" label="目的端口" width="120" />
        <el-table-column property="timestamp" label="流量抓取时间" width="240" />
        <el-table-column property="trafficDescription" label="描述" width="350" />
        <el-table-column property="suggestion" label="处理建议" width="180" />
        <el-table-column property="label" label="流量类型" width="120" > 
            <template #default="{ row }">
                {{ mapNumberToLabel(row.label) }}
            </template>
        </el-table-column>

    </el-table>
</template>

<script lang="ts" setup>
import { computed } from 'vue';
import { useTrafficStore } from '../../store/traffic';
import pinia from '../../store';
import { mapNumberToLabel } from '../../components/traffic/traffic.ts'

const store = useTrafficStore(pinia);

// 使用 computed 自动响应 store 数据变化
const tableData = computed(() => {
    const pageData = store.getPage;
    if (!pageData?.list) {
        console.log('无数据！');
        return [];
    }
    return pageData.list.map(item => ({
        ...item,
        protocol: Number(item.protocol) || -1,
        label: Number(item.label) || -1,
    }));
});

const getProtocolName = (protocol: number) => {
    switch(protocol) {
        case 6: return 'TCP';
        case 17: return 'UDP';
        default: return '其他';
    }
}
</script>

<style lang="css">
.traffic-table{
    margin-bottom: 5%;
    height:  80%;
    width: 800px;
}

.traffic-header-row{
    color: #000;
    text-align: center;
}

.traffic-table .el-table__body {
    width: 100% !important;
}
.traffic-table .el-table__body td {
    white-space: nowrap;
}
</style>