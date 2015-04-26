package org.test.model;

public enum Role {

	ADMIN("ADMIN"), USER("USER");

	private final String description;

	private Role(final String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

}
