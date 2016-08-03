package com.chen.fileoperator;

import java.util.List;

import com.chen.model.Record;

public interface onReadCompleteListener {
	void onReadConplete(List<Record> records);
}
