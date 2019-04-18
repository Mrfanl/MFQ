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
 * @date 2019��4��4��
 */
@Repository
public interface TaskMapper {
	public void insertTask(Task task);
	public void deleteAllTask();
	public List<Task> selectAllList();
	public Task selectTaskisWork();//Ѱ��δ��ɲ����ڹ���������
	public Long selectTaskCount();//����ĸ���
	public List<Task> selectNewTaskList(@Param("start")Long start,@Param("len")Long len);
	public void updateTask(Task task);
}
