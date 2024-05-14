package com.errs.management.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.errs.management.dao.PurchaseDAO;
import com.errs.management.dao.UserDAO;
import com.errs.management.dto.ProductDTO;
import com.errs.management.entities.Purchase;
import com.errs.management.entities.User;
import com.errs.management.jwt.JwtFilter;
import com.errs.management.service.ProductService;
import com.errs.management.service.PurchaseService;
import com.errs.management.service.UserService;
import com.errs.management.utils.EmailUtils;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private PurchaseDAO purchaseDAO;

	@Autowired
	UserDAO userDAO;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	JwtFilter jwtFilter;

	@Autowired
	EmailUtils emailUtils;

	@Override
	public ResponseEntity<String> addProduct(Purchase purchase) {
		try {
			// Get the product and user details
			ResponseEntity<ProductDTO> productResponse = productService.getProductById(purchase.getProduct().getId());
			ResponseEntity<User> userResponse = userService.getUserById(purchase.getUser().getUserId());

			// Check if both product and user exist
			if (productResponse != null && userResponse != null && userResponse.getBody() != null
					&& productResponse.getBody() != null) {
				ProductDTO product = productResponse.getBody();
				User user = userResponse.getBody();

				// Check if user has enough points and product is in stock
				if (user.getPoints() >= product.getPoints() && product.getQuantityAvailable() > 0) {
					int points = user.getPoints() - product.getPoints();

					// Update user points
					userService.updateUser(user, points);

					// Decrease product stock
					productService.buyProduct(product);

					// Set purchase points
					purchase.setPoints(product.getPoints());

					// Save purchase
					purchaseDAO.save(purchase);

					// Send email notification to all admins
					sendPurchaseNotificationToAdmins(user, product);

					return ResponseEntity.ok("Purchase successful: Order confirmed");
				} else {
					return ResponseEntity.ok("Purchase failed: Required points not available or product out of stock");
				}
			} else {
				return ResponseEntity.ok("Purchase failed: Product or user not found");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing order");
		}
	}

	private void sendPurchaseNotificationToAdmins(User user, ProductDTO product) {
		// Fetch all admin emails
		List<String> adminEmails = userDAO.getAllAdmin(); // Make sure UserService has a method that calls
															// UserDAO.getAllAdmin()

		// Optionally remove the current user if they are an admin to avoid sending them
		// an email
		String currentUserEmail = jwtFilter.getCurrentUser();
		adminEmails.remove(currentUserEmail);

		// Construct the email message
		String subject = "New Product Purchase";
		String message = String.format(
				"A purchase has been made by \"%s\" (%s).\n\nProduct Details:\n- Name: %s\n- Points: %d",
				user.getName(), user.getEmail(), product.getName(), product.getPoints());

		// Send the email
		emailUtils.sendSimpleMessage(currentUserEmail, subject, message, adminEmails);
	}

	@Override
	public ResponseEntity<List<Purchase>> getPurchaseByUserId(int userId) {
		// TODO Auto-generated method stub
		try {
			// Fetch the purchase history for the user with the given userId
			List<Purchase> purchaseHistory = purchaseDAO.findByUserUserId(userId);
			// Always return 200 OK with the list (empty if no purchases found)
			return ResponseEntity.ok(purchaseHistory);
		} catch (Exception ex) {
			ex.printStackTrace();
			// If an exception occurs, log the exception and return an internal server error
			// response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
		}
	}

}
