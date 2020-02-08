package frc.robot;

import edu.wpi.first.networktables.*;


public class LimelightUtility {

    static private double tv;      //Whether the limelight has any valid targets (0 or 1)
    static private double tx;	    //Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
    static private double ty;	    //Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
    static private double ta;	    //Target Area (0% of image to 100% of image)
    static private double ts;	    //Skew or rotation (-90 degrees to 0 degrees)
    static private double tl;	    //The pipeline’s latency contribution (ms) Add at least 11ms for image capture latency.
    static private double tshort;	//Sidelength of shortest side of the fitted bounding box (pixels)
    static private double tlong;	//Sidelength of longest side of the fitted bounding box (pixels)
    static private double thoriz;	//Horizontal sidelength of the rough bounding box (0 - 320 pixels)
    static private double tvert;	//Vertical sidelength of the rough bounding box (0 - 320 pixels)
    static private double camtran[];  // 3d translation of image from limelight

    static public boolean ValidTargetFound() { return tv != 0.0; }     //Whether the limelight has any valid targets (0 or 1)
    static public double TargetHorizontalOffset;                       //Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
    static public double TargetVerticalOffset;                         //Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
    static public double TargetAreaPercentage;                         //Target Area (0% of image to 100% of image)
    static public double TargetSkew;                                   //Skew or rotation (-90 degrees to 0 degrees)
    static public double PipelineLatency;                              //The pipeline’s latency contribution (ms) Add at least 11ms for image capture latency.
    static public double TargetSideLengthShortest;                     //Sidelength of shortest side of the fitted bounding box (pixels)
    static public double TargetSideLenghtLongest;                      //Sidelength of longest side of the fitted bounding box (pixels)
    static public double TargetHorizSideLengthRoughBox;                //Horizontal sidelength of the rough bounding box (0 - 320 pixels)
    static public double TargetVertSideLengthRoughbox;                 //Vertical sidelength of the rough bounding box (0 - 320 pixels)
    static public double Camera3dTranslation_x;
    static public double Camera3dTranslation_y;
    static public double Camera3dTranslation_z;
    static public double Camera3dTranslation_pitch;
    static public double Camera3dTranslation_yaw;
    static public double Camera3dTranslation_roll;

    static public void RefreshTrackingData() {
        var table = NetworkTableInstance.getDefault().getTable("limelight");
        tv = table.getEntry("tv").getDouble(0);
        tx = table.getEntry("tx").getDouble(0);
        ty = table.getEntry("ty").getDouble(0);
        ta = table.getEntry("ta").getDouble(0);
        ts = table.getEntry("ts").getDouble(0);
        tl = table.getEntry("tl").getDouble(0);
        tshort = table.getEntry("tshort").getDouble(0);
        tlong  = table.getEntry("tlong").getDouble(0);
        thoriz = table.getEntry("thor").getDouble(0);
        tvert  = table.getEntry("tvert").getDouble(0);
        double e[] = new double[6];
        camtran = table.getEntry("camtran").getDoubleArray(e);

        TargetHorizontalOffset          = tx;
        TargetVerticalOffset            = ty;
        TargetAreaPercentage            = ta;
        TargetSkew                      = ts;
        PipelineLatency                 = tl;
        TargetSideLengthShortest        = tshort;
        TargetSideLenghtLongest         = tlong;
        TargetHorizSideLengthRoughBox   = thoriz; 
        TargetVertSideLengthRoughbox    = tvert;
        Camera3dTranslation_x           = camtran[0];
        Camera3dTranslation_y           = camtran[1];
        Camera3dTranslation_z           = camtran[2];
        Camera3dTranslation_pitch       = camtran[3];
        Camera3dTranslation_yaw         = camtran[4];
        Camera3dTranslation_roll        = camtran[5];
    }

    static public void LogTrackingData() {
        RefreshTrackingData();
        System.out.println('.');System.out.println('.');
        System.out.println("######################################################");
        System.out.println("#            Tracking data from Limelight            #");
        System.out.println("######################################################");
        System.out.print("TargetFound                     =");System.out.println(tv);
        System.out.print("TargetHorizontalOffset          =");System.out.println(tx);
        System.out.print("TargetVerticalOffset            =");System.out.println(ty);
        System.out.print("TargetAreaPercentage            =");System.out.println(ta);
        System.out.print("TargetSkew                      =");System.out.println(ts);
        System.out.print("PipelineLatency                 =");System.out.println(tl);
        System.out.print("TargetSideLengthShortest        =");System.out.println(tshort);
        System.out.print("TargetSideLenghtLongest         =");System.out.println(tlong);
        System.out.print("TargetHorizSideLengthRoughBox   =");System.out.println(thoriz);
        System.out.print("TargetVertSideLengthRoughbox    =");System.out.println(tvert);
        System.out.print("Camera3dTranslation_x           =");System.out.println(Camera3dTranslation_x);     
        System.out.print("Camera3dTranslation_y           =");System.out.println(Camera3dTranslation_y);     
        System.out.print("Camera3dTranslation_z           =");System.out.println(Camera3dTranslation_z);     
        System.out.print("Camera3dTranslation_pitch       =");System.out.println(Camera3dTranslation_pitch);  
        System.out.print("Camera3dTranslation_yaw         =");System.out.println(Camera3dTranslation_yaw);   
        System.out.print("Camera3dTranslation_roll        =");System.out.println(Camera3dTranslation_roll);   
        System.out.println("######################################################");
    }

    static public void WriteDouble(String tableField, double fieldValue){
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        System.out.print("Setting Limelight value:  ");
        System.out.print(tableField);
        System.out.print(" = ");
        System.out.println(fieldValue);
        table.getEntry(tableField).setNumber(fieldValue);
    }

    static public void EnableDriverCamera(Boolean value) {
        WriteDouble("camMode", value ? 1 : 0); // if value true, write 1 for driver camera, otherwise write 0
    }

    static public enum StreamMode{
        Standard, PIPMain, PIPSecondary
    }

    static public void StreamingMode(StreamMode mode) {
        double value;
        switch (mode) {
            case Standard:     value = 0; break;
            case PIPMain:      value = 1; break;
            case PIPSecondary: value = 2; break;
            default: value = 0;
        }
        WriteDouble("stream", value);
    }

    static public void SelectPipeline(int pipeline) {
        if (pipeline < 0 || pipeline > 9) {
            System.out.print("SelectPipeline: Invalid pipeline requested: ");
            System.out.println(pipeline);
        }
        else {
            WriteDouble("pipeline", pipeline);
        }
    }
}