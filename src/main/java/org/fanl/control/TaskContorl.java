/**
 * 
 */
package org.fanl.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fanl.pojo.Experiment;
import org.fanl.pojo.Task;
import org.fanl.service.ExperimentService;
import org.fanl.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@RequestMapping("/task/addTask")
	public @ResponseBody 
	Map<String,Object> addTask(Task task) {
		taskService.insertTask(task);
		Map<String,Object> map = new HashMap<String,Object>();
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
