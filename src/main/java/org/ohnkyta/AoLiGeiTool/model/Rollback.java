package org.ohnkyta.AoLiGeiTool.model;

public class Rollback extends msgModel {
	private int seq;

	public Rollback(int seq,String from) {
		super("rollback", new String[]{
				Integer.toString(seq)
		}, from);
		this.seq = seq;
	}

	public int getSeq() {
		return seq;
	}
}
