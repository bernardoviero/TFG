package env;

public class Aviao {

    private String id;
    private String posicao;
    private String visao;
    private String coordenadas;
    private int altitude;
    private int combustivel;

    public Aviao(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public String getVisao() {
        return visao;
    }

    public void setVisao(String visao) {
        this.visao = visao;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(int combustivel) {
        this.combustivel = combustivel;
    }

    public void mover(int dx, int dy, int dz) {
        System.out.println("Movendo avião " + id + " para (" + dx + ", " + dy + ", " + dz + ")");
    }
}
