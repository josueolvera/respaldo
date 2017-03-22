package mx.bidg.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lyncode.jtwig.content.model.compilable.IfControl;
import mx.bidg.pojos.PdfAternaPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Calendar;

/**
 * Created by Desarrollador on 23/02/2017.
 */
public class BuildPdfAterna {

    @Autowired
    Environment env;

    public void createPdf(String filename, PdfAternaPojo aternaPojo)
            throws Exception {

        ConverterNumberToLetter numberToLetter = new ConverterNumberToLetter();

        // step 1
        Document document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        PdfPTable table = new PdfPTable(11);

        Paragraph paragraph2 = new Paragraph();
        Paragraph paragraph3 = new Paragraph();
        Paragraph paragraph4 = new Paragraph();
        Paragraph paragraph5 = new Paragraph();
        Paragraph paragraph6 = new Paragraph();
        Paragraph paragraph7 = new Paragraph();
        Paragraph paragraph8 = new Paragraph();
        Paragraph paragraph9 = new Paragraph();
        Paragraph producto = new Paragraph();
        Paragraph emision = new Paragraph();
        Paragraph mes = new Paragraph();
        Paragraph colocado = new Paragraph();
        Paragraph subTotal = new Paragraph();
        Paragraph iva = new Paragraph();
        Paragraph total = new Paragraph();
        Paragraph noFactura = new Paragraph();
        Paragraph subTotalUDI = new Paragraph();
        Paragraph ivaUDI = new Paragraph();
        Paragraph totalUDI = new Paragraph();
        Paragraph totales = new Paragraph();
        Paragraph totalColcados = new Paragraph();
        Paragraph sumSubTotal = new Paragraph();
        Paragraph sumIva = new Paragraph();
        Paragraph sumTotal = new Paragraph();
        Paragraph sumSubTotalUDI = new Paragraph();
        Paragraph sumIvaUDI = new Paragraph();
        Paragraph sumTotalUDI = new Paragraph();
        Paragraph totalSeguros = new Paragraph();
        Paragraph totalPrima = new Paragraph();
        Paragraph totalUDICobrar = new Paragraph();
        Paragraph totalSumSeguros = new Paragraph();
        Paragraph totalSumPrima = new Paragraph();
        Paragraph totalSumUDICobrar = new Paragraph();
        Paragraph periodo = new Paragraph();
        Paragraph totalSumSegurosL = new Paragraph();
        Paragraph totalSumPrimaL = new Paragraph();
        Paragraph totalSumUDICobrarL = new Paragraph();

        Calendar fecha = Calendar.getInstance();

        Calendar monthBefore = Calendar.getInstance();
        monthBefore.add(Calendar.MONTH,-1);

        Font font = new Font();
        Font font1 = new Font();
        Font font2 = new Font();
        Font font3 = new Font();
        Font font4 = new Font();
        Font font5 = new Font();

        font.setStyle(Font.BOLD);
        font1.setStyle(Font.BOLD);
        font1.setSize(8f);
        font2.setStyle(Font.BOLD);
        font2.setSize(6f);
        font3.setSize(6f);
        font4.setStyle(Font.BOLD);
        font4.setSize(6f);
        font4.setColor(BaseColor.WHITE);
        font5.setStyle(Font.BOLD);
        font5.setSize(6f);
        font5.setColor(BaseColor.BLUE);

        paragraph2.setAlignment(Element.ALIGN_CENTER);
        paragraph2.setFont(font);
        paragraph2.add("Carta de Control de Primas y Comisiones");
        paragraph3.setAlignment(Element.ALIGN_CENTER);
        paragraph3.setFont(font1);
        paragraph3.add(getMonthName(monthBefore.get(Calendar.MONTH)+1)+" "+monthBefore.get(Calendar.YEAR));
        paragraph4.setAlignment(Element.ALIGN_CENTER);
        paragraph4.setFont(font1);
        paragraph4.add("ATERNA - CANAL DE PAGO");
        paragraph5.setSpacingAfter(15);
        paragraph5.setSpacingBefore(15);
        paragraph5.setAlignment(Element.ALIGN_RIGHT);
        paragraph5.setFont(font2);
        String diaCC = null;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        if (dia < 10){
            diaCC = "0"+dia;
        }else{
            diaCC = ""+dia;
        }
        paragraph5.add("Fecha: "+ diaCC+"-"+getMonthName(fecha.get(Calendar.MONTH)+1)+"-"+fecha.get(Calendar.YEAR));
        paragraph6.setSpacingAfter(25);
        paragraph6.setAlignment(Element.ALIGN_LEFT);
        paragraph6.setFont(font3);
        paragraph6.add("El resultado de la emisión de seguros correspondientes al período de "+getMonthName(monthBefore.get(Calendar.MONTH)+1)+" "+monthBefore.get(Calendar.YEAR)+" se expresa de la siguiente manera:");
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(paragraph4);
        Image image = Image.getInstance("/run/media/Centos/Storage/SCOPA/imagenes/logoAterna.png");
        image.scalePercent(20f);
        image.setAbsolutePosition(500f, 780f);
        document.add(image);
        document.add(paragraph5);
        document.add(paragraph6);

        paragraph7.setFont(font3);
        paragraph7.setAlignment(Element.ALIGN_LEFT);
        paragraph7.add("Normal");
        PdfPCell cell = new PdfPCell(paragraph7);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);

        paragraph8.setFont(font4);
        paragraph8.setAlignment(Element.ALIGN_CENTER);
        paragraph8.add("PRIMA A FACTURAR");
        PdfPCell cell1 = new PdfPCell(paragraph8);
        cell1.setBackgroundColor(BaseColor.BLUE);
        cell1.setBorder(Rectangle.NO_BORDER);
        cell1.setColspan(4);

        colocado.setFont(font4);
        colocado.setAlignment(Element.ALIGN_CENTER);
        colocado.add("Total Colocados");
        PdfPCell cell2 = new PdfPCell(colocado);
        cell2.setBackgroundColor(BaseColor.BLUE);
        cell2.setBorder(Rectangle.NO_BORDER);
        cell2.setRowspan(2);

        paragraph9.setFont(font4);
        paragraph9.setAlignment(Element.ALIGN_CENTER);
        paragraph9.add("UDI POR COBRAR");
        PdfPCell cell3 = new PdfPCell(paragraph9);
        cell3.setBackgroundColor(BaseColor.BLUE);
        cell3.setBorder(Rectangle.NO_BORDER);
        cell3.setColspan(3);

        table.addCell(cell);
        table.addCell(cell2);
        table.addCell(cell1);
        table.addCell(cell3);

        producto.setAlignment(Element.ALIGN_CENTER);
        producto.setFont(font4);
        producto.add("Producto");
        PdfPCell cProducto = new PdfPCell(producto);
        cProducto.setBackgroundColor(BaseColor.BLUE);
        cProducto.setBorder(Rectangle.NO_BORDER);

        emision.setAlignment(Element.ALIGN_CENTER);
        emision.setFont(font4);
        emision.add("Emisión");
        PdfPCell cEmision = new PdfPCell(emision);
        cEmision.setBackgroundColor(BaseColor.BLUE);
        cEmision.setBorder(Rectangle.NO_BORDER);

        mes.setAlignment(Element.ALIGN_CENTER);
        mes.setFont(font4);
        mes.add("Mes");
        PdfPCell cMes = new PdfPCell(mes);
        cMes.setBackgroundColor(BaseColor.BLUE);
        cMes.setBorder(Rectangle.NO_BORDER);

        subTotal.setAlignment(Element.ALIGN_CENTER);
        subTotal.setFont(font4);
        subTotal.add("Subtotal");
        PdfPCell cSubtotal = new PdfPCell(subTotal);
        cSubtotal.setBackgroundColor(BaseColor.BLUE);
        cSubtotal.setBorder(Rectangle.NO_BORDER);

        iva.setAlignment(Element.ALIGN_CENTER);
        iva.setFont(font4);
        iva.add("IVA");
        PdfPCell cIva = new PdfPCell(iva);
        cIva.setBackgroundColor(BaseColor.BLUE);
        cIva.setBorder(Rectangle.NO_BORDER);

        total.setAlignment(Element.ALIGN_CENTER);
        total.setFont(font4);
        total.add("Total");
        PdfPCell cTotal = new PdfPCell(total);
        cTotal.setBackgroundColor(BaseColor.BLUE);
        cTotal.setBorder(Rectangle.NO_BORDER);

        noFactura.setAlignment(Element.ALIGN_CENTER);
        noFactura.setFont(font4);
        noFactura.add("No. de Factura");
        PdfPCell cNoFactura = new PdfPCell(noFactura);
        cNoFactura.setBackgroundColor(BaseColor.BLUE);
        cNoFactura.setBorder(Rectangle.NO_BORDER);

        subTotalUDI.setAlignment(Element.ALIGN_CENTER);
        subTotalUDI.setFont(font4);
        subTotalUDI.add("Subtotal");
        PdfPCell cSubtotalUDI = new PdfPCell(subTotalUDI);
        cSubtotalUDI.setBackgroundColor(BaseColor.BLUE);
        cSubtotalUDI.setBorder(Rectangle.NO_BORDER);

        ivaUDI.setAlignment(Element.ALIGN_CENTER);
        ivaUDI.setFont(font4);
        ivaUDI.add("IVA");
        PdfPCell cIvaUDI = new PdfPCell(ivaUDI);
        cIvaUDI.setBackgroundColor(BaseColor.BLUE);
        cIvaUDI.setBorder(Rectangle.NO_BORDER);

        totalUDI.setAlignment(Element.ALIGN_CENTER);
        totalUDI.setFont(font4);
        totalUDI.add("Total");
        PdfPCell cTotalUDI = new PdfPCell(totalUDI);
        cTotalUDI.setBackgroundColor(BaseColor.BLUE);
        cTotalUDI.setBorder(Rectangle.NO_BORDER);

        table.addCell(cProducto);
        table.addCell(cEmision);
        table.addCell(cMes);
        table.addCell(cSubtotal);
        table.addCell(cIva);
        table.addCell(cTotal);
        table.addCell(cNoFactura);
        table.addCell(cSubtotalUDI);
        table.addCell(cIvaUDI);
        table.addCell(cTotalUDI);


        PdfPCell cProductoValue = new PdfPCell(new Paragraph("Seguro", font3));
        cProductoValue.setBorder(Rectangle.NO_BORDER);

        PdfPCell cEmisionValue = new PdfPCell(new Paragraph("Normal", font3));
        cEmisionValue.setBorder(Rectangle.NO_BORDER);

        PdfPCell cMesValue = new PdfPCell(new Paragraph(getMonthName(monthBefore.get(Calendar.MONTH)+1), font3));
        cMesValue.setBorder(Rectangle.NO_BORDER);

        PdfPCell cTotalColocadosValue = new PdfPCell(new Paragraph(Integer.toString(aternaPojo.getNumSecures()),font3));
        cTotalColocadosValue.setBorder(Rectangle.NO_BORDER);

        PdfPCell cSubtotalValue = new PdfPCell(new Paragraph(Double.toString(aternaPojo.getSubtotalM().doubleValue()),font3));
        cSubtotalValue.setBorder(Rectangle.NO_BORDER);

        PdfPCell cIvaValue = new PdfPCell(new Paragraph(Double.toString(aternaPojo.getIvaM().doubleValue()), font3));
        cIvaValue.setBorder(Rectangle.NO_BORDER);

        PdfPCell cTotalValue = new PdfPCell(new Paragraph(Double.toString(aternaPojo.getTotalM().doubleValue()),font3));
        cTotalValue.setBorder(Rectangle.NO_BORDER);

        PdfPCell cNoFacturaValue = new PdfPCell(new Paragraph(" ", font3));
        cNoFacturaValue.setBorder(Rectangle.NO_BORDER);

        BigDecimal percentage = new BigDecimal(0.20);
        BigDecimal totalUDIAmount = aternaPojo.getTotalM().multiply(percentage, new MathContext(2));

        BigDecimal ivaS = new BigDecimal(1.16);
        BigDecimal subtotalUDIAmount = totalUDIAmount.divide(ivaS,2, RoundingMode.HALF_UP);

        BigDecimal ivaUDIAmount = totalUDIAmount.subtract(subtotalUDIAmount,new MathContext(2));

        PdfPCell cSubtotalUDIValue = new PdfPCell(new Paragraph(Double.toString(subtotalUDIAmount.doubleValue()), font3));
        cSubtotalUDIValue.setBorder(Rectangle.NO_BORDER);

        PdfPCell cIvaUDIValue = new PdfPCell(new Paragraph(Double.toString(ivaUDIAmount.doubleValue()), font3));
        cIvaUDIValue.setBorder(Rectangle.NO_BORDER);

        PdfPCell cTotalUDIValue = new PdfPCell(new Paragraph(Double.toString(totalUDIAmount.doubleValue()), font3));
        cTotalUDIValue.setBorder(Rectangle.NO_BORDER);


        table.addCell(cProductoValue);
        table.addCell(cEmisionValue);
        table.addCell(cMesValue);
        table.addCell(cTotalColocadosValue);
        table.addCell(cSubtotalValue);
        table.addCell(cIvaValue);
        table.addCell(cTotalValue);
        table.addCell(cNoFacturaValue);
        table.addCell(cSubtotalUDIValue);
        table.addCell(cIvaUDIValue);
        table.addCell(cTotalUDIValue);


        totales.setFont(font4);
        totales.setAlignment(Element.ALIGN_CENTER);
        totales.add("TOTALES");
        PdfPCell cTotales = new PdfPCell(totales);
        cTotales.setBackgroundColor(BaseColor.BLUE);
        cTotales.setBorder(Rectangle.NO_BORDER);
        cTotales.setColspan(3);

        totalColcados.setFont(font4);
        totalColcados.setAlignment(Element.ALIGN_CENTER);
        totalColcados.add(Integer.toString(aternaPojo.getNumSecures()));
        PdfPCell cTotalColocados = new PdfPCell(totalColcados);
        cTotalColocados.setBackgroundColor(BaseColor.BLUE);
        cTotalColocados.setBorder(Rectangle.NO_BORDER);

        sumSubTotal.setFont(font4);
        sumSubTotal.setAlignment(Element.ALIGN_CENTER);
        sumSubTotal.add("$ "+Double.toString(aternaPojo.getSubtotalM().doubleValue()));
        PdfPCell cSumSubtotal = new PdfPCell(sumSubTotal);
        cSumSubtotal.setBackgroundColor(BaseColor.BLUE);
        cSumSubtotal.setBorder(Rectangle.NO_BORDER);

        sumIva.setFont(font4);
        sumIva.setAlignment(Element.ALIGN_CENTER);
        sumIva.add("$ "+Double.toString(aternaPojo.getIvaM().doubleValue()));
        PdfPCell cSumIva = new PdfPCell(sumIva);
        cSumIva.setBackgroundColor(BaseColor.BLUE);
        cSumIva.setBorder(Rectangle.NO_BORDER);

        sumTotal.setFont(font4);
        sumTotal.setAlignment(Element.ALIGN_CENTER);
        sumTotal.add("$ "+Double.toString(aternaPojo.getTotalM().doubleValue()));
        PdfPCell cSumTotal = new PdfPCell(sumTotal);
        cSumTotal.setBackgroundColor(BaseColor.BLUE);
        cSumTotal.setBorder(Rectangle.NO_BORDER);

        PdfPCell folios = new PdfPCell();
        folios.setBorder(Rectangle.NO_BORDER);

        sumSubTotalUDI.setFont(font4);
        sumSubTotalUDI.setAlignment(Element.ALIGN_CENTER);
        sumSubTotalUDI.add("$ "+Double.toString(subtotalUDIAmount.doubleValue()));
        PdfPCell cSumSubtotalUDI = new PdfPCell(sumSubTotalUDI);
        cSumSubtotalUDI.setBackgroundColor(BaseColor.BLUE);
        cSumSubtotalUDI.setBorder(Rectangle.NO_BORDER);

        sumIvaUDI.setFont(font4);
        sumIvaUDI.setAlignment(Element.ALIGN_CENTER);
        sumIvaUDI.add("$ "+Double.toString(ivaUDIAmount.doubleValue()));
        PdfPCell cSumIvaUDI = new PdfPCell(sumIvaUDI);
        cSumIvaUDI.setBackgroundColor(BaseColor.BLUE);
        cSumIvaUDI.setBorder(Rectangle.NO_BORDER);

        sumTotalUDI.setFont(font4);
        sumTotalUDI.setAlignment(Element.ALIGN_CENTER);
        sumTotalUDI.add("$ "+Double.toString(totalUDIAmount.doubleValue()));
        PdfPCell cSumTotalUDI = new PdfPCell(sumTotalUDI);
        cSumTotalUDI.setBackgroundColor(BaseColor.BLUE);
        cSumTotalUDI.setBorder(Rectangle.NO_BORDER);

        table.addCell(cTotales);
        table.addCell(cTotalColocados);
        table.addCell(cSumSubtotal);
        table.addCell(cSumIva);
        table.addCell(cSumTotal);
        table.addCell(folios);
        table.addCell(cSumSubtotalUDI);
        table.addCell(cSumIvaUDI);
        table.addCell(cSumTotalUDI);

        table.setSpacingAfter(40f);

        document.add(table);

        PdfPTable tableReport = new PdfPTable(3);

        totalSeguros.setAlignment(Element.ALIGN_RIGHT);
        totalSeguros.setFont(font3);
        totalSeguros.add("Total de seguros colocados");
        PdfPCell cTotalSeguros = new PdfPCell(totalSeguros);
        cTotalSeguros.setBorder(Rectangle.NO_BORDER);

        totalSumSeguros.setFont(font5);
        totalSumSeguros.setAlignment(Element.ALIGN_RIGHT);
        totalSumSeguros.add(Integer.toString(aternaPojo.getNumSecures()));
        PdfPCell cTotalSumSeguros = new PdfPCell(totalSumSeguros);
        cTotalSumSeguros.setBorder(Rectangle.NO_BORDER);
        cTotalSumSeguros.setPaddingRight(10f);

        totalSumSegurosL.setFont(font5);
        totalSumSegurosL.setAlignment(Element.ALIGN_LEFT);
        totalSumSegurosL.add(numberToLetter.ConvertirSoloNumeroSCantidad(Integer.toString(aternaPojo.getNumSecures())) + " ASEGURADOS");
        PdfPCell cTotalSumSegurosL = new PdfPCell(totalSumSegurosL);
        cTotalSumSegurosL.setBorder(Rectangle.NO_BORDER);
        cTotalSumSegurosL.setPaddingRight(10f);

        totalPrima.setAlignment(Element.ALIGN_RIGHT);
        totalPrima.setFont(font3);
        totalPrima.add("Prima a facturar");
        PdfPCell cTotalPrima = new PdfPCell(totalPrima);
        cTotalPrima.setBorder(Rectangle.NO_BORDER);

        totalSumPrima.setFont(font5);
        totalSumPrima.setAlignment(Element.ALIGN_RIGHT);
        totalSumPrima.add("$ "+Double.toString(aternaPojo.getTotalM().doubleValue()));
        PdfPCell cTotalSumPrima = new PdfPCell(totalSumPrima);
        cTotalSumPrima.setBorder(Rectangle.NO_BORDER);
        cTotalSumPrima.setPaddingRight(10f);

        totalSumPrimaL.setFont(font5);
        totalSumPrimaL.setAlignment(Element.ALIGN_RIGHT);
        totalSumPrimaL.add(numberToLetter.Convertir(Double.toString(aternaPojo.getTotalM().doubleValue()), true));
        PdfPCell cTotalSumPrimaL = new PdfPCell(totalSumPrimaL);
        cTotalSumPrimaL.setBorder(Rectangle.NO_BORDER);
        cTotalSumPrimaL.setPaddingRight(10f);

        totalUDICobrar.setAlignment(Element.ALIGN_RIGHT);
        totalUDICobrar.setFont(font3);
        totalUDICobrar.add("UDI por cobrar");
        PdfPCell cTotalUDICobrar = new PdfPCell(totalUDICobrar);
        cTotalUDICobrar.setBorder(Rectangle.NO_BORDER);

        totalSumUDICobrar.setFont(font5);
        totalSumUDICobrar.setAlignment(Element.ALIGN_RIGHT);
        totalSumUDICobrar.add("$ "+Double.toString(totalUDIAmount.doubleValue()));
        PdfPCell cTotalSumUDICobrar = new PdfPCell(totalSumUDICobrar);
        cTotalSumUDICobrar.setBorder(Rectangle.NO_BORDER);
        cTotalSumUDICobrar.setPaddingRight(10f);

        totalSumUDICobrarL.setFont(font5);
        totalSumUDICobrarL.setAlignment(Element.ALIGN_RIGHT);
        totalSumUDICobrarL.add(numberToLetter.Convertir(Double.toString(totalUDIAmount.doubleValue()), true));
        PdfPCell cTotalSumUDICobrarL = new PdfPCell(totalSumUDICobrarL);
        cTotalSumUDICobrarL.setBorder(Rectangle.NO_BORDER);
        cTotalSumUDICobrarL.setPaddingRight(10f);

        tableReport.addCell(cTotalSeguros);
        tableReport.addCell(cTotalSumSeguros);
        tableReport.addCell(cTotalSumSegurosL);
        tableReport.addCell(cTotalPrima);
        tableReport.addCell(cTotalSumPrima);
        tableReport.addCell(cTotalSumPrimaL);
        tableReport.addCell(cTotalUDICobrar);
        tableReport.addCell(cTotalSumUDICobrar);
        tableReport.addCell(cTotalSumUDICobrarL);

        tableReport.setSpacingAfter(20f);

        document.add(tableReport);

        Calendar date = Calendar.getInstance();

        date.add(date.get(Calendar.MONTH),-1);

        periodo.setAlignment(Element.ALIGN_LEFT);
        periodo.setFont(font2);
        periodo.add("Nota: Las cifras presentadas corresponden al período del "+date.getActualMinimum(Calendar.DAY_OF_MONTH)+" al "+date.getActualMaximum(Calendar.DAY_OF_MONTH)+" de "+getMonthName(date.get(Calendar.MONTH)+1)+" de "+date.get(Calendar.YEAR)+".");
        periodo.setSpacingAfter(40f);
        document.add(periodo);

        Image firmas = Image.getInstance("/run/media/Centos/Storage/SCOPA/imagenes/firmasAterna.png");
        firmas.scalePercent(35f);
        firmas.setAlignment(Element.ALIGN_CENTER);
        document.add(firmas);

        // step 5
        document.close();
    }

    private String getMonthName(int numberOfMonth){
        String nameOfMonth = null;

        switch (numberOfMonth){
            case 1:
                nameOfMonth = "Enero";
                break;
            case 2:
                nameOfMonth = "Febrero";
                break;
            case 3:
                nameOfMonth = "Marzo";
                break;
            case 4:
                nameOfMonth = "Abril";
                break;
            case 5:
                nameOfMonth = "Mayo";
                break;
            case 6:
                nameOfMonth = "Junio";
                break;
            case 7:
                nameOfMonth = "Julio";
                break;
            case 8:
                nameOfMonth = "Agosto";
                break;
            case 9:
                nameOfMonth = "Septiembre";
                break;
            case 10:
                nameOfMonth = "Octubre";
                break;
            case 11:
                nameOfMonth = "Noviembre";
                break;
            case 12:
                nameOfMonth = "Diciembre";
                break;
        }
        return nameOfMonth;
    }
}
