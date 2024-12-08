package com.crud.spring_boot_crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.crud.spring_boot_crud.model.Formulario;
import com.crud.spring_boot_crud.repository.FormularioRepository;

@Controller
public class FormularioController {

    @Autowired
    private FormularioRepository formularioRepository;

    // Obtener informacion de la base de datos
    @GetMapping("/listarFormularios")
    public String listarFormularios(Model model) {
        List<Formulario> formularios = formularioRepository.findAll();
        model.addAttribute("forms", formularios);
        model.addAttribute("form", new Formulario());
        return "lista";
    }

    // Registrar datos en la base de datos
    @PostMapping("/submitFormulario")
    public String procesarFormulario(@ModelAttribute Formulario formulario, Model model) {
        formularioRepository.save(formulario);
        return "redirect:/listarFormularios";
    }

    // para editar
    @GetMapping("/editarFormulario/{id}")
    public String editarFormulario(@PathVariable("id") long id, Model model) {

        return formularioRepository.findById(id).map(formulario -> {
            model.addAttribute("form", formulario);
            return "formulario";
        }).orElse("redirect:/listarFormularios");
    }

    // para modificar el formulario
    @PostMapping("/submitEdicionFormulario")
    public String submitEdicionFormulario(@ModelAttribute Formulario formulario) {
        formularioRepository.findById(formulario.getId()).ifPresent(formExiste -> {
            formExiste.setNombre(formulario.getNombre());
            formExiste.setEmail(formulario.getEmail());
            formExiste.setMensaje(formulario.getMensaje());
            formularioRepository.save(formExiste);
        });
        return "redirect:/listarFormularios";
    }

    // Eliminar formulario
    @GetMapping("/eliminarFormulario/{id}")
    public String eliminarFormulario(@PathVariable("id") long id) {
        formularioRepository.findById(id).ifPresent(Formulario -> {
            formularioRepository.delete(Formulario);
        });
        return "redirect:/listarFormularios";
    }

}
