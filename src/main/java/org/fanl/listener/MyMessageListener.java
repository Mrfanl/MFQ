/**
 * 
 */
package org.fanl.listener;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.fanl.pojo.Task;
import org.fanl.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;


/**
 * <p>Title:MyMessageListener.java</P>
 * <p>Description</P>
 * @author ACER
 * @date 2019Äê4ÔÂ18ÈÕ
 */
public class MyMessageListener implements MessageListener {

	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Autowired
	private TaskService taskService;
	
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			String taskJson = textMessage.getText();
			Task task = JSON.parseObject(taskJson, Task.class);
			taskService.insertTask(task);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
