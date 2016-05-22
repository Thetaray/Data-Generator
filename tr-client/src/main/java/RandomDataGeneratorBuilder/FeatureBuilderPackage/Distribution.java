package RandomDataGeneratorBuilder.FeatureBuilderPackage;

public enum Distribution {

    NORMAL("normal"),
    BETA("beta"),
    CAUCHY("cauchy"),
    CHISQUARED("chisquared"),
    EXPONENTIAL("exponential"),
    FISHER_SENEDECOR("fisher-senedecor"),
    GAMMA("gamma"),
    STUDENT_T("student-t"),
    WEIBULL("weibull"),
    UNIFORM("uniform"),
    SECURE_HEX_STRING("SecureHexString"),
    HEX_STRING("HexString");

    private String distribution;

    Distribution(String distribution) {
        this.distribution = distribution;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public String getDistribution() {
        return distribution;
    }

}
