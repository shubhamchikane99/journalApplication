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
@Table(name = "access_role")
@Data
public class AccessRole {

	@Id
	@Column(name = "id")
	private String id = UUID.randomUUID().toString();

	@Column(name = "access_role_name")
	private String accessRoleName;

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy hh:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "insert_date_time", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertDateTime; 
}
