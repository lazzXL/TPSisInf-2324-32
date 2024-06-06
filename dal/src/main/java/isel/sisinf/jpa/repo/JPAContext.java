/*
MIT License

Copyright (c) 2022-2024, Nuno Datia, ISEL

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package isel.sisinf.jpa.repo;


import isel.sisinf.model.*;
import jakarta.persistence.*;
import org.eclipse.persistence.sessions.DatabaseLogin;
import org.eclipse.persistence.sessions.Session;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;


public class JPAContext implements IContext{

	
	private EntityManagerFactory _emf;
    private EntityManager _em;

    private EntityTransaction _tx;
    private int _txcount;
    
    private IBicycleRepository _bicycleRepository;
    private ICustomerRepository _customerRepository;
    private IReservationRepository _reservationRepository;
	private IShopRepository _shopRepository;
	private IGPSDeviceRepository _gpsdeviceRepository;
    
/// HELPER METHODS    
    protected List helperQueryImpl(String jpql, Object... params)
    {
    	Query q = _em.createQuery(jpql);

		for(int i = 0; i < params.length; ++i)
			q.setParameter(i+1, params[i]);
		
		return q.getResultList();
    }
    
    protected Object helperCreateImpl(Object entity)
    {
    	beginTransaction();
		_em.persist(entity);
		commit();
		return entity;	
    }
    
    protected Object helperUpdateImpl(Object entity)
    {
    	beginTransaction();
		_em.merge(entity);
		commit();
		return entity;	
    }
    
    protected Object helperDeleteteImpl(Object entity)
    {
    	beginTransaction();
		_em.remove(entity);
		commit();
		return entity;
    }
/// END HELPER
public class BicycleRepository implements IBicycleRepository
    {

		@Override
		public Bicycle findByKey(Long key) {
			return _em.createNamedQuery("Bicycle.findByKey",Bicycle.class)
					.setParameter("key", key)
					.getSingleResult();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Collection<Bicycle> find(String jpql, Object... params) {
			return helperQueryImpl( jpql, params);
		}


		@Override
		public Bicycle create(Bicycle entity) {
			return (Bicycle)helperCreateImpl(entity);
		}

		@Override
		public Bicycle update(Bicycle entity) {
			return (Bicycle)helperUpdateImpl(entity);
		}

		@Override
		public Bicycle delete(Bicycle entity) {
			return (Bicycle)helperDeleteteImpl(entity);
		}
    }

    protected class CustomerRepository implements ICustomerRepository
    {

		@Override
		public Customer findByKey(Long key) {
			return _em.createNamedQuery("Customer.findByKey",Customer.class)
					 .setParameter("key", key)
	            	  .getSingleResult();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Collection<Customer> find(String jpql, Object... params) {
			
			return helperQueryImpl( jpql, params);
		}

		@Override
		public Customer create(Customer entity) {
			return (Customer)helperCreateImpl(entity);
		}

		@Override
		public Customer update(Customer entity) {
			return (Customer)helperUpdateImpl(entity);
		}

		@Override
		public Customer delete(Customer entity) {
			return (Customer)helperDeleteteImpl(entity);
		}
		
    }
    
    protected class ReservationRepository implements IReservationRepository
    {

		@Override
		public Reservation findByKey(Long key) {
			return _em.createNamedQuery("Reservation.findByKey",Reservation.class)
					 .setParameter("key", key)
	            	  .getSingleResult();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Collection<Reservation> find(String jpql, Object... params) {
			return helperQueryImpl( jpql, params);
		}

		@Override
		public Reservation create(Reservation entity) {
			return (Reservation)helperCreateImpl(entity);
		}

		@Override
		public Reservation update(Reservation entity) {
			return (Reservation)helperUpdateImpl(entity);
		}

		@Override
		public Reservation delete(Reservation entity) {
			return (Reservation)helperDeleteteImpl(entity);
		}
    	
		
    }

	protected class ShopRepository implements IShopRepository
	{

		@Override
		public Shop findByKey(Long key) {
			return _em.createNamedQuery("Shop.findByKey",Shop.class)
					.setParameter("key", key)
					.getSingleResult();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Collection<Shop> find(String jpql, Object... params) {
			return helperQueryImpl( jpql, params);
		}

		@Override
		public Shop create(Shop entity) {
			return (Shop)helperCreateImpl(entity);
		}

		@Override
		public Shop update(Shop entity) {
			return (Shop)helperUpdateImpl(entity);
		}

		@Override
		public Shop delete(Shop entity) {
			return (Shop)helperDeleteteImpl(entity);
		}


	}

	protected class GPSDeviceRepository implements IGPSDeviceRepository
	{

		@Override
		public GPSDevice findByKey(Long key) {
			return _em.createNamedQuery("GPSDevice.findByKey",GPSDevice.class)
					.setParameter("key", key)
					.getSingleResult();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Collection<GPSDevice> find(String jpql, Object... params) {
			return helperQueryImpl( jpql, params);
		}

		@Override
		public GPSDevice create(GPSDevice entity) {
			return (GPSDevice)helperCreateImpl(entity);
		}

		@Override
		public GPSDevice update(GPSDevice entity) {
			return (GPSDevice)helperUpdateImpl(entity);
		}

		@Override
		public GPSDevice delete(GPSDevice entity) {
			return (GPSDevice)helperDeleteteImpl(entity);
		}


	}
    
	@Override
	public void beginTransaction() {
		if(_tx == null)
		{
			_tx = _em.getTransaction();
			_tx.begin();
			_txcount=0;
		}
		++_txcount;
	}
	
	@Override
	public void beginTransaction(IsolationLevel isolationLevel) 
	{
		beginTransaction();
		Session session =_em.unwrap(Session.class);
		DatabaseLogin databaseLogin = (DatabaseLogin) session.getDatasourceLogin();
		System.out.println(databaseLogin.getTransactionIsolation());
		
		int isolation = DatabaseLogin.TRANSACTION_READ_COMMITTED;
		if(isolationLevel == IsolationLevel.READ_UNCOMMITTED)
			isolation = DatabaseLogin.TRANSACTION_READ_UNCOMMITTED;
		else if(isolationLevel == IsolationLevel.REPEATABLE_READ)
			isolation = DatabaseLogin.TRANSACTION_REPEATABLE_READ;
		else if(isolationLevel == IsolationLevel.SERIALIZABLE)
			isolation = DatabaseLogin.TRANSACTION_SERIALIZABLE;
		
		databaseLogin.setTransactionIsolation(isolation);
	}

	@Override
	public void commit() {
		
		--_txcount;
		if(_txcount==0 && _tx != null)
		{
			_em.flush();
			_tx.commit();
			_tx = null;
		}
	}

	@Override
	public void flush() {
		_em.flush();
	}


	@Override
	public void clear() {
		_em.clear();
		
	}

	@Override
	public void persist(Object entity) {
		_em.persist(entity);
		
	}

	public JPAContext() {
		this("GoCycle-G32");
	}
	
	public JPAContext(String persistentCtx) 
	{
		super();
	
		this._emf = Persistence.createEntityManagerFactory(persistentCtx);
		this._em = _emf.createEntityManager();
		this._bicycleRepository = new BicycleRepository();
		this._customerRepository = new CustomerRepository();
		this._reservationRepository = new ReservationRepository();
		this._shopRepository = new ShopRepository();
		this._gpsdeviceRepository = new GPSDeviceRepository();
	}

	

	@Override
	public void close() throws Exception {
		
        if(_tx != null)
        	_tx.rollback();
        _em.close();
        _emf.close();
	}

	@Override
	public IBicycleRepository getBicycles() {
		return _bicycleRepository;
	}

	@Override
	public ICustomerRepository getCustomers() {
		return _customerRepository;
	}
	@Override
	public IReservationRepository getReservations() {
		return _reservationRepository;
	}
	@Override
	public IShopRepository getShops() {
		return _shopRepository;
	}
	@Override
	public IGPSDeviceRepository getGPSDevices() {
		return _gpsdeviceRepository;
	}


	public int checkBikeisAvailable(long id, Timestamp time){
		StoredProcedureQuery checkbike_disp =
				_em.createNamedStoredProcedureQuery("is_bike_available");
		checkbike_disp.setParameter(1, id);
		checkbike_disp.setParameter(2, time);
		checkbike_disp.execute();

		return (int) checkbike_disp.getOutputParameterValue(3);
	}

	public void makenewreservation(Integer shopId, Integer customerId, Integer bicycleId, Timestamp startTime, Timestamp endTime, Double amount){
		StoredProcedureQuery makeReservation =
				_em.createNamedStoredProcedureQuery("makenewreservation");
		makeReservation.setParameter(1, shopId);
		makeReservation.setParameter(2, customerId);
		makeReservation.setParameter(3, bicycleId);
		makeReservation.setParameter(4, startTime);
		makeReservation.setParameter(5, endTime);
		makeReservation.setParameter(6, amount);
		makeReservation.execute();
	}


}
