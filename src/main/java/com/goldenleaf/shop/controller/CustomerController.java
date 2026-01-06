package com.goldenleaf.shop.controller;
import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goldenleaf.shop.dto.ChangePasswordRequest;
import com.goldenleaf.shop.dto.CustomerDTO;
import com.goldenleaf.shop.dto.CustomerResponseDTO;
import com.goldenleaf.shop.exception.EmptyLoginException;
import com.goldenleaf.shop.exception.EmptyNameException;
import com.goldenleaf.shop.exception.IncorrectEmailException;
import com.goldenleaf.shop.exception.IncorrectMobileException;
import com.goldenleaf.shop.model.Customer;
import com.goldenleaf.shop.service.CustomerService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequiredArgsConstructor
public class CustomerController {
	
	private final CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
		
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Customer> get(@PathVariable Long id) {
	
		return ResponseEntity.ok(customerService.get(id));
	}
	

	@PostMapping("/register")
	public ResponseEntity<CustomerResponseDTO> create(@RequestBody CustomerDTO dto) throws EmptyNameException, IncorrectMobileException, IncorrectEmailException, EmptyLoginException {
	
		  Customer customer = customerService.create(dto);
		    CustomerResponseDTO response = new CustomerResponseDTO(
		        customer.getId(),
		        customer.getLogin(),
		        customer.getEmail(),
		        customer.getMobile()
		    );
		    return ResponseEntity.ok(response);
	}
	

	
	@PutMapping("/{id}")
	public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody CustomerDTO dto) throws EmptyNameException, IncorrectMobileException, IncorrectEmailException, EmptyLoginException {
	
		return ResponseEntity.ok(customerService.update(id, dto));
	}
	
	
	   
	   
	   @DeleteMapping("/{id}")
	    public ResponseEntity<Void> delete(@PathVariable Long id) {
		   	 customerService.deleteById(id);
	        return ResponseEntity.noContent().build();
	   }
	   
	   
	   @PostMapping("/login")
	   public ResponseEntity<CustomerResponseDTO> login(@RequestBody CustomerDTO dto,  HttpServletRequest request) throws EmptyLoginException {
		   Customer customer = customerService.login(dto.getLogin(), dto.getPassword());
		    // здесь создаём сессию и кладём пользователя
		   request.getSession(true).setAttribute("user", customer);
		   CustomerResponseDTO response = new CustomerResponseDTO(
		       customer.getId(),
		       customer.getLogin(),
		       customer.getEmail(),
		       customer.getMobile()
		   );
		   return ResponseEntity.ok(response);

	   }
	   
	   @GetMapping("/me")
	   public ResponseEntity<CustomerResponseDTO> me(HttpServletRequest request) throws EmptyLoginException {
	       Customer customer = (Customer) request.getSession().getAttribute("user");
	       if (customer == null) return ResponseEntity.status(401).build();
	       return ResponseEntity.ok(new CustomerResponseDTO(
	           customer.getId(),
	           customer.getLogin(),
	           customer.getEmail(),
	           customer.getMobile()
	       ));
	   }
	   


	   @PostMapping("/change-password")
	   public ResponseEntity<?> changePassword(
	           @RequestBody ChangePasswordRequest req,
	           HttpServletRequest request) {

	       // 1. Берём пользователя из сессии
	       Customer sessionUser = (Customer) request.getSession().getAttribute("user");
	       if (sessionUser == null) {
	           return ResponseEntity.status(401).body("Не авторизован");
	       }

	       // 2. Загружаем свежего пользователя из базы
	       Customer customer = customerService.get(sessionUser.getId());

	       try {
	           // 3. Меняем пароль и получаем обновлённого пользователя
	           Customer updatedCustomer = customerService.changePasswordAndReturn(
	               customer, req.getOldPassword(), req.getNewPassword()
	           );

	           // 4. Обновляем сессию, чтобы фронт получил актуальный объект
	           request.getSession().setAttribute("user", updatedCustomer);

	           return ResponseEntity.ok("Пароль успешно изменён");
	       } catch (IllegalArgumentException e) {
	           return ResponseEntity.status(400).body("Неверный старый пароль");
	       }
	   }

	   




}
