/**
 * 
 */
package org.fanl.service;

import java.util.List;

import org.fanl.mapper.ExperimentMapper;
import org.fanl.pojo.Experiment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>Title:ExperimentServiceImpl.java</P>
 * <p>Description</P>
 * @author ACER
 * @date 2019Äê4ÔÂ4ÈÕ
 */
@Service
public class ExperimentServiceImpl implements ExperimentService {

	@Autowired
	private ExperimentMapper experimentMapper = null;
	
	public List<Experiment> selectExperimentList() {
		// TODO Auto-generated method stub
		return experimentMapper.selectExperimentList();
	}

	public Experiment selectExperiment(Long experimentID) {
		return experimentMapper.selectExperiment(experimentID);
	}

}
