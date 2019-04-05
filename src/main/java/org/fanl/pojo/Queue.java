/**
 * 
 */
package org.fanl.pojo;

/**
 * <p>Title:Queue.java</P>
 * <p>Description</P>
 * @author ACER
 * @date 2019Äê4ÔÂ3ÈÕ
 */
public class Queue {
	private Long id;
	private Long timePiece;
	private Long  priority;
	private String experimentName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTimePiece() {
		return timePiece;
	}
	public void setTimePiece(Long timePiece) {
		this.timePiece = timePiece;
	}
	public Long getPriority() {
		return priority;
	}
	public void setPriority(Long priority) {
		this.priority = priority;
	}
	public String getExperimentName() {
		return experimentName;
	}
	public void setExperimentName(String experimentName) {
		this.experimentName = experimentName;
	}
	
	 
}
