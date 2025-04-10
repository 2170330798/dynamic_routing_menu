package org.example.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import java.sql.Timestamp;
@TableName(value = "network_flow")
public class NetworkFlow {

  private Integer id;
  private String protocol;
  private String sourceIp;
  private long sourcePort;
  private String destinationIp;
  private long destinationPort;
  private  Timestamp timestamp;
  private long flowDuration;
  private long totalFwdPackets;
  private long totalBackwardPackets;
  private long totalLengthFwdPackets;
  private long totalLengthBwdPackets;
  private long fwdPacketLengthMax;
  private long fwdPacketLengthMin;
  private double fwdPacketLengthMean;
  private double fwdPacketLengthStd;
  private long bwdPacketLengthMax;
  private long bwdPacketLengthMin;
  private double bwdPacketLengthMean;
  private double bwdPacketLengthStd;
  private double flowBytesPerSec;
  private double flowPacketsPerSec;
  private double flowIatMean;
  private double flowIatStd;
  private double flowIatMax;
  private double flowIatMin;
  private double fwdIatTotal;
  private double fwdIatMean;
  private double fwdIatStd;
  private double fwdIatMax;
  private double fwdIatMin;
  private double bwdIatTotal;
  private double bwdIatMean;
  private double bwdIatStd;
  private double bwdIatMax;
  private double bwdIatMin;
  private long fwdPshFlags;
  private long bwdPshFlags;
  private long fwdUrgFlags;
  private long bwdUrgFlags;
  private long fwdHeaderLength;
  private long bwdHeaderLength;
  private double fwdPacketsPerSec;
  private double bwdPacketsPerSec;
  private long minPacketLength;
  private long maxPacketLength;
  private double packetLengthMean;
  private double packetLengthStd;
  private double packetLengthVariance;
  private long finFlagCount;
  private long synFlagCount;
  private long rstFlagCount;
  private long pshFlagCount;
  private long ackFlagCount;
  private long urgFlagCount;
  private long cweFlagCount;
  private long eceFlagCount;
  private double downUpRatio;
  private double averagePacketSize;
  private double avgFwdSegmentSize;
  private double avgBwdSegmentSize;
  private long fwdHeaderLengthEnd;
  private double fwdAvgBytesBulk;
  private double fwdAvgPacketsBulk;
  private double fwdAvgBulkRate;
  private double bwdAvgBytesBulk;
  private double bwdAvgPacketsBulk;
  private double bwdAvgBulkRate;
  private long subflowFwdPackets;
  private long subflowFwdBytes;
  private long subflowBwdPackets;
  private long subflowBwdBytes;
  private long initWinBytesForward;
  private long initWinBytesBackward;
  private long actDataPktFwd;
  private long minSegSizeForward;
  private double activeMean;
  private double activeStd;
  private double activeMax;
  private double activeMin;
  private double idleMean;
  private double idleStd;
  private double idleMax;
  private double idleMin;
  private long similarHttp;
  private long inbound;
  private String label;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getProtocol() {
    return protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }


  public String getSourceIp() {
    return sourceIp;
  }

  public void setSourceIp(String sourceIp) {
    this.sourceIp = sourceIp;
  }


  public long getSourcePort() {
    return sourcePort;
  }

  public void setSourcePort(long sourcePort) {
    this.sourcePort = sourcePort;
  }


  public String getDestinationIp() {
    return destinationIp;
  }

  public void setDestinationIp(String destinationIp) {
    this.destinationIp = destinationIp;
  }


  public long getDestinationPort() {
    return destinationPort;
  }

  public void setDestinationPort(long destinationPort) {
    this.destinationPort = destinationPort;
  }


  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }


  public long getFlowDuration() {
    return flowDuration;
  }

  public void setFlowDuration(long flowDuration) {
    this.flowDuration = flowDuration;
  }


  public long getTotalFwdPackets() {
    return totalFwdPackets;
  }

  public void setTotalFwdPackets(long totalFwdPackets) {
    this.totalFwdPackets = totalFwdPackets;
  }


  public long getTotalBackwardPackets() {
    return totalBackwardPackets;
  }

  public void setTotalBackwardPackets(long totalBackwardPackets) {
    this.totalBackwardPackets = totalBackwardPackets;
  }


  public long getTotalLengthFwdPackets() {
    return totalLengthFwdPackets;
  }

  public void setTotalLengthFwdPackets(long totalLengthFwdPackets) {
    this.totalLengthFwdPackets = totalLengthFwdPackets;
  }


  public long getTotalLengthBwdPackets() {
    return totalLengthBwdPackets;
  }

  public void setTotalLengthBwdPackets(long totalLengthBwdPackets) {
    this.totalLengthBwdPackets = totalLengthBwdPackets;
  }


  public long getFwdPacketLengthMax() {
    return fwdPacketLengthMax;
  }

  public void setFwdPacketLengthMax(long fwdPacketLengthMax) {
    this.fwdPacketLengthMax = fwdPacketLengthMax;
  }


  public long getFwdPacketLengthMin() {
    return fwdPacketLengthMin;
  }

  public void setFwdPacketLengthMin(long fwdPacketLengthMin) {
    this.fwdPacketLengthMin = fwdPacketLengthMin;
  }


  public double getFwdPacketLengthMean() {
    return fwdPacketLengthMean;
  }

  public void setFwdPacketLengthMean(double fwdPacketLengthMean) {
    this.fwdPacketLengthMean = fwdPacketLengthMean;
  }


  public double getFwdPacketLengthStd() {
    return fwdPacketLengthStd;
  }

  public void setFwdPacketLengthStd(double fwdPacketLengthStd) {
    this.fwdPacketLengthStd = fwdPacketLengthStd;
  }


  public long getBwdPacketLengthMax() {
    return bwdPacketLengthMax;
  }

  public void setBwdPacketLengthMax(long bwdPacketLengthMax) {
    this.bwdPacketLengthMax = bwdPacketLengthMax;
  }


  public long getBwdPacketLengthMin() {
    return bwdPacketLengthMin;
  }

  public void setBwdPacketLengthMin(long bwdPacketLengthMin) {
    this.bwdPacketLengthMin = bwdPacketLengthMin;
  }


  public double getBwdPacketLengthMean() {
    return bwdPacketLengthMean;
  }

  public void setBwdPacketLengthMean(double bwdPacketLengthMean) {
    this.bwdPacketLengthMean = bwdPacketLengthMean;
  }


  public double getBwdPacketLengthStd() {
    return bwdPacketLengthStd;
  }

  public void setBwdPacketLengthStd(double bwdPacketLengthStd) {
    this.bwdPacketLengthStd = bwdPacketLengthStd;
  }


  public double getFlowBytesPerSec() {
    return flowBytesPerSec;
  }

  public void setFlowBytesPerSec(double flowBytesPerSec) {
    this.flowBytesPerSec = flowBytesPerSec;
  }


  public double getFlowPacketsPerSec() {
    return flowPacketsPerSec;
  }

  public void setFlowPacketsPerSec(double flowPacketsPerSec) {
    this.flowPacketsPerSec = flowPacketsPerSec;
  }


  public double getFlowIatMean() {
    return flowIatMean;
  }

  public void setFlowIatMean(double flowIatMean) {
    this.flowIatMean = flowIatMean;
  }


  public double getFlowIatStd() {
    return flowIatStd;
  }

  public void setFlowIatStd(double flowIatStd) {
    this.flowIatStd = flowIatStd;
  }


  public double getFlowIatMax() {
    return flowIatMax;
  }

  public void setFlowIatMax(double flowIatMax) {
    this.flowIatMax = flowIatMax;
  }


  public double getFlowIatMin() {
    return flowIatMin;
  }

  public void setFlowIatMin(double flowIatMin) {
    this.flowIatMin = flowIatMin;
  }


  public double getFwdIatTotal() {
    return fwdIatTotal;
  }

  public void setFwdIatTotal(double fwdIatTotal) {
    this.fwdIatTotal = fwdIatTotal;
  }


  public double getFwdIatMean() {
    return fwdIatMean;
  }

  public void setFwdIatMean(double fwdIatMean) {
    this.fwdIatMean = fwdIatMean;
  }


  public double getFwdIatStd() {
    return fwdIatStd;
  }

  public void setFwdIatStd(double fwdIatStd) {
    this.fwdIatStd = fwdIatStd;
  }


  public double getFwdIatMax() {
    return fwdIatMax;
  }

  public void setFwdIatMax(double fwdIatMax) {
    this.fwdIatMax = fwdIatMax;
  }


  public double getFwdIatMin() {
    return fwdIatMin;
  }

  public void setFwdIatMin(double fwdIatMin) {
    this.fwdIatMin = fwdIatMin;
  }


  public double getBwdIatTotal() {
    return bwdIatTotal;
  }

  public void setBwdIatTotal(double bwdIatTotal) {
    this.bwdIatTotal = bwdIatTotal;
  }


  public double getBwdIatMean() {
    return bwdIatMean;
  }

  public void setBwdIatMean(double bwdIatMean) {
    this.bwdIatMean = bwdIatMean;
  }


  public double getBwdIatStd() {
    return bwdIatStd;
  }

  public void setBwdIatStd(double bwdIatStd) {
    this.bwdIatStd = bwdIatStd;
  }


  public double getBwdIatMax() {
    return bwdIatMax;
  }

  public void setBwdIatMax(double bwdIatMax) {
    this.bwdIatMax = bwdIatMax;
  }


  public double getBwdIatMin() {
    return bwdIatMin;
  }

  public void setBwdIatMin(double bwdIatMin) {
    this.bwdIatMin = bwdIatMin;
  }


  public long getFwdPshFlags() {
    return fwdPshFlags;
  }

  public void setFwdPshFlags(long fwdPshFlags) {
    this.fwdPshFlags = fwdPshFlags;
  }


  public long getBwdPshFlags() {
    return bwdPshFlags;
  }

  public void setBwdPshFlags(long bwdPshFlags) {
    this.bwdPshFlags = bwdPshFlags;
  }


  public long getFwdUrgFlags() {
    return fwdUrgFlags;
  }

  public void setFwdUrgFlags(long fwdUrgFlags) {
    this.fwdUrgFlags = fwdUrgFlags;
  }


  public long getBwdUrgFlags() {
    return bwdUrgFlags;
  }

  public void setBwdUrgFlags(long bwdUrgFlags) {
    this.bwdUrgFlags = bwdUrgFlags;
  }


  public long getFwdHeaderLength() {
    return fwdHeaderLength;
  }

  public void setFwdHeaderLength(long fwdHeaderLength) {
    this.fwdHeaderLength = fwdHeaderLength;
  }


  public long getBwdHeaderLength() {
    return bwdHeaderLength;
  }

  public void setBwdHeaderLength(long bwdHeaderLength) {
    this.bwdHeaderLength = bwdHeaderLength;
  }


  public double getFwdPacketsPerSec() {
    return fwdPacketsPerSec;
  }

  public void setFwdPacketsPerSec(double fwdPacketsPerSec) {
    this.fwdPacketsPerSec = fwdPacketsPerSec;
  }


  public double getBwdPacketsPerSec() {
    return bwdPacketsPerSec;
  }

  public void setBwdPacketsPerSec(double bwdPacketsPerSec) {
    this.bwdPacketsPerSec = bwdPacketsPerSec;
  }


  public long getMinPacketLength() {
    return minPacketLength;
  }

  public void setMinPacketLength(long minPacketLength) {
    this.minPacketLength = minPacketLength;
  }


  public long getMaxPacketLength() {
    return maxPacketLength;
  }

  public void setMaxPacketLength(long maxPacketLength) {
    this.maxPacketLength = maxPacketLength;
  }


  public double getPacketLengthMean() {
    return packetLengthMean;
  }

  public void setPacketLengthMean(double packetLengthMean) {
    this.packetLengthMean = packetLengthMean;
  }


  public double getPacketLengthStd() {
    return packetLengthStd;
  }

  public void setPacketLengthStd(double packetLengthStd) {
    this.packetLengthStd = packetLengthStd;
  }


  public double getPacketLengthVariance() {
    return packetLengthVariance;
  }

  public void setPacketLengthVariance(double packetLengthVariance) {
    this.packetLengthVariance = packetLengthVariance;
  }


  public long getFinFlagCount() {
    return finFlagCount;
  }

  public void setFinFlagCount(long finFlagCount) {
    this.finFlagCount = finFlagCount;
  }


  public long getSynFlagCount() {
    return synFlagCount;
  }

  public void setSynFlagCount(long synFlagCount) {
    this.synFlagCount = synFlagCount;
  }


  public long getRstFlagCount() {
    return rstFlagCount;
  }

  public void setRstFlagCount(long rstFlagCount) {
    this.rstFlagCount = rstFlagCount;
  }


  public long getPshFlagCount() {
    return pshFlagCount;
  }

  public void setPshFlagCount(long pshFlagCount) {
    this.pshFlagCount = pshFlagCount;
  }


  public long getAckFlagCount() {
    return ackFlagCount;
  }

  public void setAckFlagCount(long ackFlagCount) {
    this.ackFlagCount = ackFlagCount;
  }


  public long getUrgFlagCount() {
    return urgFlagCount;
  }

  public void setUrgFlagCount(long urgFlagCount) {
    this.urgFlagCount = urgFlagCount;
  }


  public long getCweFlagCount() {
    return cweFlagCount;
  }

  public void setCweFlagCount(long cweFlagCount) {
    this.cweFlagCount = cweFlagCount;
  }


  public long getEceFlagCount() {
    return eceFlagCount;
  }

  public void setEceFlagCount(long eceFlagCount) {
    this.eceFlagCount = eceFlagCount;
  }


  public double getDownUpRatio() {
    return downUpRatio;
  }

  public void setDownUpRatio(double downUpRatio) {
    this.downUpRatio = downUpRatio;
  }


  public double getAveragePacketSize() {
    return averagePacketSize;
  }

  public void setAveragePacketSize(double averagePacketSize) {
    this.averagePacketSize = averagePacketSize;
  }


  public double getAvgFwdSegmentSize() {
    return avgFwdSegmentSize;
  }

  public void setAvgFwdSegmentSize(double avgFwdSegmentSize) {
    this.avgFwdSegmentSize = avgFwdSegmentSize;
  }


  public double getAvgBwdSegmentSize() {
    return avgBwdSegmentSize;
  }

  public void setAvgBwdSegmentSize(double avgBwdSegmentSize) {
    this.avgBwdSegmentSize = avgBwdSegmentSize;
  }


  public long getFwdHeaderLengthEnd() {
    return fwdHeaderLengthEnd;
  }

  public void setFwdHeaderLengthEnd(long fwdHeaderLengthEnd) {
    this.fwdHeaderLengthEnd = fwdHeaderLengthEnd;
  }


  public double getFwdAvgBytesBulk() {
    return fwdAvgBytesBulk;
  }

  public void setFwdAvgBytesBulk(double fwdAvgBytesBulk) {
    this.fwdAvgBytesBulk = fwdAvgBytesBulk;
  }


  public double getFwdAvgPacketsBulk() {
    return fwdAvgPacketsBulk;
  }

  public void setFwdAvgPacketsBulk(double fwdAvgPacketsBulk) {
    this.fwdAvgPacketsBulk = fwdAvgPacketsBulk;
  }


  public double getFwdAvgBulkRate() {
    return fwdAvgBulkRate;
  }

  public void setFwdAvgBulkRate(double fwdAvgBulkRate) {
    this.fwdAvgBulkRate = fwdAvgBulkRate;
  }


  public double getBwdAvgBytesBulk() {
    return bwdAvgBytesBulk;
  }

  public void setBwdAvgBytesBulk(double bwdAvgBytesBulk) {
    this.bwdAvgBytesBulk = bwdAvgBytesBulk;
  }


  public double getBwdAvgPacketsBulk() {
    return bwdAvgPacketsBulk;
  }

  public void setBwdAvgPacketsBulk(double bwdAvgPacketsBulk) {
    this.bwdAvgPacketsBulk = bwdAvgPacketsBulk;
  }


  public double getBwdAvgBulkRate() {
    return bwdAvgBulkRate;
  }

  public void setBwdAvgBulkRate(double bwdAvgBulkRate) {
    this.bwdAvgBulkRate = bwdAvgBulkRate;
  }


  public long getSubflowFwdPackets() {
    return subflowFwdPackets;
  }

  public void setSubflowFwdPackets(long subflowFwdPackets) {
    this.subflowFwdPackets = subflowFwdPackets;
  }


  public long getSubflowFwdBytes() {
    return subflowFwdBytes;
  }

  public void setSubflowFwdBytes(long subflowFwdBytes) {
    this.subflowFwdBytes = subflowFwdBytes;
  }


  public long getSubflowBwdPackets() {
    return subflowBwdPackets;
  }

  public void setSubflowBwdPackets(long subflowBwdPackets) {
    this.subflowBwdPackets = subflowBwdPackets;
  }


  public long getSubflowBwdBytes() {
    return subflowBwdBytes;
  }

  public void setSubflowBwdBytes(long subflowBwdBytes) {
    this.subflowBwdBytes = subflowBwdBytes;
  }


  public long getInitWinBytesForward() {
    return initWinBytesForward;
  }

  public void setInitWinBytesForward(long initWinBytesForward) {
    this.initWinBytesForward = initWinBytesForward;
  }


  public long getInitWinBytesBackward() {
    return initWinBytesBackward;
  }

  public void setInitWinBytesBackward(long initWinBytesBackward) {
    this.initWinBytesBackward = initWinBytesBackward;
  }


  public long getActDataPktFwd() {
    return actDataPktFwd;
  }

  public void setActDataPktFwd(long actDataPktFwd) {
    this.actDataPktFwd = actDataPktFwd;
  }


  public long getMinSegSizeForward() {
    return minSegSizeForward;
  }

  public void setMinSegSizeForward(long minSegSizeForward) {
    this.minSegSizeForward = minSegSizeForward;
  }


  public double getActiveMean() {
    return activeMean;
  }

  public void setActiveMean(double activeMean) {
    this.activeMean = activeMean;
  }


  public double getActiveStd() {
    return activeStd;
  }

  public void setActiveStd(double activeStd) {
    this.activeStd = activeStd;
  }


  public double getActiveMax() {
    return activeMax;
  }

  public void setActiveMax(double activeMax) {
    this.activeMax = activeMax;
  }


  public double getActiveMin() {
    return activeMin;
  }

  public void setActiveMin(double activeMin) {
    this.activeMin = activeMin;
  }


  public double getIdleMean() {
    return idleMean;
  }

  public void setIdleMean(double idleMean) {
    this.idleMean = idleMean;
  }


  public double getIdleStd() {
    return idleStd;
  }

  public void setIdleStd(double idleStd) {
    this.idleStd = idleStd;
  }


  public double getIdleMax() {
    return idleMax;
  }

  public void setIdleMax(double idleMax) {
    this.idleMax = idleMax;
  }


  public double getIdleMin() {
    return idleMin;
  }

  public void setIdleMin(double idleMin) {
    this.idleMin = idleMin;
  }


  public long getSimilarHttp() {
    return similarHttp;
  }

  public void setSimilarHttp(long similarHttp) {
    this.similarHttp = similarHttp;
  }


  public long getInbound() {
    return inbound;
  }

  public void setInbound(long inbound) {
    this.inbound = inbound;
  }


  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

}
