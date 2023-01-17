package com.example.spring;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.JsonParser;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins="http://localhost:3000")
@SpringBootApplication
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/hello")
	public String sayHello() {
		System.out.println("serv");
		String str="naruto";
		return String.format(str);
	}

	@PostMapping("/nothello")
	public String setname(@RequestBody String name) { 
		System.out.println(name);
		return String.format("hello ");
	}

	@PostMapping("/login")
	public String login(@RequestBody Map<String, String> loginCredentials) throws JsonProcessingException {
		System.out.println((loginCredentials));
		/*for (String name : loginCredentials.keySet())
			System.out.println("key: " + name);

		for (String url : loginCredentials.values())
			System.out.println("value: " + url);*/

		Iterator<Map.Entry<String, String>> itr = loginCredentials.entrySet().iterator();
		while(itr.hasNext())
		{
			Map.Entry<String, String> entry = itr.next();
			if(entry.getKey()== "username"){
				int ret=checkLoginCred(entry.getValue());
				return String.valueOf(ret);
			}
			/*System.out.println("Key = " + entry.getKey() +
					", Value = " + entry.getValue());*/
		}
		//checkLoginCred("username");
		return String.format(String.valueOf(loginCredentials));
	}

	public int checkLoginCred(String username)
	{
		Connection connection = null;
		PreparedStatement psInsert = null;
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginsystem", "root", "Rubaiyat26");

			/**
			 * Here sonoo is database name, root is username and password
			 * Statement stmt = con.createStatement();
			 * ResultSet rs = stmt.executeQuery("select * from users where name = ?");
			 **/
			psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
			System.out.println(username);
			psCheckUserExists.setString(1, username);
			resultSet = psCheckUserExists.executeQuery();
			/**/
			if (resultSet.isBeforeFirst()) {
				System.out.println("found");

				while (resultSet.next()) {
					System.out.println(resultSet.getInt(1) + "  " + resultSet.getString(2) + "  " + resultSet.getString(3));
				}
				connection.close();
				return 1;
			}
			else {
				System.out.println("no user");
				return 0;

			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0;
	}




}
