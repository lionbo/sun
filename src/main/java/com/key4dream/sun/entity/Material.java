package com.key4dream.sun.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

@Table(name="optimus_public_log")
public class Material{
	@Id
	@Column(name="id")
	private Long id;
	@Column(name="time_key")
	private Long timeKey;
	@Column(name="uuid")
	private String uuid;
	@Column(name="cell")
	private Long cell;
	@Column(name="chat_type")
	private Integer chatType;
	@Column(name="business_type")
	private Integer businessType;
	@Column(name="cityid")
	private Integer cityid;
	@Column(name="method")
	private String method;
	@Column(name="type")
	private String type;
	@Column(name="role_type")
	private Integer roleType;
	@Column(name="log_time")
	private Date logTime;
	@Column(name="wait_time")
	private Integer waitTime;
	public Material(){
	}
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
    			.add("type", type)
    			.add("chatType", chatType)
    			.add("logTime", logTime)
    			.add("method", method)
    			.add("cell", cell)
    			.add("businessType", businessType)
    			.add("roleType", roleType)
    			.add("cityid", cityid)
    			.add("uuid", uuid)
    			.add("waitTime", waitTime)
    			.toString();
	}
	public Material(Long timeKey, String uuid, Integer cityid, Long cell, Integer chatType, Integer businessType,
			String method,Integer roleType,Date logTime) {
		super();
		this.timeKey = timeKey;
		this.uuid = uuid;
		this.cityid = cityid;
		this.cell = cell;
		this.chatType = chatType;
		this.businessType = businessType;
		this.logTime = logTime;
		this.roleType = roleType;
		this.method = method;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getCityid() {
		return cityid;
	}
	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}
	public Long getTimeKey() {
		return timeKey;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Long getCell() {
		return cell;
	}
	public void setCell(Long cell) {
		this.cell = cell;
	}
	public Integer getChatType() {
		return chatType;
	}
	public void setChatType(Integer chatType) {
		this.chatType = chatType;
	}
	public Integer getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Integer getRoleType() {
		return roleType;
	}
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	public void setTimeKey(Long timeKey) {
		this.timeKey = timeKey;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(Integer waitTime) {
		this.waitTime = waitTime;
	}
}
