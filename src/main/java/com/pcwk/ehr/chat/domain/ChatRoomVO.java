package com.pcwk.ehr.chat.domain;

import com.pcwk.ehr.cmn.DTO;

public class ChatRoomVO extends DTO{

	private int roomId;
	private int senderId;
	private int receiverId;
	
	public ChatRoomVO() {

	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	@Override
	public String toString() {
		return "ChatRoom [roomId=" + roomId + ", senderId=" + senderId + ", receiverId=" + receiverId + "]";
	}
	
	
}
