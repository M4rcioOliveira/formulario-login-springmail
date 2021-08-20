package br.com.apiteste.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.apiteste.model.Usuario;
import br.com.apiteste.repository.UsuarioRepository;

@RestController
@CrossOrigin(origins = "*")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@GetMapping("/send/{to}")
	public String sendMail(@PathVariable String to) {
		SimpleMailMessage message = new SimpleMailMessage();

		int code = usuarioRepository.findByEmail(to);

		message.setText("Você está a um passo para utilizar sua conta!" + " \nDigite o código: " + code);
		message.setFrom("testes.apisendemail@gmail.com");
		message.setSubject("Ativação");
		message.setTo(to);

		try {
			javaMailSender.send(message);
			return "Email enviado com sucesso!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao enviar email.";
		}
	}

	@PostMapping("/create/{email}/{password}")
	@ResponseBody
	public void create(@PathVariable String email, @PathVariable String password) {

		Usuario usuario = new Usuario();

		usuario.setEmail(email);
		usuario.setPassword(password);
		usuario.setSituacao(false);

		Random random = new Random();

		int a = random.nextInt((90000000 - 10000000) + 1) + 10000000;

		usuario.setCodigo(a);

		usuarioRepository.save(usuario);

	}

	int a = 4;

	@GetMapping("/confirmation/{code}/{email}")
	public String confirmacao(@PathVariable Integer code, @PathVariable String email) {

		int codeBD = usuarioRepository.findByEmail(email);
		String situacao = "";

		if(codeBD == code) {
			situacao = "valido";
			Usuario usuario = new Usuario();
			usuario = usuarioRepository.findByCodigo(code);
			usuario.setSituacao(true);
			usuarioRepository.saveAndFlush(usuario);
		}
		else {
			situacao = "invalido";
		}
		
		return situacao;
		

	}
	
	
	
	@GetMapping("/sendOk/{to}")
	public String sendMailOk(@PathVariable String to) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setText("Sua conta foi confirmada. Aproveite!");
		message.setFrom("seu email");
		message.setSubject("Confirmado");
		message.setTo(to);

		try {
			javaMailSender.send(message);
			return "Email enviado com sucesso!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao enviar email.";
		}
	}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	

}
