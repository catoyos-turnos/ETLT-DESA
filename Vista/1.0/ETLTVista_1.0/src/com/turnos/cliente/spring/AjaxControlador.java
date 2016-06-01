package com.turnos.cliente.spring;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({ "/ajax" })
public class AjaxControlador {

	@RequestMapping(value = "/contadores", method = RequestMethod.GET)
	public @ResponseBody String getTime() {

		Random rand = new Random();
		int r = rand.nextInt(5);
//		String result = "<br>Next Random # is <b>" + r + "</b>. Generated on <b>" + new Date().toString() + "</b>";
//		System.out.println("Debug Message from CrunchifySpringAjaxJQuery Controller.." + new Date().toString());
//		return result;
		return r + "";
	}
}
