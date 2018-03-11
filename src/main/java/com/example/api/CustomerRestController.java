package com.example.api;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.domain.Customer;
import com.example.domain.ShuwaApiResult;
import com.example.service.CustomerService;

@RestController //(1)Restのエンドポイントとなる
@RequestMapping("api/customers") //(2)パスのルートを記載
public class CustomerRestController {
	@Autowired
	CustomerService customerService;

	@GetMapping("test")  //ロボホンテスト
	public ShuwaApiResult getShuwa(){
		return new ShuwaApiResult(3,4);
	}
	
	@GetMapping //(4)HTTPメソッドのGETを割りあてる。パスを割り当てないと、@RequestMappingのパスが指定される
	public Page<Customer> getCustomers(@PageableDefault Pageable pageable){
		Page<Customer> customers = customerService.findAll(pageable);
		return customers;
	}
	@GetMapping(path = "{id}") //(5)相対パス指定。さらに@PathVariableで引数として取れる（引数名とプレースホルダを紐付る）
	public Customer getCustomer(@PathVariable Integer id){
		return customerService.findOne(id);
	}
	@PostMapping //(6)POSTメソッドのアノテーション
	@ResponseStatus(HttpStatus.CREATED) //(7)HTTPレスポンスを設定できる。この場合201 Createdが返る。
	ResponseEntity<Customer> postCustomers(@Validated @RequestBody Customer customer,UriComponentsBuilder uriBuilder){//相対URIを構築するのに便利
		customer = customerService.create(customer);
		URI location = uriBuilder.path("api/customers/{id}").buildAndExpand(customer.getId()).toUri();
		return ResponseEntity.created(location).body(customer);
	}
	@PutMapping(path = "{id}")
	Customer putCustomer(@PathVariable Integer id,@RequestBody Customer customer){//@PathVariable:pathで設定された値を引数として渡す
		customer.setId(id);
		return customerService.update(customer);
	}
	@DeleteMapping(path = "{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) //204 No Contentを返す
	void deleteCustomer(@PathVariable Integer id){
		customerService.delete(id);
	}
}
