package com.xue.replay.handler;

import java.io.File;

import android.os.Environment;

public class FileHandle {

	private File SDCRAD_PTAH = Environment.getExternalStorageDirectory();

	private String replay = "replay";

	public boolean createDir(String dir) {
		File file = new File(SDCRAD_PTAH + File.separator + replay + File.separator + dir);
		if (!isExists(file)) {
			return file.mkdirs();
		}
		return true;
	}

	public boolean deleteDir(String dir) {
		File file = new File(SDCRAD_PTAH + File.separator + replay + File.separator + dir);
		if (file.exists()) {
			return file.delete();
		}
		return true;
	}

	public boolean isExists(File file) {
		return file.exists();
	}

	public String getPath() {
		return SDCRAD_PTAH + File.separator + replay;
	}

	public String[] getGroupList() {
		File file = new File(getPath());
		return file.list();
	}

	public String[] getEventList(String group) {
		File file = new File(getPath() + File.separator + group);
		return file.list();
	}

}
