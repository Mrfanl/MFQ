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
 * @date 2019��4��4��
 */
@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskMapper taskMapper = null;
	
	@Autowired
	private ExperimentMapper experimentMapper = null; 
	
	public void insertTask(Task task) {
		//ͨ��select��ѯt_task���е����ݣ����û�����ݣ��Ϳ�ʼ�����߳�ִ�ж༶����
		List<Task> taskList = taskMapper.selectAllList();
		if(taskList.size()==0) {
			System.out.println("��һ���������");
			Runnable doTask = new DoTask();
			Thread taskThread = new Thread(doTask);
			taskThread.start();
		}
		taskMapper.insertTask(task);
	}
	
	public void deleteAllTask() {
		taskMapper.deleteAllTask();
	}
	
	//�õ�������е���������
	public List<Task> selectAllList(){
		return taskMapper.selectAllList();
	}
	
	class DoTask implements Runnable{
		private long serviceTime = 0;//�������е������ʱ���¼
		private long taskCount = 0;//��¼���������
		private long timePiece = 10;
		private long queueNum = 0;
		private Map<Long,List<Task>> taskListMap = new HashMap<Long,List<Task>>();
		
		//��������ȼ���������Ϊ��������
		private Task findHightTask() {
			//�ҵ����ȼ���ߵ�����
			for (long index=1L;index<=taskListMap.size();index++) {
				List<Task> itemList = taskListMap.get(index);
				if(itemList.size()!=0) {
					//�����ȼ�������������
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
			//����ǵ�һ��ȡ��ȫ��ȡ��
			while(true){
				if(taskListMap.size()==0) {
					System.out.println(1111);
					//��һ��ȡ��ȫ�����ڵ�һ��������
					try {
						Thread.sleep(1000);//��֤��һ�������ܹ��Ȳ���
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					List<Task> taskList = taskMapper.selectAllList();
					taskListMap.put(1L, taskList);
					taskCount = taskMapper.selectTaskCount();
					//���빤������
					workTask = taskListMap.get(1L).get(0);
					workTask.setIsWork(1);
					taskMapper.updateTask(workTask);
					//�õ����е��������������һ������ʹ��RR
					long experimentID = workTask.getExperimentID();
					queueNum = experimentMapper.selectExperiment(experimentID).getQueueNumber();
					//��ʣ�µĿն��н�������ֹ�����񽵼�ʱ��������һ���Ķ���
					for(long i=2;i<=queueNum;i++) {
						List<Task> list = new ArrayList<Task>();
						taskListMap.put(i, list);
					}
				}
				//��鹤�������Ƿ����
				 if(workTask.getCompleteTime()>=workTask.getTaskTime()) {
					 //�������
					 long queueID = workTask.getQueueID();
					 taskListMap.get(queueID).remove(workTask);
					 workTask.setIsWork(0);
					 taskMapper.updateTask(workTask);
					 //Ѱ��������ȼ�������
					 workTask = findHightTask();
					 serviceTime=0;//�����л�����ʱ��
					 if(workTask==null) {
						 break;//��������ִ����ϣ�����ѭ�������߳�
					 }
				 }
				//�������������ı��˾����·�������
				long newCount = taskMapper.selectTaskCount();
				if(taskCount!=newCount) {
					List<Task> taskList = taskMapper.selectNewTaskList(taskCount,newCount-taskCount);
					taskCount = newCount;
					for(Task newTask:taskList) {
						taskListMap.get(1L).add(newTask);
					}
					//�ж��Ƿ�Ҫ����
					if(taskListMap.get(1L).get(0)!=workTask) {
						//Ҫ����
						//�����ϵ�������뱾���е�ĩβ
						long queueID = workTask.getQueueID();
						List<Task> taskSelfList = taskListMap.get(queueID);
						taskSelfList.remove(workTask);
						taskSelfList.add(workTask);
						//ת���������
						workTask.setIsWork(0);
						taskMapper.updateTask(workTask);
						workTask = taskListMap.get(1L).get(0);
						workTask.setIsWork(1);
						serviceTime=0;//�����л�����ʱ��
						taskMapper.updateTask(workTask);
					}
				}
				
				
				//����ʱ��Ƭ�Ƿ�������������
				if(serviceTime >= timePiece) {
					//���ȼ���ߵĶ�������
					long queueID = workTask.getQueueID();
					//����������ŵ���һ�����е�ĩβ������������һ�������˾ͷŵ������е�ĩβ
					if(queueID>=queueNum) {
						//���һ������ִ����ѯ
					  
						taskListMap.get(queueID).remove(workTask);
						taskListMap.get(queueID).add(workTask);
						workTask.setIsWork(0);
						taskMapper.updateTask(workTask);
						workTask = findHightTask();
						serviceTime=0;//�����л�����ʱ��
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
						serviceTime=0;//�����л�����ʱ��
						if(workTask==null) {
							break;
						}
					}
				}
				
				//�ж��Ƿ����ʱ��Ƭ
				
				//ģ��һ��ʱ�䵥λ��һ��
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.err.println("��ͣʱ�����");
					e.printStackTrace();
				}
				workTask.setCompleteTime(workTask.getCompleteTime()+1);
				taskMapper.updateTask(workTask);
				serviceTime++;
			}
		}
	}

}
