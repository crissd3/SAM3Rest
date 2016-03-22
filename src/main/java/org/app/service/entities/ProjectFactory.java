package org.app.service.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Singleton
public class ProjectFactory {
	/*
	 * Build a new project with a specific id, name
	 * and containing a specific number of releases.
	 */
	public Project buildProject(Integer projectID, String name, Integer releaseCount) {
		Project project = new Project(projectID, name + "." + projectID, new Date());
		List<Release> releasesProject = new ArrayList<>();
		
		Date dataPublicare = new Date();
		Long interval = 30l /*zile*/ * 24 /*ore*/ * 60 /*minute*/ * 60 /*sec*/ * 1000 /*milisec*/;
		
		for (int i=0; i<=releaseCount-1; i++) {
			releasesProject.add(new Release(null, "R: " + project.getProjectNo() + "." + i,
					new Date(dataPublicare.getTime() + i * interval), project));
		}
		
		project.setReleases(releasesProject);
		return project;
		
	}

}
