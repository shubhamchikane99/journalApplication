package net.google.journalApp.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Table(name = "chat_messages")
@Data
public class ChatMessage {

	@Id
	@Column(name = "id")
	private String id = UUID.randomUUID().toString();

	@Column(name = "sender_id")
	private String senderId;

	@Column(name = "receiver_id")
	private String receiverId;

	@Column(name = "content")
	private String content;

	@Column(name = "type")
	private String type;

	@Column(name = "status")
	private String status; // Default is SENT

	@Column(name = "is_read")
	private int isRead;

	@Column(name = "reply_to_message_id")
	private String replyToMessageId;

	@Column(name = "is_edited")
	private int isEdited;

	@Column(name = "is_delete")
	private int isDelete;

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "insert_date_time", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertDateTime;

	@Transient
	private int flag;
}
