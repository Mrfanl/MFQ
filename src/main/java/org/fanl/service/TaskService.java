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
 * @date 2019��4��4��
 */
public interface TaskService {
	public void insertTask(Task task);
	public void deleteAllTask();
	public List<Task> selectAllList();
}
