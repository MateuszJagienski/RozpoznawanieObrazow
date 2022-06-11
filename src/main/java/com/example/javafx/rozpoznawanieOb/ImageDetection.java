package com.example.javafx.rozpoznawanieOb;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.*;


public class ImageDetection {

    public void dets(String filename, double threshold1, double threshold2) throws IOException {
        Mat cannyEdges = new Mat();
        Mat hierarchy = new Mat();

        List<MatOfPoint> contourList = new ArrayList<>(); //A list to store all the contours

        Mat src = Imgcodecs.imread(filename);
        if (src.empty()) {
            System.out.println(1);
            System.err.println("Cannot read image: " + filename);
            System.exit(0);
        }
        Imgproc.medianBlur(src, src, 5);

        Imgproc.Canny(src, cannyEdges, threshold1, threshold2, 7, true);
        Imgproc.findContours(cannyEdges, contourList, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        for (var count : contourList) {
            var area = Imgproc.contourArea(count);
            MatOfPoint approxf1 = new MatOfPoint();

            if (area > 4000) {
                Rect boundRect = new Rect();
                MatOfPoint2f c2f = new MatOfPoint2f(count.toArray());
                MatOfPoint2f approx = new MatOfPoint2f();
                var epsilon = 0.01 * Imgproc.arcLength(c2f, true);
                Imgproc.approxPolyDP(c2f, approx, epsilon, true);
                approx.convertTo(approxf1, CvType.CV_32S);
                List<MatOfPoint> contourTemp = new ArrayList<>();
                contourTemp.add(approxf1);
                boundRect = Imgproc.boundingRect(approx);
                if (!(boundRect.width > 1.5 * boundRect.height) && !(boundRect.height > 1.5 * boundRect.width)) {
                    Imgproc.drawContours(src, contourTemp, 0, new Scalar(255, 255, 0), 1);
                    Imgproc.rectangle(src, boundRect.tl(), boundRect.br(), new Scalar(255, 255, 0), 5);
                }

                switch (approxf1.toList().size()) {
                    case 3:
                        Imgproc.putText(src, "Triangle sign", boundRect.tl(), Imgproc.FONT_HERSHEY_DUPLEX, 0.5, new Scalar(255, 255, 0));
                        break;
                    case 4:
                        Imgproc.putText(src, "square sign", boundRect.tl(), Imgproc.FONT_HERSHEY_DUPLEX, 0.5, new Scalar(255, 255, 0));
                        break;
                    default:
                        Imgproc.putText(src, "Circle or polygon sign", boundRect.tl(), Imgproc.FONT_HERSHEY_DUPLEX, 0.5, new Scalar(255, 255, 255));
                }
            }
        }

            HighGui.imshow("adsad", src);
            HighGui.waitKey(0);
            HighGui.destroyAllWindows();
    }


    public static void main(String[] args) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        ImageDetection imageDetection = new ImageDetection();
       // imageDetection.dets("src/main/resources/img_7.png", 75, 150);
        imageDetection.dets("src/main/resources/img_7.png", 75, 150);
        imageDetection.dets("src/main/resources/img_8.png", 75, 150);
        imageDetection.dets("src/main/resources/img_9.png", 75, 150);
        imageDetection.dets("src/main/resources/img_10.png", 75, 150);
        imageDetection.dets("src/main/resources/img_11.png", 75, 150);
        imageDetection.dets("src/main/resources/img_12.png", 75, 150);
        imageDetection.dets("src/main/resources/img_13.png", 75, 150);
        imageDetection.dets("src/main/resources/img_14.png", 75, 150);
    }

}
