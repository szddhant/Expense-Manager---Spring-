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
import com.entity.SubCategoryEntity;
import com.repository.SubCategoryRepository;

@CrossOrigin
@RestController
public class SubCategoryController {
	
	@Autowired
	SubCategoryRepository subCategoryRepo;
	
	@PostMapping("/addSubCategory")
	public ResponseEntity<CustomResponse<SubCategoryEntity>> addSubCategoryy(@RequestBody SubCategoryEntity subCategoryEntity)
	{
		SubCategoryEntity subCategoryName = subCategoryRepo.findBySubcategoryname(subCategoryEntity.getSubcategoryname());
		CustomResponse<SubCategoryEntity> resp = new CustomResponse<>();
		
		if(subCategoryName == null)
		{
			subCategoryRepo.save(subCategoryEntity);
    		
    		resp.setData(subCategoryEntity);
    		resp.setMsg("SubCategory Addes Successfully");
    		return ResponseEntity.ok(resp);
		}
		else
    	{	
    		resp.setMsg("SubCategory Already Exist");
    		return ResponseEntity.unprocessableEntity().body(resp);
    	}
	}
	
	@GetMapping("/getAllSubCategory")
	public ResponseEntity<CustomResponse<List<SubCategoryEntity>>> getAllSubCategory() {

		List<SubCategoryEntity> subcategory = subCategoryRepo.findAll();
		CustomResponse<List<SubCategoryEntity>> resp = new CustomResponse<>();
		resp.setData(subcategory);
		resp.setMsg("All SubCategory Feched");

		return ResponseEntity.ok(resp);
	}
	
	 @DeleteMapping("/deleteSubCategoryById/{subcategoryid}")
	    public ResponseEntity<CustomResponse<SubCategoryEntity>> deleteSubCategoryByIdd(@PathVariable("subcategoryid") Integer subcategoryid)
	    {
	    	SubCategoryEntity categoryEntity = subCategoryRepo.findBySubcategoryid(subcategoryid);
	    	subCategoryRepo.deleteById(subcategoryid);
	    	
	    	CustomResponse<SubCategoryEntity> resp = new CustomResponse<>();
	    	
	    	resp.setData(categoryEntity);
	    	resp.setMsg("SubCategory Deleted Successfully");
	    	return ResponseEntity.ok(resp);    
	    	
	    }
	
	
}
