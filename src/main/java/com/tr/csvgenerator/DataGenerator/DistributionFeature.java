package com.tr.csvgenerator.DataGenerator;

import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 */
public class DistributionFeature implements Feature // extends AbstractFeature
{
    private static RandomDataGenerator r = new RandomDataGenerator();
    private String Model = "";
    private List<Double> Params;

    public DistributionFeature() {
    }


    public String getModel() {
        return Model;
    }

    public List<Double> getParams() {
        return Params;
    }

    public void setModel(String model) {
        Model = model;
    }

    public void setParams(List<Double> params) {
        Params = params;
    }

    public DistributionFeature(String model, List<Double> Params, Long seed) {
        this.Model = model;
        this.Params = Params;
        if(seed != (Long)null)
            r.reSeed(seed);
    }

    @Override
    public Object NextValue() throws Exception {
        Double res = 0.0;
        switch (Model.toLowerCase()) {
            case "normal":
                res = r.nextGaussian(Params.get(0), Params.get(1));
                break;
            case "beta":
                res = r.nextBeta(Params.get(0), Params.get(1));
                break;
            case "cauchy":
                res = r.nextCauchy(Params.get(0), Params.get(1));
                break;
            case "chisquared":
                res = r.nextChiSquare(Params.get(0));
                break;
            case "exponential":
                res = r.nextExponential(Params.get(0));
                break;
            case "fisher-senedecor":
                res = r.nextF(Params.get(0), Params.get(1));
                break;
            case "gamma":
                res = r.nextGamma(Params.get(0), Params.get(1));
                break;
//            case "levy":
//                return null;
//            case "log-normal":
//                return null;
//            case "pareto":
//                return null;
            case "student-t":
                res = r.nextT(Params.get(0));
                break;
            case "weibull":
                res = r.nextWeibull(Params.get(0), Params.get(1));
                break;
            case "uniform":
                res = r.nextUniform(Params.get(0), Params.get(1));
                break;
            //case "pascal":
            //   res = r.nextPascal(Params.get(0).intValue(),Params.get(1));
//            case "SecureHexString":
//                return r.nextSecureHexString(Params.get(0).intValue());
//            case "HexString":
//                return r.nextHexString(Params.get(0).intValue());
            default:
                throw new Exception("ERROR: The Model: " + Model + " Doesn't Exist");
        }

            return res;
    }

    @Override
    public String toString() {

        final StringBuffer sb = new StringBuffer("DistributionFeature{");
        sb.append(", Model='").append(Model).append('\'');
        sb.append(", Params=").append(Params);
        sb.append(']');
        return sb.toString();
    }
}
