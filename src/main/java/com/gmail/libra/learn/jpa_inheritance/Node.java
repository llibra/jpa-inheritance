package com.gmail.libra.learn.jpa_inheritance;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public abstract class Node extends BaseObject {
	@ManyToOne
	private InnerNode parent;

	public InnerNode getParent() {
		return parent;
	}

	public void setParent(InnerNode parent) {
		this.parent = parent;
	}
}
