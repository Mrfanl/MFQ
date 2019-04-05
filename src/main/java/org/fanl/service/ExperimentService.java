/**
 * 
 */
package org.fanl.service;

import java.util.List;

import org.fanl.pojo.Experiment;

/**
 * <p>Title:ExperimentService.java</P>
 * <p>Description</P>
 * @author ACER
 * @date 2019Äê4ÔÂ4ÈÕ
 */
public interface ExperimentService {
	public List<Experiment> selectExperimentList();
	public Experiment selectExperiment(Long experimentID);
}
