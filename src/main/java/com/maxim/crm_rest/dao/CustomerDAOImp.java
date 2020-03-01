package com.maxim.crm_rest.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.maxim.crm_rest.entity.Customer;

	// should apply repository to DAO  implementation
	// for spring to be able to scan it

@Repository
@Transactional
public class CustomerDAOImp implements CustomerDAO {

	// inject the session factory by using Autowired
	
	@Autowired
	private SessionFactory sessionFactory;
	
	// let spring to start and stop transacion itself
	
	@Override
	public List<Customer> getCustomers() {
		
		
		// get current hibernate session - > create query - > execute query and get list
		// - > return the resul list
		
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName", Customer.class);
		
		List<Customer> customers = theQuery.getResultList();
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		
		// get current hibernate session and save customer
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer getCustomer(int theId) {
	
		Session currentSession = sessionFactory.getCurrentSession();
		
		Customer theCustumer = currentSession.get(Customer.class, theId);
		
		return theCustumer;
	}

	@Override
	public void deleteCustomer(int theId) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query theQuery = currentSession.createQuery("delete from Customer where id =: customerId");
		
		theQuery.setParameter("customerId", theId);
		
		theQuery.executeUpdate();
	}

}
