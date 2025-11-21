package entities;

import java.time.LocalDate;

public class Pedido {
    private Long id;
    private boolean eliminado;
    private String numero;
    private LocalDate fecha;
    private String clienteNombre;
    private double total;
    private String estado;
    private Envio envio;

    public Pedido() {}

    public Pedido(Long id, boolean eliminado, String numero, LocalDate fecha,
                  String clienteNombre, double total, String estado, Envio envio) {
        this.id = id;
        this.eliminado = eliminado;
        this.numero = numero;
        this.fecha = fecha;
        this.clienteNombre = clienteNombre;
        this.total = total;
        this.estado = estado;
        this.envio = envio;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Envio getEnvio() { return envio; }
    public void setEnvio(Envio envio) { this.envio = envio; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔═══════════════════════════════════════════╗\n");
        sb.append("║              PEDIDO                       ║\n");
        sb.append("╠═══════════════════════════════════════════╣\n");
        sb.append(String.format("║ ID:            %-27s║\n", id));
        sb.append(String.format("║ Número:        %-27s║\n", numero));
        sb.append(String.format("║ Fecha:         %-27s║\n", fecha));
        sb.append(String.format("║ Cliente:       %-27s║\n", clienteNombre));
        sb.append(String.format("║ Total:         $%-26.2f║\n", total));
        sb.append(String.format("║ Estado:        %-27s║\n", estado));
        sb.append("╠═══════════════════════════════════════════╣\n");
        
        if (envio != null) {
            sb.append("║              ENVÍO ASOCIADO               ║\n");
            sb.append("╠═══════════════════════════════════════════╣\n");
            sb.append(String.format("║ ID Envío:      %-27s║\n", envio.getId()));
            sb.append(String.format("║ Tracking:      %-27s║\n", envio.getTracking()));
            sb.append(String.format("║ Empresa:       %-27s║\n", envio.getEmpresa()));
            sb.append(String.format("║ Tipo:          %-27s║\n", envio.getTipo()));
            sb.append(String.format("║ Costo:         $%-26.2f║\n", envio.getCosto()));
            sb.append(String.format("║ Estado Envío:  %-27s║\n", envio.getEstado()));
        } else {
            sb.append("║ ⚠ SIN ENVÍO ASOCIADO                     ║\n");
        }
        
        sb.append("╚═══════════════════════════════════════════╝");
        return sb.toString();
    }
}

