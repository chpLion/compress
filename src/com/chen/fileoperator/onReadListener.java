package com.chen.fileoperator;

import com.chen.model.Record;

/**
 * 读取文件时候的状态监听器接口
 * @author chen
 *
 */
public interface onReadListener {
	void onProgress(Record record,long currentLength);
	void onFail(String error);
	void onComplete();
	void onBegin(String title);
}
