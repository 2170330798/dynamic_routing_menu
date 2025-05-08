// src/composables/useSystemMonitor.ts
import { ref } from 'vue';
//import { Client } from '@stomp/stompjs'; // 必须使用STOMP客户端

export interface SystemMetrics {
  cpu: {
    usage: string;  // "85.50"
  };
  memory: {
    total: number;  // bytes
    used: number;   // bytes
    free: number;   // bytes
    usage: string;  // "65.30"
  };
  network: {
    uploadSpeed: string;  // "1024.50" (KB/s)
    downloadSpeed: string;// "2048.75" (KB/s)
  };
}

// 状态定义（完全匹配Java端数据结构）
const cpu = ref({
    usage: 0,       // 转换为number
    cores: 0        // 额外字段
});
  
const memory = ref({
    total: 0,       // bytes
    used: 0,        // bytes
    free: 0,        // bytes
    usage: 0,       // 转换为number
    totalGB: 0,     // 计算字段
    usedGB: 0,      // 计算字段
    freeGB: 0       // 计算字段
});

const network = ref({
    uploadSpeed: 0,  // 转换为number (KB/s)
    downloadSpeed: 0 // 转换为number (KB/s)
});


// STOMP客户端配置
//let stompClient: Client | null = null;

// export function connectWebSocket(url: string, topic: string, callback: (metrics: any) => void) {
  
//     stompClient = new Client({
//       brokerURL: url,
//       reconnectDelay: 5000,
//       heartbeatIncoming: 4000,
//       heartbeatOutgoing: 4000,
//     });
//     //console.log('stompClient:',  stompClient);
//     stompClient.onConnect = (frame) => {
//         console.log('✅ STOMP Connected:', frame);
//         console.log('start...');
//         const subscription = stompClient?.subscribe(topic, (message) => {
//             console.log('【原始消息】', message);
//             console.log('connected...');
//             const metrics: SystemMetrics = JSON.parse(message.body);
//             if (!metrics.cpu?.usage) console.error('无效的CPU数据');
//             console.log('data:', metrics);
//             // CPU数据处理（严格匹配Java端格式）
//             if (metrics.cpu) {
//                 cpu.value.usage = parseFloat(metrics.cpu.usage);
//             }
            
//             // 内存数据处理（严格匹配Java端格式）
//             if (metrics.memory) {
//                 memory.value = {
//                 total: metrics.memory.total,
//                 used: metrics.memory.used,
//                 free: metrics.memory.free,
//                 usage: parseFloat(metrics.memory.usage),
//                 totalGB: parseFloat((metrics.memory.total / (1024 ** 3)).toFixed(2)),
//                 usedGB: parseFloat((metrics.memory.used / (1024 ** 3)).toFixed(2)),
//                 freeGB: parseFloat((metrics.memory.free / (1024 ** 3)).toFixed(2))
//                 };
//             }
            
//             // 网络数据处理（严格匹配Java端格式）
//             if (metrics.network) {
//                 network.value = {
//                 uploadSpeed: parseFloat(metrics.network.uploadSpeed),
//                 downloadSpeed: parseFloat(metrics.network.downloadSpeed)
//                 };
//             }

//             callback(metrics);
//         });
//         console.log('订阅对象状态:', subscription?.id);
//     }

//     stompClient.onStompError = (frame) => {
//         console.error('Broker reported error: ' + frame.headers['message']);
//         console.error('Additional details: ' + frame.body);
//     };
      
//     stompClient.onWebSocketError = (event) => {
//         console.error('WebSocket error:', event);
//     };

//     stompClient.activate();
// }


// export function disconnectWebSocket() {
//     if (stompClient) {
//       stompClient.deactivate();
//       stompClient = null;
//       console.log('已断开websocket连接');
//     }
// }

// export { 
//     cpu, 
//     memory, 
//     network
// }

let websocket: WebSocket | null = null;

export function connectWebSocket(url: string, callback: (metrics: SystemMetrics) => void) {
    websocket = new WebSocket(url);
  
    websocket.onopen = () => {
        console.log('✅ WebSocket Connected');
    };
  
    websocket.onmessage = (event) => {
        const metrics: SystemMetrics = JSON.parse(event.data);
        
        // CPU 数据处理
        if (metrics.cpu) {
            cpu.value.usage = parseFloat(metrics.cpu.usage);
        }
        
        // 内存数据处理
        if (metrics.memory) {
            memory.value = {
                total: metrics.memory.total,
                used: metrics.memory.used,
                free: metrics.memory.free,
                usage: parseFloat(metrics.memory.usage),
                totalGB: parseFloat((metrics.memory.total / (1024 ** 3)).toFixed(2)),
                usedGB: parseFloat((metrics.memory.used / (1024 ** 3)).toFixed(2)),
                freeGB: parseFloat((metrics.memory.free / (1024 ** 3)).toFixed(2))
            };
        }
        
        // 网络数据处理
        if (metrics.network) {
            network.value = {
                uploadSpeed: parseFloat(metrics.network.uploadSpeed),
                downloadSpeed: parseFloat(metrics.network.downloadSpeed)
            };
        }
        
        callback(metrics);
    };
  
    websocket.onerror = (error) => {
        console.error('WebSocket error:', error);
    };
  
    websocket.onclose = () => {
        console.log('WebSocket disconnected');
    };
}

export function disconnectWebSocket() {
    if (websocket) {
        websocket.close();
        websocket = null;
    }
}

export { cpu, memory, network };

