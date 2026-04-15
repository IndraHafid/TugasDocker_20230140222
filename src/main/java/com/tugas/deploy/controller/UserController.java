package com.tugas.deploy.controller;

import com.tugas.deploy.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private List<User> students = new ArrayList<>();
    private String currentUser = "";
    private String currentNim = "";

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username, 
                             @RequestParam String password,
                             RedirectAttributes redirectAttributes) {
        if ("admin".equals(username) && password.length() >= 8 && password.matches("\\d+")) {
            currentUser = username;
            currentNim = password;
            return "redirect:/home";
        } else {
            redirectAttributes.addAttribute("error", "Username atau password salah!");
            return "redirect:/login";
        }
    }

    @GetMapping("/home")
    public String home(Model model) {
        if (currentUser.isEmpty()) {
            return "redirect:/login";
        }
        model.addAttribute("username", currentUser);
        model.addAttribute("nim", currentNim);
        model.addAttribute("students", students);
        return "home";
    }

    @GetMapping("/form")
    public String form(Model model) {
        if (currentUser.isEmpty()) {
            return "redirect:/login";
        }
        model.addAttribute("username", currentUser);
        model.addAttribute("nim", currentNim);
        model.addAttribute("student", new User());
        return "form";
    }

    @PostMapping("/save")
    public String saveStudent(@ModelAttribute User student, 
                             RedirectAttributes redirectAttributes) {
        if (student.getNama() == null || student.getNama().trim().isEmpty() ||
            student.getNim() == null || student.getNim().trim().isEmpty() ||
            student.getJenisKelamin() == null || student.getJenisKelamin().trim().isEmpty()) {
            redirectAttributes.addAttribute("error", "Harap isi semua field dengan benar!");
            return "redirect:/form";
        }
        
        students.add(student);
        redirectAttributes.addAttribute("success", "Data mahasiswa berhasil disimpan!");
        return "redirect:/form";
    }

    @GetMapping("/logout")
    public String logout() {
        currentUser = "";
        currentNim = "";
        return "redirect:/login";
    }
}
