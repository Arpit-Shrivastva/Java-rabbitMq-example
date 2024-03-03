package com.bej.product.controller;

import com.bej.product.domain.User;
import com.bej.product.domain.Product;
import com.bej.product.exception.UserAlreadyExistsException;
import com.bej.product.exception.UserNotFoundException;
import com.bej.product.exception.ProductNotFoundException;
import com.bej.product.service.IUserProductService;
import com.bej.product.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1")
public class UserProductController {
    // Autowire IUserProductService using constructor autowiring
    private IUserProductService userProductService;
    private ResponseEntity<?> responseEntity;

    @Autowired
    public UserProductController(IUserProductService userProductService) {
        this.userProductService = userProductService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody User user) throws UserAlreadyExistsException {
        // Register a new user and save to db,
        // return 201 status if user is saved else 500 status
        try {
            responseEntity = new ResponseEntity<>(userProductService.registerUser(user), HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException();
        }
        return responseEntity;
    }

    @PostMapping("/customer/saveProduct")
    public ResponseEntity<?> saveCustomerProductToList(@RequestBody Product product, HttpServletRequest request) throws UserNotFoundException {
        // add a product to a specific customer,
        // return 201 status if track is saved else 500 status
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String userId = claims.getSubject();
            responseEntity = new ResponseEntity<>(userProductService.saveUserProductToList(product, userId), HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        }
        return responseEntity;
    }

    @GetMapping("/customer/getAllProducts")
    public ResponseEntity<?> getAllCustomerProductsFromList(HttpServletRequest request) throws UserNotFoundException {
        // list all products of a specific customer,
        // return 200 status if track is saved else 500 status
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String userId = claims.getSubject();
            responseEntity = new ResponseEntity<>(userProductService.getAllUserProductsFromList(userId), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        }
        return responseEntity;
    }

    @DeleteMapping("/customer/{productCode}")
    public ResponseEntity<?> deleteCustomerProductFromList(@PathVariable String productCode, HttpServletRequest request) throws ProductNotFoundException {
        // delete product of a specific customer,
        // return 200 status if track is saved else 500 status
        Claims claims = (Claims) request.getAttribute("claims");
        String userId = claims.getSubject();
        try {
            responseEntity = new ResponseEntity<>(userProductService.deleteUserProductFromList(userId, productCode), HttpStatus.OK);
        } catch (UserNotFoundException | ProductNotFoundException m) {
            throw new ProductNotFoundException();
        }
        return responseEntity;
    }

//     Read the customer id present in the claims from the request
//    private String getCustomerIdFromClaims(HttpServletRequest request) {
//        return null;
//    }
}
