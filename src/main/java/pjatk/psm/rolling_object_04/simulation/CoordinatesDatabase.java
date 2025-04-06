package pjatk.psm.rolling_object_04.simulation;

import org.jfree.data.xy.XYSeries;

public class CoordinatesDatabase {
    private final XYSeries inclineLineCoordinates = new XYSeries("Incline Surface");
    private final XYSeries ballProjectionCoordinates = new XYSeries("Ball Projection");
    private final XYSeries ballCenterCoordinates = new XYSeries("Ball Center");
    private final XYSeries ballCoordinates = new XYSeries("Ball Ring");
    private final XYSeries epCoordinates = new XYSeries("Ep");
    private final XYSeries ekCoordinates = new XYSeries("Ek");
    private final XYSeries etCoordinates = new XYSeries("Et");

    public void reset(){
        inclineLineCoordinates.clear();
        ballProjectionCoordinates.clear();
        ballCenterCoordinates.clear();
        ballCoordinates.clear();
        epCoordinates.clear();
        ekCoordinates.clear();
        etCoordinates.clear();
    }

    public void addInclineLineCoordinates(double x, double y){
        inclineLineCoordinates.add(x, y);
    }
    public void addBallProjectionCoordinates(double x, double y){
        ballProjectionCoordinates.add(x, y);
    }
    public void addCenterCoordinates(double x, double y){
        ballCenterCoordinates.add(x, y);
    }
    public void addBallCoordinates(double x, double y){
        ballCoordinates.add(x, y);
    }
    public void addEpCoordinates(double x, double y){
        epCoordinates.add(x, y);
    }
    public void addEkCoordinates(double x, double y){
        ekCoordinates.add(x, y);
    }
    public void addEtCoordinates(double x, double y){
        etCoordinates.add(x, y);
    }

    public XYSeries getInclineLineCoordinates() {
        return inclineLineCoordinates;
    }
    public XYSeries getBallProjectionCoordinates() {
        return ballProjectionCoordinates;
    }
    public XYSeries getBallCentreCoordinates() {
        return ballCenterCoordinates;
    }
    public XYSeries getBallCoordinates() {
        return ballCoordinates;
    }

    public XYSeries getEpCoordinates() {
        return epCoordinates;
    }
    public XYSeries getEkCoordinates() {
        return ekCoordinates;
    }
    public XYSeries getEtCoordinates() {
        return etCoordinates;
    }
}
