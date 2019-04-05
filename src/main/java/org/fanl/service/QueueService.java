/**
 * 
 */
package org.fanl.service;

import java.util.List;

import org.fanl.pojo.Queue;
import org.fanl.pojo.QueueParams;

/**
 * <p>
 * Title:QueueService.java
 * </P>
 * <p>
 * Description
 * </P>
 * 
 * @author ACER
 * @date 2019Äê4ÔÂ3ÈÕ
 */
public interface QueueService {
	public List<Queue> insertQueues(QueueParams queueParams);
}
