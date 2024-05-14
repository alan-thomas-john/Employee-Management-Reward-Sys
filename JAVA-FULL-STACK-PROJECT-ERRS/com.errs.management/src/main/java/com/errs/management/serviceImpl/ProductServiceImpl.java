package com.errs.management.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.errs.management.constants.ErrsConstants;
import com.errs.management.dao.ProductDAO;
import com.errs.management.dto.ProductDTO;
import com.errs.management.entities.Category;
import com.errs.management.entities.Product;
import com.errs.management.jwt.JwtFilter;
import com.errs.management.service.ProductService;
import com.errs.management.utils.ErrsUtils;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDAO productDAO;

	// only admin can add product
	@Autowired
	JwtFilter jwtFilter;

	@Override
	public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		try {
			// checking if current user is admin or not
			if (jwtFilter.isAdmin()) {
				// if its a valid user ,validating map
				if (validateProductMap(requestMap, false)) {
					productDAO.save(getProductFromMap(requestMap, false));// IN save getting product object,also same
																			// method is used for getting the product
																			// map while updating product
					return ErrsUtils.getResponseEntity("Product Added SuccessFully", HttpStatus.OK);
				}
				// if validation fails below message
				return ErrsUtils.getResponseEntity(ErrsConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
			} else {
				return ErrsUtils.getResponseEntity(ErrsConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ErrsUtils.getResponseEntity(ErrsConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
		// TODO Auto-generated method stub//boolean value ,this method is used for admin
		// product and update product also and to manage id and to check the pk or not
		// of the product
		if (requestMap.containsKey("name")) {// only validating name
			if (requestMap.containsKey("id") && validateId) {
				return true;
			} else if (!validateId) {
				return true;
			}
		}
		return false;
	}

	private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
		// TODO Auto-generated method stub
		// create object of category because to store the category reference in product
		Category category = new Category();
		// extracting category from requestMap set to cat
		category.setId(Integer.parseInt(requestMap.get("categoryId")));

		// creating product object
		Product product = new Product();
		if (isAdd) {
			// if isAdd then need to extract id from the requestMap and need to set into the
			// product
			product.setId(Integer.parseInt(requestMap.get("id")));
		} else {
			// making product status to true that is active by default
			product.setStatus("true");
		}

		// setting the category into the product
		product.setCategory(category);
		product.setName(requestMap.get("name"));
		product.setDescription(requestMap.get("description"));
		product.setPoints(Integer.parseInt(requestMap.get("points")));
		product.setQuantityAvailable(Integer.parseInt(requestMap.get("quantityAvailable")));
		product.setRedemptionConditions(requestMap.get("redemptionConditions"));
		product.setTermsAndConditions(requestMap.get("termsAndConditions"));
		product.setImageThumbnailUrl(requestMap.get("imageThumbnailUrl"));
		product.setManufacturer(requestMap.get("manufacturer"));
		return product;
	}

	@Override
	public ResponseEntity<List<ProductDTO>> getAllProduct() {
		// TODO Auto-generated method stub
		try {
			return new ResponseEntity<>(productDAO.getAllProduct(), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		try {
			// validating if current user admin or not
			if (jwtFilter.isAdmin()) {
				if (validateProductMap(requestMap, true)) {// validateid-true
					// fetch the product from database if it is in db or not
					Optional<Product> optional = productDAO.findById(Integer.parseInt(requestMap.get("id")));// return
																												// object
																												// of
																												// optional
																												// type
					// checking if the optional is empty
					if (!optional.isEmpty()) {
						Product product = getProductFromMap(requestMap, true);// isAdd checking true,then sets id for
																				// the product
						// set status
						product.setStatus(optional.get().getStatus());
						// save
						productDAO.save(product);
						return ErrsUtils.getResponseEntity("Product Updated SuccessFully", HttpStatus.OK);
					} else {
						return ErrsUtils.getResponseEntity("Product id does not exist", HttpStatus.OK);
					}
				} else {
					return ErrsUtils.getResponseEntity(ErrsConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
				}
			} else {
				return ErrsUtils.getResponseEntity(ErrsConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ErrsUtils.getResponseEntity(ErrsConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> deleteProduct(Integer id) {
		// TODO Auto-generated method stub
		try {
			// checking if it is an admin
			if (jwtFilter.isAdmin()) {
				// if it is a admin ,and checking if that id exist in the database
				Optional<Product> optional = productDAO.findById(id);
				if (!optional.isEmpty()) {
					// if we find product id then
					productDAO.deleteById(id);
					return ErrsUtils.getResponseEntity("Product Deleted Successfully", HttpStatus.OK);
				}
				return ErrsUtils.getResponseEntity("Product id does not exist.", HttpStatus.OK);
			} else {
				return ErrsUtils.getResponseEntity(ErrsConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ErrsUtils.getResponseEntity(ErrsConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		try {
			// checking if the user is an admin
			if (jwtFilter.isAdmin()) {
				// if that product exist with id or not
				Optional<Product> optional = productDAO.findById(Integer.parseInt(requestMap.get("id")));
				// if optional is empty
				if (!optional.isEmpty()) {
					productDAO.updateProductStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
					return ErrsUtils.getResponseEntity("Product Status Updated Successfully", HttpStatus.OK);
				}
				return ErrsUtils.getResponseEntity("Product id does not exist", HttpStatus.OK);
			} else {
				return ErrsUtils.getResponseEntity(ErrsConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ErrsUtils.getResponseEntity(ErrsConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<ProductDTO>> getByCategory(Integer id) {
		// TODO Auto-generated method stub
		try {
			return new ResponseEntity<>(productDAO.getProductByCategory(id), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<ProductDTO> getProductById(Integer id) {
		// TODO Auto-generated method stub
		try {
			return new ResponseEntity<>(productDAO.getProductById(id), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<>(new ProductDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> buyProduct(ProductDTO product) {
		// TODO Auto-generated method stub
		try {
			// Fetch the product from the database
			Optional<Product> optionalProduct = productDAO.findById(product.getId());

			// Check if the product exists
			if (optionalProduct.isPresent()) {
				Product existingProduct = optionalProduct.get();

				// Check if the product is in stock
				if (existingProduct.getQuantityAvailable() > 0) {
					// Reduce the available stock by 1
					existingProduct.setQuantityAvailable(existingProduct.getQuantityAvailable() - 1);

					// Save the updated product
					productDAO.save(existingProduct);

					return ErrsUtils.getResponseEntity("Product purchased successfully", HttpStatus.OK);
				} else {
					return ErrsUtils.getResponseEntity("Product is out of stock", HttpStatus.BAD_REQUEST);
				}
			} else {
				return ErrsUtils.getResponseEntity("Product not found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ErrsUtils.getResponseEntity("Error processing purchase", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
