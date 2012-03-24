package br.com.insula.cfopservice.app;

import static com.google.common.base.Preconditions.checkArgument;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Ranges;

@Document
public class SubGrupo implements CodigoFiscal {

	@JsonIgnore
	String id;

	@Indexed
	private int codigo;

	private String descricao;

	@DBRef
	private Grupo grupo;

	public SubGrupo(int codigo, String descricao, String aplicacao) {
		checkArgument(Ranges.openClosed(1000, 9999).contains(codigo));
		checkArgument(!Strings.isNullOrEmpty(descricao));
		checkArgument(!Strings.isNullOrEmpty(aplicacao));
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public SubGrupo() {
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SubGrupo) {
			SubGrupo other = (SubGrupo) obj;
			return Objects.equal(this.codigo, other.codigo);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.codigo);
	}

	public String getId() {
		return id;
	}

	public int getCodigo() {
		return codigo;
	}

	@Override
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	@Override
	public void setAplicacao(String aplicacao) {
	}

}