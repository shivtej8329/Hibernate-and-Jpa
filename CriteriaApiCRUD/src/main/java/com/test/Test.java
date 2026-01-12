package com.test;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<Employee> cd = cb.createCriteriaDelete(Employee.class);

		Root<Employee> root = cd.from(Employee.class);
		cd.where(cb.equal(root.get("id"), eid));

		Query query = em.createQuery(cd);
		query.executeUpdate();

		transaction.commit();
		System.out.println("record deleted");
		em.close();
	}

	private static void update(EntityManager em) {

		Scanner sc = new Scanner(System.in);

		System.out.println("Enter employee id : ");
		int eid = sc.nextInt();

		System.out.println("Enter employee salary : ");
		int esalary = sc.nextInt();

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaUpdate<Employee> cu = cb.createCriteriaUpdate(Employee.class);

		Root<Employee> root = cu.from(Employee.class);

		cu.set(root.get("salary"), esalary).where(cb.equal(root.get("id"), eid));

		Query query = em.createQuery(cu);
		query.executeUpdate();

		transaction.commit();
		System.out.println("record updated");
		em.close();
	}

	private static void read(EntityManager em) {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Employee> cq = criteriaBuilder.createQuery(Employee.class);
		Root<Employee> from = cq.from(Employee.class);
		cq.select(from);

		TypedQuery<Employee> query = em.createQuery(cq);
		List<Employee> list = query.getResultList();

		for (Employee e : list) {
			System.out.println(e);
		}

		em.close();
	}

	private static void read2(EntityManager em) {

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
		Root<Employee> root = cq.from(Employee.class);

		Predicate p1 = cb.like(root.get("name"), "s%");
		Predicate p2 = cb.greaterThan(root.get("id"), 5);

		cq.select(root).where(cb.and(p1, p2));

		List<Employee> list = em.createQuery(cq).getResultList();

		for (Employee e : list) {
			System.out.println(e);
		}

		em.close();
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
