/**
 * 
 */
package org.fanl.mapper;


import org.fanl.pojo.Queue;
import org.springframework.stereotype.Repository;

/**
 * <p>Title:QueueMapper.java</P>
 * <p>Description</P>
 * @author ACER
 * @date 2019Äê4ÔÂ3ÈÕ
 */
@Repository
public interface QueueMapper {
	public void insertQueue(Queue queue);
}
