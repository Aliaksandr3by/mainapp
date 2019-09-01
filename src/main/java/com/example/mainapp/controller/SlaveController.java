package com.example.mainapp.controller;

import com.example.mainapp.model.SlaveContext;
import com.example.mainapp.model.entity.Slave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/slave", produces = "application/json")
@CrossOrigin(origins = "*")
public class SlaveController {

	private SlaveContext contextSlave;

	public SlaveController() {

	}

	@Autowired
	public SlaveController(@Qualifier("slaveContext") SlaveContext context) {
		this.contextSlave = context;
	}

	@GetMapping(value = "")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Slave> getEmployees() {
		try {
			return contextSlave.getSlave("idSlave");

		} catch (Throwable e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	@PostMapping(value = "/")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Slave saveEmployee(@RequestBody Slave item) {
		try {

			return contextSlave.createSlave(item);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}

	}
}
