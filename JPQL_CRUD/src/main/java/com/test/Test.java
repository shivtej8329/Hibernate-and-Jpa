package com.test;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

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

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		Query query = em.createNamedQuery("deleteJPQL");
		query.setParameter("id", eid);

		query.executeUpdate();

		transaction.commit();
		System.out.println("record deleted");
		em.close();

//		Scanner sc = new Scanner(System.in);
//		System.out.println("Enter employee id : ");
//		int eid = sc.nextInt();
//
//		EntityTransaction transaction = em.getTransaction();
//		transaction.begin();
//
//		String jpql = "delete from Employee where id =:id";
//
//		Query query = em.createQuery(jpql);
//		query.setParameter("id", eid);
//
//		query.executeUpdate();
//
//		transaction.commit();
//		System.out.println("record deleted");
//		em.close();
	}

	private static void update(EntityManager em) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		Scanner sc = new Scanner(System.in);

		System.out.println("Enter employee id : ");
		int eid = sc.nextInt();

		System.out.println("Enter employee salary : ");
		int esalary = sc.nextInt();

		String jpql = "update Employee set salary =:salary where id =:id";
		Query query = em.createQuery(jpql);
		query.setParameter("salary", esalary);
		query.setParameter("id", eid);

		query.executeUpdate();

		transaction.commit();
		System.out.println("Record updated");
		em.close();

	}

	private static void read(EntityManager em) {

		String jpql = "select e from Employee e";

		Query query = em.createQuery(jpql, Employee.class);
		List<Employee> list = query.getResultList();

		for (Employee e : list) {
			System.out.println(e);
		}

	}

	public static void insert(EntityManager em) {

		Scanner sc = new Scanner(System.in);

		System.out.println("Enter id : ");
		int eid = sc.nextInt();
		sc.nextLine();

		System.out.println("Enter name : ");
		String ename = sc.nextLine();

		System.out.println("Enter address : ");
		String eaddress = sc.nextLine();

		System.out.println("Enter salary : ");
		int esalary = sc.nextInt();

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		Employee employee = new Employee(eid, ename, eaddress, esalary);
		em.persist(employee);

		transaction.commit();
		System.out.println("Record inserted");
		em.close();
	}

}
