/**
 * 
 */
package org.fanl.mapper;

import java.util.List;

import org.fanl.pojo.Experiment;
import org.springframework.stereotype.Repository;

/**
 * <p>Title:ExperimentMapper.java</P>
 * <p>Description</P>
 * @author ACER
 * @date 2019��4��4��
 */
@Repository
public interface ExperimentMapper {
	
	public void insertExperiment(Experiment experiment);
	
	public List<Experiment> selectExperimentList();
	
	public Experiment selectExperiment(Long experimentID);
}
