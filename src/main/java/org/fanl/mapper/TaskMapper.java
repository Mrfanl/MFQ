/**
 * 
 */
package org.fanl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.fanl.pojo.Task;
import org.springframework.stereotype.Repository;

/**
 * <p>Title:TaskMapper.java</P>
 * <p>Description</P>
 * @author ACER
 * @date 2019年4月4日
 */
@Repository
public interface TaskMapper {
	public void insertTask(Task task);
	public void deleteAllTask();
	public List<Task> selectAllList();
	public Task selectTaskisWork();//寻找未完成并且在工作的任务
	public Long selectTaskCount();//任务的个数
	public List<Task> selectNewTaskList(@Param("start")Long start,@Param("len")Long len);
	public void updateTask(Task task);
}
