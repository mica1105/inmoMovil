package com.example.inmobiliariamovil.modelo;

import java.io.Serializable;

public class Pago implements Serializable {

    private int id;
    private int nroPago;
    private Contrato contrato;
    private double importe;
    private String fecha;

    public Pago() {}

    public Pago(int idPago, int numero, Contrato contrato, double importe, String fechaDePago) {
        this.id = idPago;
        this.nroPago = numero;
        this.contrato = contrato;
        this.importe = importe;
        this.fecha = fechaDePago;
    }

    public int getId() {
        return id;
    }

    public void setId(int idPago) {
        this.id= idPago;
    }

    public int getNroPago() {
        return nroPago;
    }

    public void setNroPago(int nroPago) {
        this.nroPago = nroPago;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String formato(String fecha){
        String[] parts = fecha.split("-");
        String part1 = parts[0];
        String part2 = parts[1];
        String part = parts[2];
        String[] partes = part.split("T");
        String part3= partes[0];
        String f= part3+"-"+part2+"-"+part1;
        return f;
    }
}
