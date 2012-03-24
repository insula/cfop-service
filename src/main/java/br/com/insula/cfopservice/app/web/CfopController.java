package br.com.insula.cfopservice.app.web;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.insula.cfopservice.app.Cfop;

@Controller
public class CfopController {

	@Autowired
	private MongoOperations mongoOperations;

	@RequestMapping(value = "/{codigo}", produces = "application/json")
	@ResponseBody
	public Cfop find(@PathVariable int codigo) {
		Cfop cfop = mongoOperations.findOne(query(where("codigo").is(codigo)), Cfop.class);
		if (cfop == null) {
			throw new CfopNotFoundException();
		}
		return cfop;
	}

}