package com.example.spring.calendar.controller;

import java.time.DayOfWeek;
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
@RequestMapping("/square")
public class SquareController {

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

		List<List<LocalDate>> dates = new ArrayList<>();
		List<LocalDate> weekDates = new ArrayList<>();
		dates.add(weekDates);

		while (start.getDayOfWeek() != DayOfWeek.MONDAY) {
			start = start.minusDays(1);
		}
		while (end.getDayOfWeek() != DayOfWeek.SATURDAY) {
			end = end.plusDays(1);
		}

		LocalDate curr = start.plusDays(0);
		weekDates.add(curr.plusDays(0));

		do {
			curr = curr.plusDays(1);
			if (curr.getDayOfWeek() == DayOfWeek.MONDAY) {
				weekDates = new ArrayList<>();
				dates.add(weekDates);

			}
			weekDates.add(curr);
		} while (curr.isBefore(end.plusDays(1)));

		model.addAttribute("startDate", start);
		model.addAttribute("endDate", end);
		model.addAttribute("list", dates);

		log.info("startDate => {}", start);
		log.info("endDate   => {}", end);
		log.info("list      => {}", dates);

		return "square";
	}
}
