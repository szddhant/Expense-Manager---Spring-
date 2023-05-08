package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bean.CustomResponse;
import com.entity.VendorEntity;
import com.repository.VendorRepository;

@CrossOrigin
@RestController
public class VendorController {

	@Autowired
	VendorRepository vendorRepository;
	
	@PostMapping("/addVendor")
	public ResponseEntity<CustomResponse<VendorEntity>>  addvendorr(@RequestBody VendorEntity vendorEntity)
	{
		VendorEntity vendorName = vendorRepository.findByVendorname(vendorEntity.getVendorname());
    	
    	CustomResponse<VendorEntity> resp = new CustomResponse<>();
    	
    	if(vendorName == null)
    	{
    		vendorRepository.save(vendorEntity);
    		
    		resp.setData(vendorEntity);
    		resp.setMsg("Vendor Added Successfully");
    		return ResponseEntity.ok(resp);
    	}
    	else
    	{	
    		resp.setMsg("Vendor Already Exist");
    		return ResponseEntity.unprocessableEntity().body(resp);
    	}
	}
	
	    @GetMapping("/getAllVendor")
		public ResponseEntity<CustomResponse<List<VendorEntity>>> getAllVendor() {

			List<VendorEntity> vendorlist = vendorRepository.findAll();
			CustomResponse<List<VendorEntity>> resp = new CustomResponse<>();
			resp.setData(vendorlist);
			resp.setMsg("All Vendors are listed above : ");

			return ResponseEntity.ok(resp);
		}
	    
	    @DeleteMapping("/deleteVendorById/{vendorid}")
	    public ResponseEntity<CustomResponse<VendorEntity>> deleteCategoryByIdd(@PathVariable("vendorid") Integer vendorid)
	    {
	    	VendorEntity vendorEntity = vendorRepository.findByVendorid(vendorid);
	    	vendorRepository.deleteById(vendorid);
	    	
	    	CustomResponse<VendorEntity> resp = new CustomResponse<>();
	    	
	    	resp.setData(vendorEntity);
	    	resp.setMsg("Vendor Deleted Successfully");
	    	return ResponseEntity.ok(resp);
	    	
	    }
	
}
