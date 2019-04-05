/**
 * 
 */
package org.fanl.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.fanl.pojo.Experiment;
import org.fanl.pojo.Task;
import org.fanl.service.ExperimentService;
import org.fanl.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * <p>Title:TaskContorl.java</P>
 * <p>Description</P>
 * @author ACER
 * @date 2019年4月4日
 */
@Controller
public class TaskContorl {
	
	@Autowired
	private ExperimentService experimentService = null;
	
	@Autowired
	private TaskService taskService = null;
	
	//进入页面的同时获取实验数量的信息
	@RequestMapping("/task")
	public String initTaskPage(Model model) {
		List<Experiment> experimentList = experimentService.selectExperimentList();
		model.addAttribute("experimentList",experimentList);
		return "task";
	}
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@RequestMapping("/task/addTask")
	public @ResponseBody 
	Map<String,Object> addTask(Task task) {
		//taskService.insertTask(task);
		Map<String,Object> map = new HashMap<String,Object>();
		//将对象转换为json文本便于传输
		final String taskJson = JSON.toJSONString(task);
		//缓解数据库的压力可是使用消息队列
		jmsTemplate.send(new MessageCreator() {
			
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(taskJson);
			}
		});
		map.put("taskName", task.getTaskName());
		return map;
	}
	
	@RequestMapping("/task/getExperimentByEID")
	public @ResponseBody
	Map<String,Object> getExperimentByEID(Long experimentID){
		Map<String,Object> map = new HashMap<String,Object>();
		Experiment experiment = experimentService.selectExperiment(experimentID);
		map.put("experiment", experiment);
		return map;
	}
	
	@RequestMapping("/task/endExperiment")
	public @ResponseBody
	Map<String,Object> endExperiment(){
		Map<String,Object> map = new HashMap<String,Object>();
		taskService.deleteAllTask();
		return map;
	}
	
	@RequestMapping("/task/getAllTask")
	public @ResponseBody
	Map<String,Object> getAllTask(){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Task> taskList = taskService.selectAllList();
		map.put("taskList", taskList);
		return map;
	}
}
