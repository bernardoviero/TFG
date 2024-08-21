public class Agente {
	public int id;
	public float posicao_x;
	public float posicao_y;
	public float velocidade;
	public float altitude;
	public int combustivel;
	public String visao;
	
	public Agente(int id, float posicao_x, float posicao_y, String visao, float velocidade, float altitude, int combustivel) {
		super();
		this.id = id;
		this.posicao_x = posicao_x;
		this.posicao_y = posicao_y;
		this.combustivel = combustivel;
		this.visao = visao;
		this.velocidade = velocidade;
		this.altitude = altitude;
	}

	@Override
	public String toString() {
		return "Agent [id=" + id + ", posicao_x=" + posicao_x + ", posicao_y=" + posicao_y
				+ ", velocidade=" + velocidade + ", altitude=" + altitude + ", combustivel=" + combustivel + ", visao=" + visao + "]";
	}

}
