/**
 * 
 */
package org.fanl.pojo;

/**
 * <p>Title:Task.java</P>
 * <p>Description</P>
 * @author ACER
 * @date 2019Äê4ÔÂ4ÈÕ
 */
public class Task {
	private Long taskID;
	private String taskName;
	private Long taskTime;
	private Long experimentID;
	private Long queueID = 1L;
	private Long completeTime = 0L;
	private Integer isWork = 0;
	
	public Integer getIsWork() {
		return isWork;
	}
	public void setIsWork(Integer isWork) {
		this.isWork = isWork;
	}
	public Long getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Long completeTime) {
		this.completeTime = completeTime;
	}
	public Long getQueueID() {
		return queueID;
	}
	public void setQueueID(Long queueID) {
		this.queueID = queueID;
	}
	public Long getTaskID() {
		return taskID;
	}
	public void setTaskID(Long taskID) {
		this.taskID = taskID;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Long getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(Long taskTime) {
		this.taskTime = taskTime;
	}
	public Long getExperimentID() {
		return experimentID;
	}
	public void setExperimentID(Long experimentID) {
		this.experimentID = experimentID;
	}
	

}
