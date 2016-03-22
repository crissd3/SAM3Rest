package org.app.service.entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Feature implements Serializable{
	@Id @GeneratedValue
	protected Integer featureID;
	private String name;
	private String description;
}
