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

		Query nativeQuery = em.createNamedQuery("deleteJPQL");
		nativeQuery.setParameter(1, eid);

		nativeQuery.executeUpdate();

		transaction.commit();
		System.out.println("record deleted");
		em.close();
	}

	private static void update(EntityManager em) {

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		Scanner sc = new Scanner(System.in);

		System.out.println("Enter employee id : ");
		int eid = sc.nextInt();

		System.out.println("Enter employee salary : ");
		int esalary = sc.nextInt();

		String sql = "update xemployee set salary = ? where id = ?";

		Query nativeQuery = em.createNativeQuery(sql);
		nativeQuery.setParameter(1, esalary);
		nativeQuery.setParameter(2, eid);

		nativeQuery.executeUpdate();

		transaction.commit();
		System.out.println("salary updated");

		em.close();

	}

	private static void read(EntityManager em) {

		String sql = "select id,name,address,salary from xemployee";
		Query query = em.createNativeQuery(sql, Employee.class);
		List<Employee> list = query.getResultList();

		for (Employee e : list) {
			System.out.println(e);
		}

		em.close();

//		String sql = "select id,name,address,salary from xemployee";
//		Query query = em.createNativeQuery(sql);
//		List<Object[]> list = query.getResultList();
//		
//		for(Object[] o : list) {
//			for(Object data : o) {
//				System.out.print(data + " ");
//			}
//			System.out.println();
//		}
	}

	private static void insert(EntityManager em) {

		Scanner sc = new Scanner(System.in);

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		String sql = "insert into xemployee (id,name,address,salary) values(?,?,?,?)";

		System.out.println("Enter id : ");
		int eid = Integer.parseInt(sc.nextLine());

		System.out.println("Enter name : ");
		String ename = sc.nextLine();

		System.out.println("Enter address : ");
		String eaddress = sc.nextLine();

		System.out.println("Enter salary : ");
		int esalary = Integer.parseInt(sc.nextLine());

		Query nativeQuery = em.createNativeQuery(sql);
		nativeQuery.setParameter(1, eid);
		nativeQuery.setParameter(2, ename);
		nativeQuery.setParameter(3, eaddress);
		nativeQuery.setParameter(4, esalary);

		nativeQuery.executeUpdate();

		transaction.commit();
		System.out.println("bulk data inserted...");

		em.close();
	}
}
