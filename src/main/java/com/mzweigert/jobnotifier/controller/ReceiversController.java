package com.mzweigert.jobnotifier.controller;

import com.mzweigert.jobnotifier.model.Job;
import com.mzweigert.jobnotifier.model.Receiver;
import com.mzweigert.jobnotifier.service.ReceiverService;
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
import java.util.Set;

@Controller
@RequestMapping("/receivers")
public class ReceiversController {

	private final ReceiverService receiverService;
	private final SourcePageService sourcePageService;

	@Autowired
	public ReceiversController(ReceiverService receiverService, SourcePageService sourcePageService) {
		this.receiverService = receiverService;
		this.sourcePageService = sourcePageService;
	}

	@GetMapping
	public String getAll(Model model) {
		Iterable<Receiver> all = receiverService.findAll();
		model.addAttribute("receivers", all);
		return "list-receivers";
	}

	@GetMapping(path = {"/new", "/edit/{id}"})
	public String newOrEdit(Model model, @PathVariable("id") Optional<Long> id) {
		if (id.isPresent()) {
			Optional<Receiver> entity = receiverService.findById(id.get());
			entity.ifPresent(receiver -> {
				model.addAttribute("receiver", receiver);
				Set<Job> sentJobsByReceiverId = receiverService.findSentJobsByReceiverId(receiver.getId());
				model.addAttribute("sentJobs", sentJobsByReceiverId);
			});
		} else {
			model.addAttribute("receiver", new Receiver());
		}
		model.addAttribute("sourcePages", sourcePageService.findAll());
		return "add-edit-receiver";
	}

	@GetMapping(path = {"/delete/{id}"})
	public String delete(Model model, @PathVariable("id") Long id) {
		try {
			receiverService.deleteById(id);
			return "redirect:/receivers";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "error";
	}

	@PostMapping(path = {"/save"})
	public String save(Model model, @Valid Receiver receiver, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("sourcePages", sourcePageService.findAll());
			return "add-edit-receiver";
		}
		Set<Job> sentJobs = receiverService.findSentJobsByReceiverId(receiver.getId());
		receiver.setSentJobs(sentJobs);
		receiverService.merge(receiver);
		return "redirect:/receivers";
	}
}
