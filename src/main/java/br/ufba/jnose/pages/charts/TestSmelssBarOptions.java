package br.ufba.jnose.pages.charts;

import br.ufba.jnose.core.testsmelldetector.testsmell.Util;
import de.adesso.wickedcharts.highcharts.options.*;
import de.adesso.wickedcharts.highcharts.options.color.HexColor;
import de.adesso.wickedcharts.highcharts.options.series.SimpleSeries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSmelssBarOptions extends Options {

    private static final long serialVersionUID = 1L;

    public TestSmelssBarOptions(List<List<String>> todasLinhas) {

        Map<String,String> mapaColunaValorTotal = new HashMap<>();

        Map<String,String> mapaCelulasValorTotal = new HashMap<>();

        int linhaAtual = 0;
        for(List<String> linha : todasLinhas){
            if(linhaAtual == 0){
                System.out.println("##############################################################");
                System.out.println(linha);
                int colunaID = 0;
                for(String coluna:linha){
                    mapaColunaValorTotal.put(coluna,colunaID+"");
                    colunaID++;
                }
                System.out.println("##############################################################");
                linhaAtual++;
            }else{
                System.out.println(linha);
                int celulaID = 0;
                for(String celulaValor:linha){
                    if(linhaAtual > 1) {
                        String valorAnterior = mapaCelulasValorTotal.get(linhaAtual-1);
                        if(Util.isInt(valorAnterior) && Util.isInt(celulaValor)){
                            int valorAnteriorInt = Integer.parseInt(valorAnterior);
                            int valorAtualInt = Integer.parseInt(celulaValor);
                            valorAtualInt =+ valorAnteriorInt;
                            celulaValor = (valorAtualInt + "");
                        }
                        mapaCelulasValorTotal.put(celulaID + "", celulaValor);
                        celulaID++;
                    }else{
                        mapaCelulasValorTotal.put(celulaID + "", celulaValor);
                        celulaID++;
                    }
                }
            }
        }


        setChartOptions(new ChartOptions()
                .setType(SeriesType.BAR));

        setGlobal(new Global()
                .setUseUTC(Boolean.TRUE));

        setTitle(new Title("Quantity of TestSmells in the Project"));

        setSubtitle(new Title("Source: JNose"));

        setxAxis(new Axis()
                .setCategories("")
                .setTitle(new Title(null)));

        setyAxis(new Axis()
                .setTitle(
                        new Title("TestSmells (uni)")
                                .setAlign(HorizontalAlignment.HIGH))
                .setLabels(new Labels().setOverflow(Overflow.JUSTIFY)));

        setTooltip(new Tooltip()
                .setFormatter(new Function(
                        "return ''+this.series.name +': '+ this.y +' testsmells';")));

        setPlotOptions(new PlotOptionsChoice()
                .setBar(new PlotOptions()
                        .setDataLabels(new DataLabels()
                                .setEnabled(Boolean.TRUE))));

        setLegend(new Legend()
                .setLayout(LegendLayout.VERTICAL)
                .setAlign(HorizontalAlignment.RIGHT)
                .setVerticalAlign(VerticalAlignment.TOP)
                .setX(-100)
                .setY(100)
                .setFloating(Boolean.TRUE)
                .setBorderWidth(1)
                .setBackgroundColor(new HexColor("#ffffff"))
                .setShadow(Boolean.TRUE));

        setCredits(new CreditOptions()
                .setEnabled(Boolean.FALSE));

        mapaColunaValorTotal.forEach( (colunaName, colunaKey) ->  {
            String valorTotalString = mapaCelulasValorTotal.get(colunaKey);

            if(Util.isInt(valorTotalString)){
                int valorTotal = Integer.parseInt(valorTotalString);
                addSeries(new SimpleSeries()
                        .setName(colunaName)
                        .setData(valorTotal));
            }

        });

//        addSeries(new SimpleSeries()
//                .setName("AssertionRoulette")
//                .setData(107));
//
//        addSeries(new SimpleSeries()
//                .setName("ConditionalTestLogic")
//                .setData(133));
//
//        addSeries(new SimpleSeries()
//                .setName("ConstructorInitialization")
//                .setData(973));

    }

    public String getLabel() {
        return "Basic bar";
    }

}
