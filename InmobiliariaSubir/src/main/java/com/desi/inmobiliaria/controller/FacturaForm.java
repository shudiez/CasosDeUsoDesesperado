package com.desi.inmobiliaria.controller;

import com.desi.inmobiliaria.entity.Contrato;
import com.desi.inmobiliaria.entity.EstadoFactura;
import com.desi.inmobiliaria.entity.Factura;
import com.desi.inmobiliaria.entity.MedioPago;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class FacturaForm {
   private Long id;
   private @NotNull(
   message = "Debe seleccionar un contrato asociado."
) Long contratoId;
   private @NotBlank(
   message = "El concepto facturado es obligatorio."
) String conceptoFacturado;
   private @NotNull(
   message = "El importe base es obligatorio."
) @Positive(
   message = "El importe debe ser un número positivo mayor a cero."
) BigDecimal importe;
   private EstadoFactura estado;
   @DateTimeFormat(
      pattern = "yyyy-MM-dd"
   )
   private @NotNull(
   message = "La fecha de emisión es obligatoria."
) LocalDate fechaEmision;
   @DateTimeFormat(
      pattern = "yyyy-MM-dd"
   )
   private @NotNull(
   message = "La fecha de vencimiento es obligatoria."
) LocalDate fechaVencimiento;
   @DateTimeFormat(
      pattern = "yyyy-MM-dd"
   )
   private LocalDate fechaPago;
   private MedioPago medio;
   private @PositiveOrZero(
   message = "El importe pagado no puede ser negativo."
) BigDecimal importePagado;
   private @PositiveOrZero(
   message = "El interés no puede ser negativo."
) BigDecimal interes;

   public FacturaForm() {
   }

   public FacturaForm(Factura f) {
      this.id = f.getId();
      if (f.getContrato() != null) {
         this.contratoId = f.getContrato().getId();
      }

      this.conceptoFacturado = f.getConceptoFacturado();
      this.fechaEmision = f.getFechaEmision();
      this.fechaVencimiento = f.getFechaVencimiento();
      this.importe = f.getImporte();
      this.estado = f.getEstado();
      this.fechaPago = f.getFechaPago();
      this.medio = f.getMedio();
      this.importePagado = f.getImportePagado();
      this.interes = f.getInteres();
   }

   public static FacturaForm fromPojo(Factura f) {
      FacturaForm form = new FacturaForm();
      form.setId(f.getId());
      if (f.getContrato() != null) {
         form.setContratoId(f.getContrato().getId());
      }

      form.setConceptoFacturado(f.getConceptoFacturado());
      form.setFechaEmision(f.getFechaEmision());
      form.setFechaVencimiento(f.getFechaVencimiento());
      form.setImporte(f.getImporte());
      form.setEstado(f.getEstado());
      form.setFechaPago(f.getFechaPago());
      form.setMedio(f.getMedio());
      form.setImportePagado(f.getImportePagado());
      form.setInteres(f.getInteres());
      return form;
   }

   public Factura toPojo() {
      Factura f = new Factura();
      f.setId(this.id);
      f.setConceptoFacturado(this.conceptoFacturado);
      f.setFechaEmision(this.fechaEmision);
      f.setFechaVencimiento(this.fechaVencimiento);
      f.setImporte(this.importe);
      f.setEstado(this.estado);
      f.setFechaPago(this.fechaPago);
      f.setMedio(this.medio);
      f.setImportePagado(this.importePagado);
      f.setInteres(this.interes);
      if (this.contratoId != null) {
         Contrato c = new Contrato();
         c.setId(this.contratoId);
         f.setContrato(c);
      }

      return f;
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

   public String getConceptoFacturado() {
      return this.conceptoFacturado;
   }

   public void setConceptoFacturado(String conceptoFacturado) {
      this.conceptoFacturado = conceptoFacturado;
   }

   public Long getContratoId() {
      return this.contratoId;
   }

   public void setContratoId(Long contratoId) {
      this.contratoId = contratoId;
   }
}
