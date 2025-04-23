export interface TRAFFIC {

        protocol: number;
        sourceIp: string;
        sourcePort: number;
        destinationIp: string;
        destinationPort: number;
        timestamp: Date;
        trafficDescription: string;
        suggestion: string;
        label: number;
      
}

// 原始映射字典（名字到数字）
const labelMappingDict = {
      'DrDoS_DNS': 0,
      'DrDoS_LDAP': 1,
      'DrDoS_MSSQL': 2,
      'DrDoS_NetBIOS': 3,
      'DrDoS_NTP': 4,
      'DrDoS_SNMP': 5,
      'DrDoS_SSDP': 6,
      'DrDoS_UDP': 7,
      'Syn': 8,
      'TFTP': 9,
      'UDP-lag': 10,
      'BENIGN': 11,
      'WebDDoS': 12,
      'Portmap': 13,
      'NetBIOS': 14,
      'UDPLag': 10,
      'LDAP': 15,
      'MSSQL': 16,
      'UDP': 17,
      'Normall': 18
};

// 反转映射字典（数字到名字）
const reverseLabelMappingDict: Record<number, string> = {};

// 构建反转字典
Object.entries(labelMappingDict).forEach(([name, num]) => {
  // 注意：如果有多个名字映射到同一个数字，后面的会覆盖前面的
  reverseLabelMappingDict[num] = name;
});

/**
 * 将数字映射回对应的名字
 * @param num 要查找的数字
 * @returns 对应的名字，如果找不到则返回 undefined
 */
export const mapNumberToLabel = (num: number): string | undefined => {
      return reverseLabelMappingDict[num];
}