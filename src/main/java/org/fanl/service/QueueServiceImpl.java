/**
 * 
 */
package org.fanl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fanl.mapper.ExperimentMapper;
import org.fanl.mapper.QueueMapper;
import org.fanl.pojo.Experiment;
import org.fanl.pojo.Queue;
import org.fanl.pojo.QueueParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Title:QueueServiceImpl.java
 * </P>
 * <p>
 * Description
 * </P>
 * 
 * @author ACER
 * @date 2019年4月3日
 */
@Service
public class QueueServiceImpl implements QueueService {

	@Autowired
	private QueueMapper queueMapper = null;
	
	@Autowired
	private ExperimentMapper experimentMapper = null;

	public List<Queue> insertQueues(QueueParams queueParams) {
		String experimentName = queueParams.getExperimentName();// 得到实验名称
		Long queueNumber = queueParams.getQueueNumber();
		Long timePiece = queueParams.getTimePiece();
		Experiment experiment = new Experiment();
		experiment.setExperimentName(experimentName);
		experiment.setQueueNumber(queueNumber);
		experimentMapper.insertExperiment(experiment);	
		List<Queue> queueList = new ArrayList<Queue>();
		// 生成多个Queue插入队列
		for (long i = 0; i < queueNumber; i++) {
			Queue queue = new Queue();
			queue.setExperimentName(experimentName);
			queue.setPriority(i+1);
			queue.setTimePiece(timePiece << i);
			queueMapper.insertQueue(queue);
			queue.setId(i+1);
			queueList.add(queue);
		}
		return queueList;
	}
}
