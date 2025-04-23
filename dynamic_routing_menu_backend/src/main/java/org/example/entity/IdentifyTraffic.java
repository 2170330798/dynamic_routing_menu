package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@TableName(value = "identify_traffic")
public class IdentifyTraffic {
    private Integer id;
    private long protocol;
    private String sourceIp;
    private long sourcePort;
    private String destinationIp;
    private long destinationPort;
    private Timestamp timestamp;
    private String trafficDescription;
    private String suggestion;
    private Integer label;
}
