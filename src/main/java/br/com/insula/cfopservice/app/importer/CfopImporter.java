package br.com.insula.cfopservice.app.importer;

import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import br.com.insula.cfopservice.app.Cfop;
import br.com.insula.cfopservice.app.CodigoFiscal;
import br.com.insula.cfopservice.app.Grupo;
import br.com.insula.cfopservice.app.SubGrupo;
import br.com.insula.cfopservice.configuration.root.CfopServiceConfig;

@Component
public class CfopImporter {

	@Autowired
	private MongoOperations mongoOperations;

	private static enum TokenAnterior {
		CODIGO, DESCRICAO;
	}

	public void importar() {
		Scanner scanner = new Scanner(getClass().getResourceAsStream("/cfop.csv"));
		scanner.useDelimiter("\\||\n");
		TokenAnterior tokenAnterior = null;
		Grupo grupo = null;
		SubGrupo subGrupo = null;
		Cfop cfop = null;
		CodigoFiscal codigoFiscal = null;
		while (scanner.hasNext()) {
			String next = scanner.next();
			String digitos = next.trim().replaceAll("\\D", "");
			if (digitos.matches("\\d{4}")) {
				int codigo = Integer.parseInt(digitos);
				System.out.println("Codigo: " + codigo);
				if (digitos.endsWith("000")) {
					System.out.println("Grupo encontrado");
					grupo = mongoOperations.findOne(query(where("codigo").is(codigo)), Grupo.class);
					if (grupo == null) {
						grupo = new Grupo();
					}
					codigoFiscal = grupo;
				}
				else if (digitos.endsWith("00") || digitos.endsWith("50")) {
					System.out.println("SubGrupo encontrado");
					subGrupo = mongoOperations.findOne(query(where("codigo").is(codigo)), SubGrupo.class);
					if (subGrupo == null) {
						subGrupo = new SubGrupo();
					}
					subGrupo.setGrupo(grupo);
					codigoFiscal = subGrupo;
				}
				else {
					System.out.println("Cfop encontrado");
					cfop = mongoOperations.findOne(query(where("codigo").is(codigo)), Cfop.class);
					if (cfop == null) {
						cfop = new Cfop();
					}
					cfop.setSubGrupo(subGrupo);
					codigoFiscal = cfop;
				}
				codigoFiscal.setCodigo(codigo);
				tokenAnterior = TokenAnterior.CODIGO;
			}
			else {
				switch (tokenAnterior) {
				case CODIGO:
					System.out.println("Descricao: " + next);
					codigoFiscal.setDescricao(next);
					tokenAnterior = TokenAnterior.DESCRICAO;
					break;
				case DESCRICAO:
					System.out.println("Aplicacao: " + next);
					codigoFiscal.setAplicacao(next);
					break;
				}
				mongoOperations.save(codigoFiscal);
			}
		}
	};

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.getEnvironment().setDefaultProfiles("homologacao");
		context.register(CfopServiceConfig.class);
		context.refresh();
		CfopImporter cfopImporter = context.getBean(CfopImporter.class);
		cfopImporter.importar();
	}

}
