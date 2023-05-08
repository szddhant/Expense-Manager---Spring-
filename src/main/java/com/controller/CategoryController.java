package com.controller;

import java.util.List;
import java.util.Optional;

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
import com.entity.CategoryEntity;
import com.repository.CategoryRepository;
import com.repository.SubCategoryRepository;

@CrossOrigin
@RestController
public class CategoryController {
	
	@Autowired
	CategoryRepository categoryRepo;
	

	

	
	@PostMapping("/addCategory")
	public ResponseEntity<CustomResponse<CategoryEntity>> addCategory(@RequestBody CategoryEntity categoryEntity){
		CategoryEntity categoryName = categoryRepo.findBycategoryName(categoryEntity.getCategoryName());
		CustomResponse<CategoryEntity> resp = new CustomResponse<>();
		if(categoryName == null) {
			categoryRepo.save(categoryEntity);
			resp.setData(categoryEntity);
			resp.setMsg("Category added Successfully!");
			return ResponseEntity.ok(resp);
		}else {
			resp.setMsg("Category already exists!");
			return ResponseEntity.unprocessableEntity().body(resp);
		}
	}
	
	
	@GetMapping("/getAllCategory")
	public ResponseEntity<CustomResponse<List<CategoryEntity>>> getAllCategory(){
		List<CategoryEntity> category = categoryRepo.findAll();
		CustomResponse<List<CategoryEntity>> resp = new CustomResponse<>();
		resp.setData(category);
		resp.setMsg("All the categories are listed below : ");
		return ResponseEntity.ok(resp);
	}

	@DeleteMapping("/deleteCategoryById/{cid}")
	public ResponseEntity<CustomResponse<CategoryEntity>> deletecategoryByidd(@PathVariable("cid")Integer cid) {
	
		CategoryEntity categoryEntity = categoryRepo.findBycid(cid);
		categoryRepo.deleteById(cid);
		
		CustomResponse<CategoryEntity> resp = new CustomResponse<>();
		resp.setData(categoryEntity);
		resp.setMsg("Category Deleted Successfully!");
		return ResponseEntity.ok(resp);
	}
		

}
