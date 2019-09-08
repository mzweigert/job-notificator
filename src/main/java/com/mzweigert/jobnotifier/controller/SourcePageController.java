package com.mzweigert.jobnotifier.controller;

import com.mzweigert.jobnotifier.model.SourcePage;
import com.mzweigert.jobnotifier.service.SourcePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/sourcePages")
public class SourcePageController {

	private SourcePageService service;

	@Autowired
	public SourcePageController(SourcePageService service) {
		this.service = service;
	}

	@GetMapping
	public String getAll(Model model) {
		Iterable<SourcePage> all = service.findAll();
		model.addAttribute("sourcePages", all);
		return "list-source-pages";
	}

	@GetMapping(path = {"/new", "/edit/{id}"})
	public String newOrEdit(Model model, @PathVariable("id") Optional<Long> id) {
		if (id.isPresent()) {
			Optional<SourcePage> entity = service.findById(id.get());
			model.addAttribute("sourcePage", entity);
		} else {
			model.addAttribute("sourcePage", new SourcePage());
		}
		return "add-edit-source-page";
	}

	@GetMapping(path = {"/delete/{id}"})
	public String delete(Model model, @PathVariable("id") Long id) {
		try {
			service.deleteById(id);
			return "redirect:/sourcePages";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "error";
	}

	@PostMapping(path = {"/save"})
	public String save(@Valid SourcePage sourcePage, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "add-edit-source-page";
		}
		service.merge(sourcePage);
		return "redirect:/sourcePages";
	}

}
