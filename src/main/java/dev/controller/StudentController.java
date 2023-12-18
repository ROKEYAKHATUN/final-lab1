package dev.controller;

import dev.domain.Student;
import dev.service.StudentService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @RequestMapping("/create")
    public String createStudent(Model model) {
        model.addAttribute("student", new Student());
        return "registration";
    }

    @RequestMapping("/store")
    public String storeStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "registration";
        } else {
            studentService.create(student);
            return "confirm";
        }
    }

    @RequestMapping("/students")
    public String getAllStudents(Model model) throws SQLException {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "students";
    }

    @RequestMapping("/students/{id}")
    public String findStudent(@PathVariable int id, Model model) throws SQLException {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "find";
    }

    @RequestMapping(value = "students/{id}/edit", method = RequestMethod.GET)
    public String editStudentForm(@PathVariable int id, Model model) throws SQLException {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "editStudent";
    }

    @RequestMapping(value = "students/{id}/edit", method = RequestMethod.POST)
    public String updateStudent(@PathVariable int id, @Valid @ModelAttribute("student") Student student, BindingResult bindingResult) throws SQLException {
        studentService.updateStudentById(id, student);
        return "redirect:/students";
    }


    @RequestMapping("/students/{id}/delete")
    public String deleteStudent(@PathVariable int id) throws SQLException {
        studentService.deleteStudentById(id);
        return "redirect:/students";
    }


}
