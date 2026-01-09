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
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.util.Collections;
import java.util.List;
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
	public ResponseEntity<CustomerResponseDTO> update(
	        @PathVariable("id") Long id,
	        @RequestBody CustomerDTO dto,
	        HttpServletRequest request
	) throws EmptyNameException, IncorrectMobileException, IncorrectEmailException, EmptyLoginException {
		

	    Customer updatedCustomer = customerService.update(id, dto);
	 
	    request.getSession().setAttribute("user", updatedCustomer);

	    return ResponseEntity.ok(
	        new CustomerResponseDTO(
	            updatedCustomer.getId(),
	            updatedCustomer.getLogin(),
	            updatedCustomer.getEmail(),
	            updatedCustomer.getMobile()
	        )
	    );
	}

	   
	   
	   @DeleteMapping("/{id}")
	    public ResponseEntity<Void> delete(@PathVariable Long id) {
		   	 customerService.deleteById(id);
	        return ResponseEntity.noContent().build();
	   }
	   
	   
//	   @PostMapping("/login")
//	   public ResponseEntity<CustomerResponseDTO> login(@RequestBody CustomerDTO dto,  HttpServletRequest request) throws EmptyLoginException {
//		   Customer customer = customerService.login(dto.getLogin(), dto.getPassword());
//		    // здесь создаём сессию и кладём пользователя
//		   request.getSession(true).setAttribute("user", customer);
//	
//		   CustomerResponseDTO response = new CustomerResponseDTO(
//		       customer.getId(),
//		       customer.getLogin(),
//		       customer.getEmail(),
//		       customer.getMobile()
//		   );
//		   return ResponseEntity.ok(response);
//
//	   }
	   
	   @PostMapping("/login")
	   public ResponseEntity<CustomerResponseDTO> login(
	           @RequestBody CustomerDTO dto, 
	           HttpServletRequest request) throws EmptyLoginException {

	       Customer customer = customerService.login(dto.getLogin(), dto.getPassword());

	       // 1. Твоя старая логика — кладём в сессию (оставляем, если где-то используется)
	       HttpSession session = request.getSession(true);
	       session.setAttribute("user", customer);

	       // 2. НОВАЯ ОБЯЗАТЕЛЬНАЯ ЧАСТЬ: устанавливаем Spring Security аутентификацию
	       List<GrantedAuthority> authorities = Collections.singletonList(
	           new SimpleGrantedAuthority("ROLE_USER") // или "ROLE_ADMIN", если нужно
	       );

	       Authentication authentication = new UsernamePasswordAuthenticationToken(
	           customer,      // principal — сам объект Customer
	           null,          // credentials (пароль не нужен после логина)
	           authorities    // роли
	       );

	       SecurityContextHolder.getContext().setAuthentication(authentication);

	       // 3. (Опционально, но рекомендуется) Сохраняем SecurityContext в сессию
	       // Это нужно, чтобы аутентификация сохранялась между запросами
	       session.setAttribute(
	           HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
	           SecurityContextHolder.getContext()
	       );

	       // 4. Возвращаем DTO
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
