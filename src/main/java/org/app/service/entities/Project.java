package org.app.service.entities;

import static javax.persistence.CascadeType.ALL;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

public class Project implements Serializable{
	
	//constructor added by CD, not available in documentation
	public Project(Integer projectID, String string, Date date) {
		// TODO Auto-generated constructor stub
		projectNo = projectID;
		name = string;
		startDate = date;
	}

	/*internal structure: member fields*/
	@Id @GeneratedValue
	private Integer projectNo;
	private String name;
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Transient
	private ProjectFactory projectManager; //changed variable type from ProjectManager to ProjectFactory
	
	@OneToMany(mappedBy="project", cascade = ALL, fetch = FetchType.EAGER, orphanRemoval = false)
	private List<Release> releases = new ArrayList<>();

	public String getProjectNo() {
		// TODO Auto-generated method stub
		return this.projectNo.toString();
	}

	public void setReleases(List<Release> releasesProject) {
		// TODO Auto-generated method stub
		releases = releasesProject;
	}
	
}
