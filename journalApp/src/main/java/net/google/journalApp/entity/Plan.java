package net.google.journalApp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Table(name = "plan")
@Data
public class Plan {

	@Id
	@Column(name = "id")
	private String id = UUID.randomUUID().toString();

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private double price;

	@Column(name = "description")
	private String description;

	@Column(name = "is_active")
	private int isActive;

	@Column(name = "is_populer")
	private int isPopuler;

	@Column(name = "display_order")
	private int displayOrder;

	@Column(name = "duration")
	private int duration;

	@Column(name = "access_features")
	private String accessFeatures;

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "insert_date_time", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertDateTime;

	@OneToMany(targetEntity = PlanFeature.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "plan_id ", referencedColumnName = "id", nullable = false)
	private List<PlanFeature> PlanFeature = new ArrayList<PlanFeature>();
}
