package com.techlab.articulo.model;

public class ArticuloAlimenticio extends Articulo {

    private int diasParaVecimiento;

    public ArticuloAlimenticio(int codigo, String nombre, double precio, Categoria categoria, int diasParaVecimiento){
        super(codigo, nombre, precio, categoria);
        this.diasParaVecimiento = diasParaVecimiento;
    }

    public int getDiasParaVencimiento(){
        return this.diasParaVecimiento;
    }

    public void setDiasParaVencimiento(int diasParaVecimiento){
        this.diasParaVecimiento = diasParaVecimiento;
    }

    @Override
    public String getTipoArticulo(){
        return "Alimenticio";
    }

    @Override
    public String getDetalleEspecifico(){
        return "Vence en: " + this.getDiasParaVencimiento() + " dias.";
    }

    @Override 
    public String toString(){
        return super.toString() + " [subtipo Alimenticio]";
    }
}
