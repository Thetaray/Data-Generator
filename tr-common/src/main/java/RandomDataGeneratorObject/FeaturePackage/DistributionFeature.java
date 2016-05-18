package RandomDataGeneratorObject.FeaturePackage;

import RandomDataGeneratorObject.Data;
import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 */
public class DistributionFeature implements Feature {

    private String Model = "";
    private List<Double> params;

    public DistributionFeature() {
    }


    public DistributionFeature(String model, List<Double> Params, Long seed) {
        this.Model = model;
        this.params = Params;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public List<Double> getParams() {
        return params;
    }

    public void setParams(List<Double> params) {
        this.params = params;
    }

    @Override
    public Object NextValue() throws Exception {
        RandomDataGenerator r = Data.getRandom();
        Double res = 0.0;
        switch (Model.toLowerCase()) {
            case "normal": {
                if (params.size() > 2)
                    res = (r.nextGaussian(params.get(0), params.get(1))) * (params.get(3) - params.get(2)) + params.get(2);
                else
                    res = r.nextGaussian(params.get(0), params.get(1));
                break;
            }
            case "beta": {
                if (params.size() > 2)
                    res = ((r.nextBeta(params.get(0), params.get(1))) * (params.get(3) - params.get(2))) + params.get(2);
                else
                    res = r.nextBeta(params.get(0), params.get(1));
                break;
            }
            case "cauchy":
                res = r.nextCauchy(params.get(0), params.get(1));
                break;
            case "chisquared":
                res = r.nextChiSquare(params.get(0));
                break;
            case "exponential":
                res = r.nextExponential(params.get(0));
                break;
            case "fisher-senedecor":
                res = r.nextF(params.get(0), params.get(1));
                break;
            case "gamma":
                res = r.nextGamma(params.get(0), params.get(1));
                break;
//            case "levy":
//                return null;
//            case "log-normal":
//                return null;
//            case "pareto":
//                return null;
            case "student-t":
                res = r.nextT(params.get(0));
                break;
            case "weibull":
                res = r.nextWeibull(params.get(0), params.get(1));
                break;
            case "uniform": {
                if (params.get(0).equals(params.get(1)))
                    res = params.get(0);
                else
                    res = r.nextUniform(params.get(0), params.get(1));
                break;
            }
//            case "cor":
//            {
//                // Create and seed a RandomGenerator (could use any of the generators in the random package here)
//                RandomGenerator rg = new JDKRandomGenerator();
//                rg.setSeed(17399225432l);  // Fixed seed means same results every time
//
//                // Create a GassianRandomGenerator using rg as its source of randomness
//                GaussianRandomGenerator rawGenerator = new GaussianRandomGenerator(rg);
//
//                // Create a CorrelatedRandomVectorGenerator using rawGenerator for the components
//                double mean = 0.5;
//                RealMatrixFormat covariance = new RealMatrixFormat();
//                CorrelatedRandomVectorGenerator generator =
//                        new CorrelatedRandomVectorGenerator(mean, covariance, 1.0e-12 * covariance.getNorm(), rawGenerator);
//
//                // Use the generator to generate correlated vectors
//                double[] randomVector = generator.nextVector();
//            }
            //case "pascal":
            //   res = r.nextPascal(params.get(0).intValue(),params.get(1));
            case "SecureHexString":
                return r.nextSecureHexString(params.get(0).intValue());
            case "HexString":
                return r.nextHexString(params.get(0).intValue());
            default:
                throw new Exception("ERROR: The Model: " + Model + " Doesn't Exist");
        }

        return res;
    }

    @Override
    public String toString() {

        final StringBuffer sb = new StringBuffer("DistributionFeature{");
        sb.append(", Model='").append(Model).append('\'');
        sb.append(", params=").append(params);
        sb.append(']');
        return sb.toString();
    }
}
