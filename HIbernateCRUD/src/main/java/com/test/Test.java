package com.test;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.entity.Employee;

public class Test {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("config");
		EntityManager em = emf.createEntityManager();

		Scanner sc = new Scanner(System.in);

		while (true) {

			System.out.println("1. insert employee details");
			System.out.println("2. read employee");
			System.out.println("3. update salary");
			System.out.println("4. delete employee");
			System.out.println("5. exit");

			System.out.println("Enter choice");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				insert(em);
				break;
			case 2:
				read(em);
				break;

			case 3:
				update(em);
				break;

			case 4:
				delete(em);
				break;

			case 5:
				return;

			default:
				System.out.println("Wrong choice...");
			}
		}
	}

	private static void delete(EntityManager em) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter employee id : ");

		int eid = sc.nextInt();
		Employee employee = em.find(Employee.class, eid);
		if (employee != null) {

			EntityTransaction transaction = em.getTransaction();
			transaction.begin();

			em.remove(employee);

			transaction.commit();
			System.out.println("Employee deleted successfully\n");

		} else {
			System.out.println("Employee not found");
		}
	}

	private static void update(EntityManager em) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter employee id : ");

		int eid = sc.nextInt();
		Employee employee = em.find(Employee.class, eid);
		if (employee != null) {

			EntityTransaction transaction = em.getTransaction();
			transaction.begin();

			System.out.println("Enter salary : ");
			int esalary = sc.nextInt();
			employee.setSalary(esalary);

			transaction.commit();
			System.out.println("Salary updated successfully");

		} else {
			System.out.println("Employee not found");
		}
	}

	private static void read(EntityManager em) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter employee id : ");

		int eid = sc.nextInt();
		Employee employee = em.find(Employee.class, eid);
		System.out.println(employee);

	}

	private static void insert(EntityManager em) {

		Scanner sc = new Scanner(System.in);

		System.out.println("Enter id : ");
		int eid = Integer.parseInt(sc.nextLine());

		System.out.println("Enter name : ");
		String ename = sc.nextLine();

		System.out.println("Enter address : ");
		String eaddress = sc.nextLine();

		System.out.println("Enter salary : ");
		int esalary = Integer.parseInt(sc.nextLine());

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		Employee employee = new Employee(eid, ename, eaddress, esalary);
		em.persist(employee);

		transaction.commit();
		System.out.println("Data inserted successfully...");
	}
}
