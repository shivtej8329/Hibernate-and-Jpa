package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "xemployee")
public class Employee {

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "name" , length = 30)
	private String name;

	@Column(name = "address" , length = 30)
	private String address;

	@Column(name = "salary")
	private int salary;

	public Employee() {

	}

	public Employee(int id, String name, String address, int salary) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.salary = salary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "id=" + id + ", name=" + name + ", address=" + address + ", salary=" + salary;
	}

}
