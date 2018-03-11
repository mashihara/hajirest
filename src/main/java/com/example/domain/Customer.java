package com.example.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //(1)
@Table(name = "customers") //(2)
@Data
@NoArgsConstructor //(3)JPAの仕様で引数なしのコンストラクタをつける必要がある
@AllArgsConstructor //これはまー、便利なので
public class Customer {
	@Id //(5)
	@GeneratedValue //(6)
	private Integer id;
	@NotNull
	@Size(min=1,max=10)
	@Column(nullable = false) //(7)
	private String firstName;
	@NotNull
	@Size(min=1,max=10)
	@Column(nullable = false)
	private String lastName;
}
