package com.thoughtworks;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.function.Consumer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/data")
public class EmployeeController {

    private final EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}



	@GetMapping("/{tableName}")
    public ResponseEntity<StreamingResponseBody> streamAllEmployees(HttpServletResponse response,@PathVariable String tableName) {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"employees.csv\"");

        StreamingResponseBody stream = out -> {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            Consumer<String> consumer = employee -> {
                try {
                    writer.write(employee.toString()); // Customize the format as needed
                    writer.newLine();
                } catch (IOException e) {
                    // Handle exception
                }
            };

            employeeService.streamAllEmployees(consumer,tableName);
            writer.flush();
        };

        return new ResponseEntity<>(stream, HttpStatus.OK);
    }
}
