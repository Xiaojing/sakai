package org.sakaiproject.gradebookng.business.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

/**
 * Class for storing the order of an assignment.
 * A list of these is persisted as XML into the site tool properties
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class AssignmentOrder {


	@Getter
	@Setter
	@XmlElement
	private int assignmentId;
	
	@Getter
	@Setter
	@XmlElement
	private int order;	
	
	private AssignmentOrder() {
		//JAXB constructor
	}
	
	public AssignmentOrder(int assignmentId, int order) {
		this.assignmentId = assignmentId;
		this.order = order;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	
}
