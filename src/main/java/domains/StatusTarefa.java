package domains;


public enum StatusTarefa {
	CRIADA(1, "Criaca"), FINALIZADA(2, "Finalizada");

	private Integer cod;
	private String tipo;

	private StatusTarefa(Integer cod, String tipo) {
		this.cod = cod;
		this.tipo = tipo;
	}

	public Integer getCod() {
		return cod;
	}

	public String getTipo() {
		return tipo;
	}

	public static StatusTarefa toEnum(Integer cod) {
		if (cod == null) {
			throw new IllegalArgumentException("tipo inv�lido");

		}
		for (StatusTarefa st : StatusTarefa.values()) {
			if (st.getCod() == cod) {
				return st;
			}
		}
		return null;
	}

}
