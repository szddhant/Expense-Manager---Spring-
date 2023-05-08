package com.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entity.VendorEntity;

@Repository
public interface VendorRepository extends CrudRepository<VendorEntity, Integer>{
		
	VendorEntity findByVendorname(String vendorname);
	
	List<VendorEntity> findAll();
    
	VendorEntity findByVendorid(Integer vendorid);
	
}
