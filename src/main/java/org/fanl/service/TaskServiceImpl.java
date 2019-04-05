/**
 * 
 */
package org.fanl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.fanl.mapper.ExperimentMapper;
import org.fanl.mapper.TaskMapper;
import org.fanl.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>Title:TaskServiceImpl.java</P>
 * <p>Description</P>
 * @author ACER
 * @date 2019年4月4日
 */
@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskMapper taskMapper = null;
	
	@Autowired
	private ExperimentMapper experimentMapper = null; 
	
	public void insertTask(Task task) {
		//通过select查询t_task表中的数据，如果没有数据，就开始启动线程执行多级反馈
		List<Task> taskList = taskMapper.selectAllList();
		if(taskList.size()==0) {
			System.out.println("第一次添加任务");
			Runnable doTask = new DoTask();
			Thread taskThread = new Thread(doTask);
			taskThread.start();
		}
		taskMapper.insertTask(task);
	}
	
	public void deleteAllTask() {
		taskMapper.deleteAllTask();
	}
	
	//得到任务表中的所有任务
	public List<Task> selectAllList(){
		return taskMapper.selectAllList();
	}
	
	class DoTask implements Runnable{
		private long serviceTime = 0;//正在运行的任务的时间记录
		private long taskCount = 0;//记录任务的数量
		private long timePiece = 10;
		private long queueNum = 0;
		private Map<Long,List<Task>> taskListMap = new HashMap<Long,List<Task>>();
		
		//将最高优先级的任务置为工作任务
		private Task findHightTask() {
			//找到优先级最高的任务
			for (long index=1L;index<=taskListMap.size();index++) {
				List<Task> itemList = taskListMap.get(index);
				if(itemList.size()!=0) {
					//该优先级队列中有任务
					Task task = itemList.get(0);
					task.setIsWork(1);
					taskMapper.updateTask(task);
					return task;
				}
			}
			return null;
		}
		
		public void run() {
			Task workTask = taskMapper.selectTaskisWork();
			//如果是第一次取就全部取出
			while(true){
				if(taskListMap.size()==0) {
					System.out.println(1111);
					//第一次取，全部都在第一个队列中
					try {
						Thread.sleep(1000);//保证第一个任务能够先插入
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					List<Task> taskList = taskMapper.selectAllList();
					taskListMap.put(1L, taskList);
					taskCount = taskMapper.selectTaskCount();
					//放入工作任务
					workTask = taskListMap.get(1L).get(0);
					workTask.setIsWork(1);
					taskMapper.updateTask(workTask);
					//得到队列的数量，用于最后一个队列使用RR
					long experimentID = workTask.getExperimentID();
					queueNum = experimentMapper.selectExperiment(experimentID).getQueueNumber();
					//将剩下的空队列建立，防止将任务降级时不存在下一级的队列
					for(long i=2;i<=queueNum;i++) {
						List<Task> list = new ArrayList<Task>();
						taskListMap.put(i, list);
					}
				}
				//检查工作任务是否完成
				 if(workTask.getCompleteTime()>=workTask.getTaskTime()) {
					 //任务完成
					 long queueID = workTask.getQueueID();
					 taskListMap.get(queueID).remove(workTask);
					 workTask.setIsWork(0);
					 taskMapper.updateTask(workTask);
					 //寻找最高优先级的任务
					 workTask = findHightTask();
					 serviceTime=0;//任务切换更新时间
					 if(workTask==null) {
						 break;//所有任务执行完毕，跳出循环结束线程
					 }
				 }
				//如果任务的数量改变了就重新分配任务
				long newCount = taskMapper.selectTaskCount();
				if(taskCount!=newCount) {
					List<Task> taskList = taskMapper.selectNewTaskList(taskCount,newCount-taskCount);
					taskCount = newCount;
					for(Task newTask:taskList) {
						taskListMap.get(1L).add(newTask);
					}
					//判断是否要抢断
					if(taskListMap.get(1L).get(0)!=workTask) {
						//要抢断
						//被抢断的任务进入本队列的末尾
						long queueID = workTask.getQueueID();
						List<Task> taskSelfList = taskListMap.get(queueID);
						taskSelfList.remove(workTask);
						taskSelfList.add(workTask);
						//转换工作标记
						workTask.setIsWork(0);
						taskMapper.updateTask(workTask);
						workTask = taskListMap.get(1L).get(0);
						workTask.setIsWork(1);
						serviceTime=0;//任务切换更新时间
						taskMapper.updateTask(workTask);
					}
				}
				
				
				//看看时间片是否用完有则抢断
				if(serviceTime >= timePiece) {
					//优先级最高的队列抢断
					long queueID = workTask.getQueueID();
					//将工作任务放到下一个队列的末尾或者如果是最后一个队列了就放到本队列的末尾
					if(queueID>=queueNum) {
						//最后一个队列执行轮询
					  
						taskListMap.get(queueID).remove(workTask);
						taskListMap.get(queueID).add(workTask);
						workTask.setIsWork(0);
						taskMapper.updateTask(workTask);
						workTask = findHightTask();
						serviceTime=0;//任务切换更新时间
						if(workTask==null) {
							break;
						}
					}else {
						
						taskListMap.get(queueID).remove(workTask);
						taskListMap.get(queueID+1).add(workTask);
						workTask.setIsWork(0);
						workTask.setQueueID(queueID+1);
						taskMapper.updateTask(workTask);
						workTask = findHightTask();
						serviceTime=0;//任务切换更新时间
						if(workTask==null) {
							break;
						}
					}
				}
				
				//判断是否更新时间片
				
				//模拟一个时间单位是一秒
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.err.println("暂停时间出错");
					e.printStackTrace();
				}
				workTask.setCompleteTime(workTask.getCompleteTime()+1);
				taskMapper.updateTask(workTask);
				serviceTime++;
			}
		}
	}

}
