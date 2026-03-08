package net.google.journalApp.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Table(name = "user_message_usage")
@Data
public class UserMessageUsage {

	@Id
	@Column(name = "id")
	private String id = UUID.randomUUID().toString();

	@Column(name = "sender_id")
	private String senderId;

	@Column(name = "receiver_id")
	private String receiverId;

	@Column(name = "message_count")
	private int messageCount;

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name = "date_time")
	private Date dateTime;

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "insert_date_time", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertDateTime;

}
