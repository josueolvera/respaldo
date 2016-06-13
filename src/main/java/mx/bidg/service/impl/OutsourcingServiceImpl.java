package mx.bidg.service.impl;

import mx.bidg.dao.OutsourcingDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.Outsourcing;
import mx.bidg.service.OutsourcingService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by gerardo8 on 16/05/16.
 */
@Transactional
@Service
public class OutsourcingServiceImpl implements OutsourcingService {

    @Autowired
    private OutsourcingDao outsourcingDao;

    @Override
    public List<Outsourcing> findAll() {
        return outsourcingDao.findAll();
    }

    @Override
    public Outsourcing findById(Integer id) {
        return outsourcingDao.findById(id);
    }

    @Override
    public List<Outsourcing> saveFromExcel(MultipartFile file,String calculateDate) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        for (int i=9;i<=sheet.getLastRowNum();i++) {
            Row currentRow = sheet.getRow(i);
            Cell idW = currentRow.getCell(0);
            Cell sueldo = currentRow.getCell(2);
            Cell septimoDia = currentRow.getCell(3);
            Cell horasExtras = currentRow.getCell(4);
            Cell destajos = currentRow.getCell(5);
            Cell comisiones = currentRow.getCell(6);
            Cell indemnizacionEspecial = currentRow.getCell(7);
            Cell premiosEficiencia = currentRow.getCell(8);
            Cell vacacionesATiempo = currentRow.getCell(9);
            Cell primaDeVacacionesATiempo = currentRow.getCell(10);
            Cell vacacionesReportadas = currentRow.getCell(11);
            Cell primaDeVacacionesReportada = currentRow.getCell(12);
            Cell aguinaldo = currentRow.getCell(13);
            Cell primaDeAntiguedad = currentRow.getCell(14);
            Cell otrasPercepciones = currentRow.getCell(15);
            Cell totalPercepciones = currentRow.getCell(16);
            Cell retInvYVida = currentRow.getCell(17);
            Cell retCesantia = currentRow.getCell(18);
            Cell retEnfYMatObrero = currentRow.getCell(19);
            Cell seguroDeViviendaInfonavit = currentRow.getCell(20);
            Cell prestamoInfonavitVsm = currentRow.getCell(21);
            Cell subsAlEmpleoAcreditado = currentRow.getCell(22);
            Cell subsidioAlEmpleoSp = currentRow.getCell(23);
            Cell isrAntesDeSubsAlEmpleo = currentRow.getCell(24);
            Cell isrArt142 = currentRow.getCell(25);
            Cell isrSp = currentRow.getCell(26);
            Cell imss = currentRow.getCell(27);
            Cell prestamoInfonavit = currentRow.getCell(28);
            Cell ajusteAlNeto = currentRow.getCell(29);
            Cell isrFiniquito = currentRow.getCell(30);
            Cell otrasDeducciones = currentRow.getCell(31);
            Cell totalDeducciones = currentRow.getCell(32);
            Cell neto = currentRow.getCell(33);
            Cell invalidezYVida = currentRow.getCell(34);
            Cell cesantiaYVejez = currentRow.getCell(35);
            Cell enfYMatPatron = currentRow.getCell(36);
            Cell fondoRetiroSar = currentRow.getCell(37);
            Cell impuestoEstatal = currentRow.getCell(38);
            Cell riesgoDeTrabajo = currentRow.getCell(39);
            Cell imssEmpresa = currentRow.getCell(40);
            Cell infonavitEmpresa = currentRow.getCell(41);
            Cell guarderiaImss = currentRow.getCell(42);
            Cell otrasObligaciones = currentRow.getCell(43);
            Cell totalObligaciones = currentRow.getCell(44);
            Cell percepcionesSubsidioSpTotalObligaciones = currentRow.getCell(45);
            Cell comision = currentRow.getCell(46);
            Cell iva = currentRow.getCell(47);
            Cell totalIva = currentRow.getCell(48);

            Outsourcing outsourcing = new Outsourcing();

            if (idW != null) {
                outsourcing.setIdW(idW.getStringCellValue());
            }
            if (sueldo != null) {

                if (sueldo.getCellType() == Cell.CELL_TYPE_STRING) {
                    break;
                }
                
                BigDecimal bdSueldo = new BigDecimal(sueldo.getNumericCellValue());
                outsourcing.setSueldo(bdSueldo);
            }
            if (septimoDia != null) {
                BigDecimal bdSeptimoDia = new BigDecimal(septimoDia.getNumericCellValue());
                outsourcing.setSeptimoDia(bdSeptimoDia);
            }
            if (horasExtras != null) {
                BigDecimal bdHorasExtras = new BigDecimal(horasExtras.getNumericCellValue());
                outsourcing.setHorasExtras(bdHorasExtras);
            }
            if (destajos != null) {
                BigDecimal bdDestajos = new BigDecimal(destajos.getNumericCellValue());
                outsourcing.setDestajos(bdDestajos);
            }
            if (comisiones != null) {
                BigDecimal bdComisiones = new BigDecimal(comisiones.getNumericCellValue());
                outsourcing.setComisiones(bdComisiones);
            }
            if (indemnizacionEspecial != null) {
                BigDecimal bdIndemnizacionEspecial = new BigDecimal(indemnizacionEspecial.getNumericCellValue());
                outsourcing.setIndemnizacionEspecial(bdIndemnizacionEspecial);
            }
            if (premiosEficiencia != null) {
                BigDecimal bdPremiosEficiencia = new BigDecimal(premiosEficiencia.getNumericCellValue());
                outsourcing.setPremiosEficiencia(bdPremiosEficiencia);
            }
            if (vacacionesATiempo != null) {
                BigDecimal bdVacacionesATiempo = new BigDecimal(vacacionesATiempo.getNumericCellValue());
                outsourcing.setVacacionesATiempo(bdVacacionesATiempo);
            }
            if (primaDeVacacionesATiempo != null) {
                BigDecimal bdPrimaDeVacacionesATiempo = new BigDecimal(primaDeVacacionesATiempo.getNumericCellValue());
                outsourcing.setPrimaDeVacacionesATiempo(bdPrimaDeVacacionesATiempo);
            }
            if (vacacionesReportadas != null) {
                BigDecimal bdVacacionesReportadas = new BigDecimal(vacacionesReportadas.getNumericCellValue());
                outsourcing.setVacacionesReportadas(bdVacacionesReportadas);
            }
            if (primaDeVacacionesReportada != null) {
                BigDecimal bdPrimaDeVacacionesReportada = new BigDecimal(primaDeVacacionesReportada.getNumericCellValue());
                outsourcing.setPrimaDeVacacionesReportada(bdPrimaDeVacacionesReportada);
            }
            if (aguinaldo != null) {
                BigDecimal bdAguinaldo = new BigDecimal(aguinaldo.getNumericCellValue());
                outsourcing.setAguinaldo(bdAguinaldo);
            }
            if (primaDeAntiguedad != null) {
                BigDecimal bdPrimaDeAntiguedad = new BigDecimal(primaDeAntiguedad.getNumericCellValue());
                outsourcing.setPrimaDeAntiguedad(bdPrimaDeAntiguedad);
            }
            if (otrasPercepciones != null) {
                BigDecimal bdOtrasPercepciones = new BigDecimal(otrasPercepciones.getNumericCellValue());
                outsourcing.setOtrasPercepciones(bdOtrasPercepciones);
            }
            if (totalPercepciones != null) {
                BigDecimal bdTotalPercepciones = new BigDecimal(totalPercepciones.getNumericCellValue());
                outsourcing.setTotalPercepciones(bdTotalPercepciones);
            }
            if (retInvYVida != null) {
                BigDecimal bdRetInvYVida = new BigDecimal(retInvYVida.getNumericCellValue());
                outsourcing.setRetInvYVida(bdRetInvYVida);
            }
            if (retCesantia != null) {
                BigDecimal bdRetCesantia = new BigDecimal(retCesantia.getNumericCellValue());
                outsourcing.setRetCesantia(bdRetCesantia);
            }
            if (retEnfYMatObrero != null) {
                BigDecimal bdRetEnfYMatObrero = new BigDecimal(retEnfYMatObrero.getNumericCellValue());
                outsourcing.setRetEnfYMatObrero(bdRetEnfYMatObrero);
            }
            if (seguroDeViviendaInfonavit != null) {
                BigDecimal bdSeguroDeViviendaInfonavit = new BigDecimal(seguroDeViviendaInfonavit.getNumericCellValue());
                outsourcing.setSeguroDeViviendaInfonavit(bdSeguroDeViviendaInfonavit);
            }
            if (prestamoInfonavitVsm != null) {
                BigDecimal bdPrestamoInfonavitVsm = new BigDecimal(prestamoInfonavitVsm.getNumericCellValue());
                outsourcing.setPrestamoInfonavitVsm(bdPrestamoInfonavitVsm);
            }
            if (subsAlEmpleoAcreditado != null) {
                BigDecimal bdSubsAlEmpleoAcreditado = new BigDecimal(subsAlEmpleoAcreditado.getNumericCellValue());
                outsourcing.setSubsAlEmpleoAcreditado(bdSubsAlEmpleoAcreditado);
            }
            if (subsidioAlEmpleoSp != null) {
                BigDecimal bdSubsidioAlEmpleoSp = new BigDecimal(subsidioAlEmpleoSp.getNumericCellValue());
                outsourcing.setSubsidioAlEmpleoSp(bdSubsidioAlEmpleoSp);
            }
            if (isrAntesDeSubsAlEmpleo != null) {
                BigDecimal bdIsrAntesDeSubsAlEmpleo = new BigDecimal(isrAntesDeSubsAlEmpleo.getNumericCellValue());
                outsourcing.setIsrAntesDeSubsAlEmpleo(bdIsrAntesDeSubsAlEmpleo);
            }
            if (isrArt142 != null) {
                BigDecimal bdIsrArt142 = new BigDecimal(isrArt142.getNumericCellValue());
                outsourcing.setIsrArt142(bdIsrArt142);
            }
            if (isrSp != null) {
                BigDecimal bdIsrSp = new BigDecimal(isrSp.getNumericCellValue());
                outsourcing.setIsrSp(bdIsrSp);
            }
            if (imss != null) {
                BigDecimal bdImss = new BigDecimal(imss.getNumericCellValue());
                outsourcing.setImss(bdImss);
            }
            if (prestamoInfonavit != null) {
                BigDecimal bdPrestamoInfonavit = new BigDecimal(prestamoInfonavit.getNumericCellValue());
                outsourcing.setPrestamoInfonavit(bdPrestamoInfonavit);
            }
            if (ajusteAlNeto != null) {
                BigDecimal bdAjusteAlNeto = new BigDecimal(ajusteAlNeto.getNumericCellValue());
                outsourcing.setAjusteAlNeto(bdAjusteAlNeto);
            }
            if (isrFiniquito != null) {
                BigDecimal bdIsrFiniquito = new BigDecimal(isrFiniquito.getNumericCellValue());
                outsourcing.setIsrFiniquito(bdIsrFiniquito);
            }
            if (otrasDeducciones != null) {
                BigDecimal bdOtrasDeducciones = new BigDecimal(otrasDeducciones.getNumericCellValue());
                outsourcing.setOtrasDeducciones(bdOtrasDeducciones);
            }
            if (totalDeducciones != null) {
                BigDecimal bdTotalDeducciones = new BigDecimal(totalDeducciones.getNumericCellValue());
                outsourcing.setTotalDeducciones(bdTotalDeducciones);
            }
            if (neto != null) {
                BigDecimal bdNeto = new BigDecimal(neto.getNumericCellValue());
                outsourcing.setNeto(bdNeto);
            }
            if (invalidezYVida != null) {
                BigDecimal bdInvalidezYVida = new BigDecimal(invalidezYVida.getNumericCellValue());
                outsourcing.setInvalidezYVida(bdInvalidezYVida);
            }
            if (cesantiaYVejez != null) {
                BigDecimal bdCesantiaYVejez = new BigDecimal(cesantiaYVejez.getNumericCellValue());
                outsourcing.setCesantiaYVejez(bdCesantiaYVejez);
            }
            if (enfYMatPatron != null) {
                BigDecimal bdEnfYMatPatron = new BigDecimal(enfYMatPatron.getNumericCellValue());
                outsourcing.setEnfYMatPatron(bdEnfYMatPatron);
            }
            if (fondoRetiroSar != null) {
                BigDecimal bdFondoRetiroSar = new BigDecimal(fondoRetiroSar.getNumericCellValue());
                outsourcing.setFondoRetiroSar(bdFondoRetiroSar);
            }
            if (impuestoEstatal != null) {
                BigDecimal bdImpuestoEstatal = new BigDecimal(impuestoEstatal.getNumericCellValue());
                outsourcing.setImpuestoEstatal(bdImpuestoEstatal);
            }
            if (riesgoDeTrabajo != null) {
                BigDecimal bdRiesgoDeTrabajo = new BigDecimal(riesgoDeTrabajo.getNumericCellValue());
                outsourcing.setRiesgoDeTrabajo(bdRiesgoDeTrabajo);
            }
            if (imssEmpresa != null) {
                BigDecimal bdImssEmpresa = new BigDecimal(imssEmpresa.getNumericCellValue());
                outsourcing.setImssEmpresa(bdImssEmpresa);
            }
            if (infonavitEmpresa != null) {
                BigDecimal bdInfonavitEmpresa = new BigDecimal(infonavitEmpresa.getNumericCellValue());
                outsourcing.setInfonavitEmpresa(bdInfonavitEmpresa);
            }
            if (guarderiaImss != null) {
                BigDecimal bdGuarderiaImss = new BigDecimal(guarderiaImss.getNumericCellValue());
                outsourcing.setGuarderiaImss(bdGuarderiaImss);
            }
            if (otrasObligaciones != null) {
                BigDecimal bdOtrasObligaciones = new BigDecimal(otrasObligaciones.getNumericCellValue());
                outsourcing.setOtrasObligaciones(bdOtrasObligaciones);
            }
            if (totalObligaciones != null) {
                BigDecimal bdTotalObligaciones = new BigDecimal(totalObligaciones.getNumericCellValue());
                outsourcing.setTotalObligaciones(bdTotalObligaciones);
            }
            if (percepcionesSubsidioSpTotalObligaciones != null) {
                BigDecimal bdPercepcionesSubsidioSpTotalObligaciones = new BigDecimal(percepcionesSubsidioSpTotalObligaciones.getNumericCellValue());
                outsourcing.setPercepcionesSubsidioSpTotalObligaciones(bdPercepcionesSubsidioSpTotalObligaciones);
            }
            if (comision != null) {
                BigDecimal bdComision = new BigDecimal(comision.getNumericCellValue());
                outsourcing.setComision(bdComision);
            }
            if (iva != null) {
                BigDecimal bdIva = new BigDecimal(iva.getNumericCellValue());
                outsourcing.setIva(bdIva);
            }
            if (totalIva != null) {
                BigDecimal bdTotalIva = new BigDecimal(totalIva.getNumericCellValue());
                outsourcing.setTotalIva(bdTotalIva);
            }

            outsourcing.setCreationDate(LocalDateTime.parse(calculateDate+" 00:00",formatter));

            if (outsourcing.getIdW() != null) {
                outsourcingDao.save(outsourcing);
            }
        }

        return outsourcingDao.findAll();
    }

    @Override
    public List<Outsourcing> updateFromExcel(MultipartFile file, String calculateDate) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        for (int i=9;i<=sheet.getLastRowNum();i++) {
            Row currentRow = sheet.getRow(i);
            Cell idW = currentRow.getCell(0);
            Cell sueldo = currentRow.getCell(2);
            Cell septimoDia = currentRow.getCell(3);
            Cell horasExtras = currentRow.getCell(4);
            Cell destajos = currentRow.getCell(5);
            Cell comisiones = currentRow.getCell(6);
            Cell indemnizacionEspecial = currentRow.getCell(7);
            Cell premiosEficiencia = currentRow.getCell(8);
            Cell vacacionesATiempo = currentRow.getCell(9);
            Cell primaDeVacacionesATiempo = currentRow.getCell(10);
            Cell vacacionesReportadas = currentRow.getCell(11);
            Cell primaDeVacacionesReportada = currentRow.getCell(12);
            Cell aguinaldo = currentRow.getCell(13);
            Cell primaDeAntiguedad = currentRow.getCell(14);
            Cell otrasPercepciones = currentRow.getCell(15);
            Cell totalPercepciones = currentRow.getCell(16);
            Cell retInvYVida = currentRow.getCell(17);
            Cell retCesantia = currentRow.getCell(18);
            Cell retEnfYMatObrero = currentRow.getCell(19);
            Cell seguroDeViviendaInfonavit = currentRow.getCell(20);
            Cell prestamoInfonavitVsm = currentRow.getCell(21);
            Cell subsAlEmpleoAcreditado = currentRow.getCell(22);
            Cell subsidioAlEmpleoSp = currentRow.getCell(23);
            Cell isrAntesDeSubsAlEmpleo = currentRow.getCell(24);
            Cell isrArt142 = currentRow.getCell(25);
            Cell isrSp = currentRow.getCell(26);
            Cell imss = currentRow.getCell(27);
            Cell prestamoInfonavit = currentRow.getCell(28);
            Cell ajusteAlNeto = currentRow.getCell(29);
            Cell isrFiniquito = currentRow.getCell(30);
            Cell otrasDeducciones = currentRow.getCell(31);
            Cell totalDeducciones = currentRow.getCell(32);
            Cell neto = currentRow.getCell(33);
            Cell invalidezYVida = currentRow.getCell(34);
            Cell cesantiaYVejez = currentRow.getCell(35);
            Cell enfYMatPatron = currentRow.getCell(36);
            Cell fondoRetiroSar = currentRow.getCell(37);
            Cell impuestoEstatal = currentRow.getCell(38);
            Cell riesgoDeTrabajo = currentRow.getCell(39);
            Cell imssEmpresa = currentRow.getCell(40);
            Cell infonavitEmpresa = currentRow.getCell(41);
            Cell guarderiaImss = currentRow.getCell(42);
            Cell otrasObligaciones = currentRow.getCell(43);
            Cell totalObligaciones = currentRow.getCell(44);
            Cell percepcionesSubsidioSpTotalObligaciones = currentRow.getCell(45);
            Cell comision = currentRow.getCell(46);
            Cell iva = currentRow.getCell(47);
            Cell totalIva = currentRow.getCell(48);


            if (idW != null) {

                Outsourcing outsourcing = outsourcingDao.finfByidW(
                        idW.getStringCellValue(),
                        LocalDateTime.parse(calculateDate+" 00:00",formatter)
                );

                if (idW.getCellType() == Cell.CELL_TYPE_STRING) {
                    break;
                }

                if (outsourcing != null) {
                    if (sueldo != null) {

                        if (sueldo.getCellType() == Cell.CELL_TYPE_STRING) {
                            break;
                        }

                        BigDecimal bdSueldo = new BigDecimal(sueldo.getNumericCellValue());
                        outsourcing.setSueldo(bdSueldo);
                    }
                    if (septimoDia != null) {
                        BigDecimal bdSeptimoDia = new BigDecimal(septimoDia.getNumericCellValue());
                        outsourcing.setSeptimoDia(bdSeptimoDia);
                    }
                    if (horasExtras != null) {
                        BigDecimal bdHorasExtras = new BigDecimal(horasExtras.getNumericCellValue());
                        outsourcing.setHorasExtras(bdHorasExtras);
                    }
                    if (destajos != null) {
                        BigDecimal bdDestajos = new BigDecimal(destajos.getNumericCellValue());
                        outsourcing.setDestajos(bdDestajos);
                    }
                    if (comisiones != null) {
                        BigDecimal bdComisiones = new BigDecimal(comisiones.getNumericCellValue());
                        outsourcing.setComisiones(bdComisiones);
                    }
                    if (indemnizacionEspecial != null) {
                        BigDecimal bdIndemnizacionEspecial = new BigDecimal(indemnizacionEspecial.getNumericCellValue());
                        outsourcing.setIndemnizacionEspecial(bdIndemnizacionEspecial);
                    }
                    if (premiosEficiencia != null) {
                        BigDecimal bdPremiosEficiencia = new BigDecimal(premiosEficiencia.getNumericCellValue());
                        outsourcing.setPremiosEficiencia(bdPremiosEficiencia);
                    }
                    if (vacacionesATiempo != null) {
                        BigDecimal bdVacacionesATiempo = new BigDecimal(vacacionesATiempo.getNumericCellValue());
                        outsourcing.setVacacionesATiempo(bdVacacionesATiempo);
                    }
                    if (primaDeVacacionesATiempo != null) {
                        BigDecimal bdPrimaDeVacacionesATiempo = new BigDecimal(primaDeVacacionesATiempo.getNumericCellValue());
                        outsourcing.setPrimaDeVacacionesATiempo(bdPrimaDeVacacionesATiempo);
                    }
                    if (vacacionesReportadas != null) {
                        BigDecimal bdVacacionesReportadas = new BigDecimal(vacacionesReportadas.getNumericCellValue());
                        outsourcing.setVacacionesReportadas(bdVacacionesReportadas);
                    }
                    if (primaDeVacacionesReportada != null) {
                        BigDecimal bdPrimaDeVacacionesReportada = new BigDecimal(primaDeVacacionesReportada.getNumericCellValue());
                        outsourcing.setPrimaDeVacacionesReportada(bdPrimaDeVacacionesReportada);
                    }
                    if (aguinaldo != null) {
                        BigDecimal bdAguinaldo = new BigDecimal(aguinaldo.getNumericCellValue());
                        outsourcing.setAguinaldo(bdAguinaldo);
                    }
                    if (primaDeAntiguedad != null) {
                        BigDecimal bdPrimaDeAntiguedad = new BigDecimal(primaDeAntiguedad.getNumericCellValue());
                        outsourcing.setPrimaDeAntiguedad(bdPrimaDeAntiguedad);
                    }
                    if (otrasPercepciones != null) {
                        BigDecimal bdOtrasPercepciones = new BigDecimal(otrasPercepciones.getNumericCellValue());
                        outsourcing.setOtrasPercepciones(bdOtrasPercepciones);
                    }
                    if (totalPercepciones != null) {
                        BigDecimal bdTotalPercepciones = new BigDecimal(totalPercepciones.getNumericCellValue());
                        outsourcing.setTotalPercepciones(bdTotalPercepciones);
                    }
                    if (retInvYVida != null) {
                        BigDecimal bdRetInvYVida = new BigDecimal(retInvYVida.getNumericCellValue());
                        outsourcing.setRetInvYVida(bdRetInvYVida);
                    }
                    if (retCesantia != null) {
                        BigDecimal bdRetCesantia = new BigDecimal(retCesantia.getNumericCellValue());
                        outsourcing.setRetCesantia(bdRetCesantia);
                    }
                    if (retEnfYMatObrero != null) {
                        BigDecimal bdRetEnfYMatObrero = new BigDecimal(retEnfYMatObrero.getNumericCellValue());
                        outsourcing.setRetEnfYMatObrero(bdRetEnfYMatObrero);
                    }
                    if (seguroDeViviendaInfonavit != null) {
                        BigDecimal bdSeguroDeViviendaInfonavit = new BigDecimal(seguroDeViviendaInfonavit.getNumericCellValue());
                        outsourcing.setSeguroDeViviendaInfonavit(bdSeguroDeViviendaInfonavit);
                    }
                    if (prestamoInfonavitVsm != null) {
                        BigDecimal bdPrestamoInfonavitVsm = new BigDecimal(prestamoInfonavitVsm.getNumericCellValue());
                        outsourcing.setPrestamoInfonavitVsm(bdPrestamoInfonavitVsm);
                    }
                    if (subsAlEmpleoAcreditado != null) {
                        BigDecimal bdSubsAlEmpleoAcreditado = new BigDecimal(subsAlEmpleoAcreditado.getNumericCellValue());
                        outsourcing.setSubsAlEmpleoAcreditado(bdSubsAlEmpleoAcreditado);
                    }
                    if (subsidioAlEmpleoSp != null) {
                        BigDecimal bdSubsidioAlEmpleoSp = new BigDecimal(subsidioAlEmpleoSp.getNumericCellValue());
                        outsourcing.setSubsidioAlEmpleoSp(bdSubsidioAlEmpleoSp);
                    }
                    if (isrAntesDeSubsAlEmpleo != null) {
                        BigDecimal bdIsrAntesDeSubsAlEmpleo = new BigDecimal(isrAntesDeSubsAlEmpleo.getNumericCellValue());
                        outsourcing.setIsrAntesDeSubsAlEmpleo(bdIsrAntesDeSubsAlEmpleo);
                    }
                    if (isrArt142 != null) {
                        BigDecimal bdIsrArt142 = new BigDecimal(isrArt142.getNumericCellValue());
                        outsourcing.setIsrArt142(bdIsrArt142);
                    }
                    if (isrSp != null) {
                        BigDecimal bdIsrSp = new BigDecimal(isrSp.getNumericCellValue());
                        outsourcing.setIsrSp(bdIsrSp);
                    }
                    if (imss != null) {
                        BigDecimal bdImss = new BigDecimal(imss.getNumericCellValue());
                        outsourcing.setImss(bdImss);
                    }
                    if (prestamoInfonavit != null) {
                        BigDecimal bdPrestamoInfonavit = new BigDecimal(prestamoInfonavit.getNumericCellValue());
                        outsourcing.setPrestamoInfonavit(bdPrestamoInfonavit);
                    }
                    if (ajusteAlNeto != null) {
                        BigDecimal bdAjusteAlNeto = new BigDecimal(ajusteAlNeto.getNumericCellValue());
                        outsourcing.setAjusteAlNeto(bdAjusteAlNeto);
                    }
                    if (isrFiniquito != null) {
                        BigDecimal bdIsrFiniquito = new BigDecimal(isrFiniquito.getNumericCellValue());
                        outsourcing.setIsrFiniquito(bdIsrFiniquito);
                    }
                    if (otrasDeducciones != null) {
                        BigDecimal bdOtrasDeducciones = new BigDecimal(otrasDeducciones.getNumericCellValue());
                        outsourcing.setOtrasDeducciones(bdOtrasDeducciones);
                    }
                    if (totalDeducciones != null) {
                        BigDecimal bdTotalDeducciones = new BigDecimal(totalDeducciones.getNumericCellValue());
                        outsourcing.setTotalDeducciones(bdTotalDeducciones);
                    }
                    if (neto != null) {
                        BigDecimal bdNeto = new BigDecimal(neto.getNumericCellValue());
                        outsourcing.setNeto(bdNeto);
                    }
                    if (invalidezYVida != null) {
                        BigDecimal bdInvalidezYVida = new BigDecimal(invalidezYVida.getNumericCellValue());
                        outsourcing.setInvalidezYVida(bdInvalidezYVida);
                    }
                    if (cesantiaYVejez != null) {
                        BigDecimal bdCesantiaYVejez = new BigDecimal(cesantiaYVejez.getNumericCellValue());
                        outsourcing.setCesantiaYVejez(bdCesantiaYVejez);
                    }
                    if (enfYMatPatron != null) {
                        BigDecimal bdEnfYMatPatron = new BigDecimal(enfYMatPatron.getNumericCellValue());
                        outsourcing.setEnfYMatPatron(bdEnfYMatPatron);
                    }
                    if (fondoRetiroSar != null) {
                        BigDecimal bdFondoRetiroSar = new BigDecimal(fondoRetiroSar.getNumericCellValue());
                        outsourcing.setFondoRetiroSar(bdFondoRetiroSar);
                    }
                    if (impuestoEstatal != null) {
                        BigDecimal bdImpuestoEstatal = new BigDecimal(impuestoEstatal.getNumericCellValue());
                        outsourcing.setImpuestoEstatal(bdImpuestoEstatal);
                    }
                    if (riesgoDeTrabajo != null) {
                        BigDecimal bdRiesgoDeTrabajo = new BigDecimal(riesgoDeTrabajo.getNumericCellValue());
                        outsourcing.setRiesgoDeTrabajo(bdRiesgoDeTrabajo);
                    }
                    if (imssEmpresa != null) {
                        BigDecimal bdImssEmpresa = new BigDecimal(imssEmpresa.getNumericCellValue());
                        outsourcing.setImssEmpresa(bdImssEmpresa);
                    }
                    if (infonavitEmpresa != null) {
                        BigDecimal bdInfonavitEmpresa = new BigDecimal(infonavitEmpresa.getNumericCellValue());
                        outsourcing.setInfonavitEmpresa(bdInfonavitEmpresa);
                    }
                    if (guarderiaImss != null) {
                        BigDecimal bdGuarderiaImss = new BigDecimal(guarderiaImss.getNumericCellValue());
                        outsourcing.setGuarderiaImss(bdGuarderiaImss);
                    }
                    if (otrasObligaciones != null) {
                        BigDecimal bdOtrasObligaciones = new BigDecimal(otrasObligaciones.getNumericCellValue());
                        outsourcing.setOtrasObligaciones(bdOtrasObligaciones);
                    }
                    if (totalObligaciones != null) {
                        BigDecimal bdTotalObligaciones = new BigDecimal(totalObligaciones.getNumericCellValue());
                        outsourcing.setTotalObligaciones(bdTotalObligaciones);
                    }
                    if (percepcionesSubsidioSpTotalObligaciones != null) {
                        BigDecimal bdPercepcionesSubsidioSpTotalObligaciones = new BigDecimal(percepcionesSubsidioSpTotalObligaciones.getNumericCellValue());
                        outsourcing.setPercepcionesSubsidioSpTotalObligaciones(bdPercepcionesSubsidioSpTotalObligaciones);
                    }
                    if (comision != null) {
                        BigDecimal bdComision = new BigDecimal(comision.getNumericCellValue());
                        outsourcing.setComision(bdComision);
                    }
                    if (iva != null) {
                        BigDecimal bdIva = new BigDecimal(iva.getNumericCellValue());
                        outsourcing.setIva(bdIva);
                    }
                    if (totalIva != null) {
                        BigDecimal bdTotalIva = new BigDecimal(totalIva.getNumericCellValue());
                        outsourcing.setTotalIva(bdTotalIva);
                    }

                    outsourcingDao.update(outsourcing);
                }
            }
        }

        return outsourcingDao.findAll();
    }

    @Override
    public Boolean existsOutsourcingRecord(MultipartFile file,String calculateDate) throws IOException, InvalidFormatException {

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        Row headerRow = sheet.getRow(7);
        String[] headersToSkip = {
                "Código", "Empleado","Sueldo", "Séptimo día",
                "Horas extras","Destajos","Comisiones", "Indemnización Especial",
                "Premios eficiencia","Vacaciones a tiempo","Prima de vacaciones a tiempo",
                "Vacaciones reportadas $", "Prima de vacaciones reportada $",
                "Aguinaldo","Prima de antiguedad", "*Otras* *Percepciones*",
                "*TOTAL* *PERCEPCIONES*", "Ret. Inv. Y Vida", "Ret. Cesantia",
                "Ret. Enf. y Mat. obrero","Seguro de vivienda Infonavit",
                "Préstamo Infonavit (vsm)", "Subs al Empleo acreditado","Subsidio al Empleo (sp)",
                "I.S.R. antes de Subs al Empleo","I.S.R. Art142", "I.S.R. (sp)",
                "I.M.S.S.","Préstamo Infonavit","Ajuste al neto","I.S.R. finiquito",
                "*Otras* *Deducciones*","*TOTAL* *DEDUCCIONES*","*NETO*","Invalidez y Vida",
                "Cesantia y Vejez","Enf. y Mat. Patron","2% Fondo retiro SAR (8)",
                "2% Impuesto estatal","Riesgo de trabajo (9)","I.M.S.S. empresa",
                "Infonavit empresa","Guarderia I.M.S.S. (7)","*Otras* *Obligaciones*",
                "*TOTAL* *OBLIGACIONES*","PERCEPCIONES + SUBSIDIO(sp) + TOTAL OBLIGACIONES",
                "COMISION","IVA","TOTAL+IVA"
        };

        for (int i = 0 ; i < 49 ;i++) {
            if (!headerRow.getCell(i).getStringCellValue().equals(headersToSkip[i])) {
                throw new ValidationException("Tipo de formato no compatible.",
                        "Los datos de este archivo no son los correctos o no cumplen con los datos de venta.");
            }
        }

        boolean existsOutsourcing = false;

        for (int i=9;i<=sheet.getLastRowNum();i++) {
            Row currentRow = sheet.getRow(i);
            Cell idW = currentRow.getCell(0);

            Outsourcing savedOutsourcing;

            if (idW != null) {

                if (idW.getCellType() == Cell.CELL_TYPE_STRING) {
                    break;
                }

                savedOutsourcing = outsourcingDao.finfByidW(
                        idW.getStringCellValue(),
                        LocalDateTime.parse(calculateDate+" 00:00",formatter)
                );

                if (savedOutsourcing != null) {
                    existsOutsourcing = true;
                }
            }
        }

        return existsOutsourcing;
    }
}
