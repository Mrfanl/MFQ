/**
 * 
 */
package org.fanl.pojo;

/**
 * <p>Title:QueueParams.java</P>
 * <p>Description</P>
 * @author ACER
 * @date 2019Äê4ÔÂ3ÈÕ
 */
public class QueueParams {
	private String experimentName;
	private Long queueNumber;
	private Long timePiece;
	public String getExperimentName() {
		return experimentName;
	}
	public void setExperimentName(String experimentName) {
		this.experimentName = experimentName;
	}
	public Long getQueueNumber() {
		return queueNumber;
	}
	public void setQueueNumber(Long queueNumber) {
		this.queueNumber = queueNumber;
	}
	public Long getTimePiece() {
		return timePiece;
	}
	public void setTimePiece(Long timePiece) {
		this.timePiece = timePiece;
	}
	@Override
	public String toString() {
		return "QueueParams [experimentName=" + experimentName + ", queueNumber=" + queueNumber + ", timePiece="
				+ timePiece + "]";
	}
	
	


}
