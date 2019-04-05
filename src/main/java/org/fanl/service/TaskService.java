/**
 * 
 */
package org.fanl.service;

import java.util.List;

import org.fanl.pojo.Task;

/**
 * <p>Title:TaskService.java</P>
 * <p>Description</P>
 * @author ACER
 * @date 2019Äê4ÔÂ4ÈÕ
 */
public interface TaskService {
	public void insertTask(Task task);
	public void deleteAllTask();
	public List<Task> selectAllList();
}
