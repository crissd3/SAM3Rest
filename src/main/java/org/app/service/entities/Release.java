package org.app.service.entities;

import static javax.persistence.CascadeType.ALL;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

public class Release implements Serializable {
	
	public Release(Object object, String string, Date date, Project project2) {
		// TODO Auto-generated constructor stub
		//not sure what first argument should be used for
		description = string;
		publishDate = date;
		project = project2;
	}

	/*internal member fields*/
	@Id @GeneratedValue
	private Integer releaseId;
	private String codeName;
	private String indicative;
	private String description;
	
	@Temporal(TemporalType.DATE)
	private Date publishDate;
	
	@ManyToOne
	private Project project;
	
	@OneToMany(cascade = ALL, fetch=FetchType.EAGER)
	private List<Feature> features = new ArrayList<>();
	
	
}
