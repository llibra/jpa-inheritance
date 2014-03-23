package com.gmail.libra.learn.jpa_inheritance;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("INNER")
public class InnerNode extends Node {
	@OneToMany(mappedBy = "parent")
	private Set<Node> children;

	public Set<Node> getChildren() {
		return children;
	}

	public void setChildren(Set<Node> children) {
		this.children = children;
	}
}
