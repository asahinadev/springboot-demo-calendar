package com.example.spring.calendar.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/list")
public class ListController {

	@GetMapping({
			"",
			"{year:[\\d]{4}}/{month:[\\d]{1,2}}"
	})
	public String index(
			Model model,
			@PathVariable(name = "year", required = false) Integer year,
			@PathVariable(name = "month", required = false) Integer month) {

		LocalDate start = LocalDate.now().withDayOfMonth(1);
		if (year != null) {
			start = start.withYear(year).withMonth(month);
		}
		LocalDate end = start.plusMonths(1).minusDays(1);

		List<LocalDate> dates = new ArrayList<>();

		LocalDate curr = start.plusDays(0);
		dates.add(curr.plusDays(0));
		do {
			dates.add(curr = curr.plusDays(1));
		} while (curr.isBefore(end));

		model.addAttribute("startDate", start);
		model.addAttribute("endDate", end);
		model.addAttribute("list", dates);

		log.info("startDate => {}", start);
		log.info("endDate   => {}", end);
		log.info("list      => {}", dates);

		return "list";
	}
}
