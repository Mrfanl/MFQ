/**
 * 
 */
package org.fanl.control;

import java.util.HashMap;
import java.util.Map;

import org.fanl.pojo.QueueParams;
import org.fanl.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>Title:QueueContorl.java</P>
 * <p>Description</P>
 * @author ACER
 * @date 2019Äê4ÔÂ3ÈÕ
 */
@Controller
public class QueueContorl {
	
	@Autowired
	private QueueService queueService = null;
	
	@RequestMapping("/queue")
	public String initQueuePage(Model model) {
		return "queue";
	}
	
	@RequestMapping("/queue/setQueue")
	public @ResponseBody
	Map<String,Object> setQueue(QueueParams queueParams) {
		System.out.println(queueParams.toString());
		Map<String,Object> map = new HashMap<String,Object>(); 
		map.put("queues", queueService.insertQueues(queueParams));
		return map;
	}
	
	
}









