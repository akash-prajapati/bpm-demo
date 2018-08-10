package com.easyjet.ei.commercials.claims.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestStream {
	public static void main(String args[]) {

		String fileName = "c://test.txt";
		List<String> list = new ArrayList<>();
		StringBuilder stringBuilder = new StringBuilder();
		String response = null;
		for(int i=0;i<10;i++){
		try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {

			//br returns as stream and convert it into a List
			//list = br.lines().collect(Collectors.toList());
			 String output;
			while ((output = br.readLine()) != null) {
				stringBuilder.append(output);
			}
			
			stringBuilder.append(br);
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		response = stringBuilder.toString();
		System.out.println(response);
		//list.forEach(System.out::println);

	}
}
