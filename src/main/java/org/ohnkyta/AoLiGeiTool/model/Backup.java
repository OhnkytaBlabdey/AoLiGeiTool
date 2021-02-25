package org.ohnkyta.AoLiGeiTool.model;

public class Backup extends msgModel {
	private String tips;

	public Backup(String tips, String from) {
		super("backup", new String[]{tips}, from);
		this.tips = tips;
	}

	public String getTips() {
		return tips;
	}
}
