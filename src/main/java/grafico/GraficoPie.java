/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafico;

import Enum.StatusContrato;
import Servico.ContratadoServico;
import Servico.ContratoServico;
import Servico.UsuarioServico;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Contratado;
import modelo.Contrato;
import modelo.ContratoVo;
import modelo.Setor;
import modelo.Usuario;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.animation.Animation;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;
import org.primefaces.model.charts.pie.PieChartOptions;
import org.primefaces.model.charts.polar.PolarAreaChartDataSet;
import org.primefaces.model.charts.polar.PolarAreaChartModel;
import org.primefaces.model.charts.radar.RadarChartDataSet;
import org.primefaces.model.charts.radar.RadarChartModel;
import util.Utils;

/**
 *
 * @author eduardo
 */
@ManagedBean
@ViewScoped
public class GraficoPie implements Serializable {

    @EJB
    ContratoServico contratoServico;
    @EJB
    UsuarioServico userServico;
    private Setor setor;
    private StatusContrato status;
    private Contratado contratado;
    private List<ContratoVo> contratoVos;
    private LineChartModel cartesianLinerModel;
    private PieChartModel pieModel;
    private PolarAreaChartModel polarAreaModel;
    private RadarChartModel radarModel;
    private BarChartModel barModel;
    private Usuario user;

    @PostConstruct
    public void init() {
        this.setor = new Setor();
        this.contratoVos = new ArrayList<>();
        this.contratado = new Contratado();
        this.user = userServico.getCurrentUser();
        createPieModel();
        createCartesianLinerModel();
        createPolarAreaModel();
        createRadarModel();
        createBarModel();
    }

    public void createCartesianLinerModel() {
        cartesianLinerModel = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();
        List<Object> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        for (ContratoVo c : buscarValorContrato()) {
            values.add(c.getValor());
            labels.add(c.getNome());
        }
        dataSet.setData(values);
        dataSet.setLabel("Left Dataset");
        dataSet.setYaxisID("left-y-axis");
        dataSet.setFill(true);
        dataSet.setTension(0.5);
        data.addChartDataSet(dataSet);
        data.setLabels(labels);
        cartesianLinerModel.setData(data);

        //Options
        LineChartOptions options = new LineChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setId("left-y-axis");
        linearAxes.setPosition("left");
        CartesianLinearAxes linearAxes2 = new CartesianLinearAxes();
        linearAxes2.setId("right-y-axis");
        linearAxes2.setPosition("right");

        cScales.addYAxesData(linearAxes);
        cScales.addYAxesData(linearAxes2);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Cartesian Linear Chart");
        options.setTitle(title);

        cartesianLinerModel.setOptions(options);
    }

    public void createBarModel() {
        barModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Tipo de Contrato");

        List<Number> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        bgColor.add("rgba(75, 192, 192, 0.2)");
        bgColor.add("rgba(54, 162, 235, 0.2)");
        bgColor.add("rgba(153, 102, 255, 0.2)");
        bgColor.add("rgba(201, 203, 207, 0.2)");
        barDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 205, 86)");
        borderColor.add("rgb(75, 192, 192)");
        borderColor.add("rgb(54, 162, 235)");
        borderColor.add("rgb(153, 102, 255)");
        borderColor.add("rgb(201, 203, 207)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);
        int contador = 0;
        for (ContratoVo tipo : TiposContrato()) {
            contador = contador + 1;
            if (contador < 8) {
                labels.add(tipo.getNome());
                values.add(tipo.getQuantidade());
            }
        }
        barDataSet.setData(values);
        data.setLabels(labels);
        barModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Tipos de contratos mais utilizados");
        options.setTitle(title);

        Animation animation = new Animation();
        animation.setDuration(0);
        options.setAnimation(animation);

        barModel.setOptions(options);
    }

    public List<ContratoVo> TiposContrato() {
        return contratoServico.buscarTipoContrato(user);
    }

    public void buscarContratoSetor() {
        this.contratoVos = contratoServico.buscarContratoSetor(this.setor, this.contratado, user);
        createCartesianLinerModel();
        createPolarAreaModel();
    }

    public List<ContratoVo> buscarValorContrato() {
        List<ContratoVo> contratoValor = new ArrayList<>();
        List<Contrato> contratos = contratoServico.buscarValorContrato(this.setor, user);
        BigDecimal valor = new BigDecimal(0);
        for (ContratoVo cv : contratoVos) {
            for (Contrato c : contratos) {
                if (c.getStatus().getStatus().equals(cv.getNome())) {
                    valor = valor.add(c.getValor());
                }
            }
            contratoValor.add(new ContratoVo(cv.getNome(), valor));
        }
        return contratoValor;
    }

    private void createPieModel() {
        pieModel = new PieChartModel();
        ChartData data = new ChartData();

        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        List<String> bgColors = new ArrayList<>();

        for (ContratoVo contra : contratoServico.buscarContratos(user)) {
            values.add(contra.getQuantidade());
            labels.add(contra.getNome());
            bgColors.add(cores(contra.getNome()));
        }
        dataSet.setData(values);
        PieChartOptions options = new PieChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Resumo dos Contratos");
        options.setTitle(title);
        dataSet.setBackgroundColor(bgColors);

        data.setLabels(labels);
        pieModel.setOptions(options);
        pieModel.setData(data);
    }

    public String cores(String stts) {
        String cor = "teste";
        if (Utils.isNotEmpty(stts)) {
            switch (stts) {
                case "Aprovado":
                    cor = "rgb(0, 92, 254)";
                    break;
                case "Cancelado":
                    cor = "rgb(255, 1, 0)";
                    break;
                case "Esperando início":
                    cor = "rgb(119, 54, 255)";
                    break;
                case "Expirado":
                    cor = "rgb(254, 216, 0)";
                    break;
                case "Finalizado":
                    cor = "rgb(126, 14, 172)";
                    break;
                case "Iniciado":
                    cor = "rgb(206, 217, 219)";
                    break;
                case "Pago":
                    cor = "rgb(41, 185, 246)";
                    break;
                case "Proximo a expirar":
                    cor = "rgb(249, 203, 0)";
                    break;
                case "Vigência":
                    cor = "rgb(67, 135, 8)";
                    break;
                default:
                    break;
            }
        }
        return cor;

    }

    private void createPolarAreaModel() {
        polarAreaModel = new PolarAreaChartModel();
        ChartData data = new ChartData();

        PolarAreaChartDataSet dataSet = new PolarAreaChartDataSet();
        List<Number> values = new ArrayList<>();
        List<String> bgColors = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        if (Utils.isNotEmpty(contratoVos)) {
            for (ContratoVo contratoSetor : this.contratoVos) {
                values.add(contratoSetor.getQuantidade());
                labels.add(contratoSetor.getNome());
            }
        }

        dataSet.setData(values);

        bgColors.add("rgba(255, 99, 132,0.4)");
        bgColors.add("rgba(75, 192, 192,0.4)");
        bgColors.add("rgba(255, 205, 86,0.4)");
        bgColors.add("rgba(201, 203, 207,0.4)");
        bgColors.add("rgba(54, 162, 235,0.4)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);

        data.setLabels(labels);

        polarAreaModel.setData(data);
    }

    public void createRadarModel() {
        radarModel = new RadarChartModel();
        ChartData data = new ChartData();

        RadarChartDataSet radarDataSet = new RadarChartDataSet();
        radarDataSet.setLabel("My First Dataset");
        radarDataSet.setFill(true);
        radarDataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");
        radarDataSet.setBorderColor("rgb(255, 99, 132)");
        radarDataSet.setPointBackgroundColor("rgb(255, 99, 132)");
        radarDataSet.setPointBorderColor("#fff");
        radarDataSet.setPointHoverBackgroundColor("#fff");
        radarDataSet.setPointHoverBorderColor("rgb(255, 99, 132)");
        List<Number> dataVal = new ArrayList<>();
        dataVal.add(65);
        dataVal.add(59);
        dataVal.add(90);
        dataVal.add(81);
        dataVal.add(56);
        dataVal.add(55);
        dataVal.add(40);
        radarDataSet.setData(dataVal);

        RadarChartDataSet radarDataSet2 = new RadarChartDataSet();
        radarDataSet2.setLabel("My Second Dataset");
        radarDataSet2.setFill(true);
        radarDataSet2.setBackgroundColor("rgba(54, 162, 235, 0.2)");
        radarDataSet2.setBorderColor("rgb(54, 162, 235)");
        radarDataSet2.setPointBackgroundColor("rgb(54, 162, 235)");
        radarDataSet2.setPointBorderColor("#fff");
        radarDataSet2.setPointHoverBackgroundColor("#fff");
        radarDataSet2.setPointHoverBorderColor("rgb(54, 162, 235)");
        List<Number> dataVal2 = new ArrayList<>();
        dataVal2.add(28);
        dataVal2.add(48);
        dataVal2.add(40);
        dataVal2.add(19);
        dataVal2.add(96);
        dataVal2.add(27);
        dataVal2.add(100);
        radarDataSet2.setData(dataVal2);

        data.addChartDataSet(radarDataSet);
        data.addChartDataSet(radarDataSet2);

        List<String> labels = new ArrayList<>();
        labels.add("Eating");
        labels.add("Drinking");
        labels.add("Sleeping");
        labels.add("Designing");
        labels.add("Coding");
        labels.add("Cycling");
        labels.add("Running");
        data.setLabels(labels);

        radarModel.setData(data);
    }

    public RadarChartModel getRadarModel() {
        return radarModel;
    }

    public Contratado getContratado() {
        return contratado;
    }

    public void setContratado(Contratado contratado) {
        this.contratado = contratado;
    }

    public void setRadarModel(RadarChartModel radarModel) {
        this.radarModel = radarModel;
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

    public PolarAreaChartModel getPolarAreaModel() {
        return polarAreaModel;
    }

    public void setPolarAreaModel(PolarAreaChartModel polarAreaModel) {
        this.polarAreaModel = polarAreaModel;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public StatusContrato getStatus() {
        return status;
    }

    public void setStatus(StatusContrato status) {
        this.status = status;
    }

    public LineChartModel getCartesianLinerModel() {
        return cartesianLinerModel;
    }

    public void setCartesianLinerModel(LineChartModel cartesianLinerModel) {
        this.cartesianLinerModel = cartesianLinerModel;
    }

}
