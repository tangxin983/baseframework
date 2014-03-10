package com.tx.framework.web.common.enums;

/**
 * 附件类型
 * @author tangx
 *
 */
public enum AttachmentType {

	IMAGE(1), //图片
	VIDEO(2); //视频或音频

	private int value;

	private AttachmentType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
