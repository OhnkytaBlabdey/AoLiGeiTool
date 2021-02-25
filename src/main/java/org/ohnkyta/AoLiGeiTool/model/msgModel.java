package org.ohnkyta.AoLiGeiTool.model;

import com.google.gson.Gson;

public class msgModel {
	private String cmd;
	private String[] args;
	private String from;

	public msgModel(String command, String[] args, String from) {
		this.cmd = command;
		this.args = args;
		this.from = from;
	}

	public String getCmd() {
		return cmd;
	}

	public String[] getArgs() {
		return args;
	}

	public String getFrom() {
		return from;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
