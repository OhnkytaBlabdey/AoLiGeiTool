package org.ohnkyta.AoLiGeiTool.model;

public class TodoItem extends msgModel {
	private String name;
	private String desc;
	private String action;

	public TodoItem(String name, String desc, String action) {
		super("gugu", new String[]{action, desc}, name);
	}

	public TodoItem(String name, String action) {
		super("gugu", new String[]{action}, name);
	}
}
