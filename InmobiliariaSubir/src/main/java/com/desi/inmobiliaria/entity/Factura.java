package com.desi.inmobiliaria.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Factura {
   @Id
   @GeneratedValue(
      strategy = GenerationType.IDENTITY
   )
   private Long id;
   private LocalDate fechaEmision;
   private LocalDate fechaVencimiento;
   private BigDecimal importe;
   @Enumerated(EnumType.STRING)
   private EstadoFactura estado;
   private boolean eliminado;
   private LocalDate fechaPago;
   @Enumerated(EnumType.STRING)
   private MedioPago medio;
   private BigDecimal importePagado;
   private BigDecimal interes;
   @ManyToOne
   private Contrato contrato;
   @OneToMany(
      mappedBy = "factura",
      cascade = {CascadeType.ALL},
      orphanRemoval = true
   )
   private List<HistorialEstadoFactura> historialEstados = new ArrayList();
   private @NotBlank String conceptoFacturado;

   public Factura() {
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public LocalDate getFechaEmision() {
      return this.fechaEmision;
   }

   public void setFechaEmision(LocalDate fechaEmision) {
      this.fechaEmision = fechaEmision;
   }

   public LocalDate getFechaVencimiento() {
      return this.fechaVencimiento;
   }

   public void setFechaVencimiento(LocalDate fechaVencimiento) {
      this.fechaVencimiento = fechaVencimiento;
   }

   public BigDecimal getImporte() {
      return this.importe;
   }

   public void setImporte(BigDecimal importe) {
      this.importe = importe;
   }

   public EstadoFactura getEstado() {
      return this.estado;
   }

   public void setEstado(EstadoFactura estado) {
      this.estado = estado;
   }

   public boolean isEliminado() {
      return this.eliminado;
   }

   public void setEliminado(boolean eliminado) {
      this.eliminado = eliminado;
   }

   public LocalDate getFechaPago() {
      return this.fechaPago;
   }

   public void setFechaPago(LocalDate fechaPago) {
      this.fechaPago = fechaPago;
   }

   public MedioPago getMedio() {
      return this.medio;
   }

   public void setMedio(MedioPago medio) {
      this.medio = medio;
   }

   public BigDecimal getImportePagado() {
      return this.importePagado;
   }

   public void setImportePagado(BigDecimal importePagado) {
      this.importePagado = importePagado;
   }

   public BigDecimal getInteres() {
      return this.interes;
   }

   public void setInteres(BigDecimal interes) {
      this.interes = interes;
   }

   public Contrato getContrato() {
      return this.contrato;
   }

   public void setContrato(Contrato contrato) {
      this.contrato = contrato;
   }

   public String getConceptoFacturado() {
      return this.conceptoFacturado;
   }

   public void setConceptoFacturado(String conceptoFacturado) {
      this.conceptoFacturado = conceptoFacturado;
   }

   public void agregarCambioEstado(EstadoFactura nuevoEstado) {
      HistorialEstadoFactura nuevoHistorial = new HistorialEstadoFactura(this, nuevoEstado);
      this.historialEstados.add(nuevoHistorial);
      this.estado = nuevoEstado;
   }
}
