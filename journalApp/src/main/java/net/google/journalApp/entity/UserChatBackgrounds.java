package net.google.journalApp.entity;

import java.util.Date;
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
@Table(name = "user_chat_backgrounds")
@Data
public class UserChatBackgrounds {

	@Id
	@Column(name = "id")
	private String id = UUID.randomUUID().toString();

	@Column(name = "user_id")
	private String userId;

	@Column(name = "bg_img_user_id ")
	private String bgImgUserId;

	@Column(name = "img_url ")
	private String imgUrl;

	@Column(name = "is_active ")
	private String isActive;

	@Column(name = "inserted_user_id")
	private String insertedUserId;

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "insert_date_time", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertDateTime;

}
