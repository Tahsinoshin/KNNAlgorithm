import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Driver {
    public static void main(String[] args) throws IOException {
   /* //    for(int i=1;i<11;i++){
    //         BufferedImage img = ImageIO.read(new File("sunset"+i+".jpg"));
    //         img = resizeImage(img, 500,500);
    //         File output = new File("training\\Sunset\\sunset"+i+".png");
    //         ImageIO.write(img, "png", output);
    //     }

    */

        int[][] treeRGB = new int[10][500*500];
        int[][] nonTreeRGB = new int[10][500*500];

        for (int i=1; i<11;i++){
            BufferedImage tree = ImageIO.read(new File("training\\Tree\\tree"+i+".png"));
            BufferedImage nonTree = ImageIO.read(new File("training\\Sunset\\sunset"+i+".png"));
            treeRGB[i-1] =tree.getRGB(0,0,500,500,null,0,500);
            nonTreeRGB[i-1] = nonTree.getRGB(0,0,500,500,null,0,500);
        }

        int[] testRGB = new int[500*500];
        BufferedImage testImage = ImageIO.read(new File("test2.jpg"));
        testRGB =testImage.getRGB(0,0,500,500,null,0,500);
    

        int treeCorrect = 0;
        int nonTreeCorrect = 0;


        //tree testing
        for(int i=0; i<10;i++){
            double[] distances = new double[18];
            int[] type = new int[18];

            int count = 0;
            for (int j =0; j<10; j++)
                if(i!=j){
                    distances[count] = distance(treeRGB[j], treeRGB[i] );
                    type[count] = 0;
                    count++;
                    distances[count] = distance(nonTreeRGB[j], treeRGB[i] );
                    type[count] = 1;
                    count++;
                }

            for(int k=0; k<18;k++)
                for(int l=k+1; l<18; l++)
                    if(distances[k] > distances[l]){
                        double temp = distances[k];
                        distances[k] = distances[l];
                        distances[l] = temp;

                        int tType = type[k];
                        type[k] = type[l];
                        type[l] = tType;
                    }

            int[] knn = new int[2];
            for(int k=0; k<5;k++)
                knn[type[k]]++;

            if(knn[0] > knn[1]) {
                System.out.println("tree "+(i+1)+" correct");
                treeCorrect++;
            } else System.out.println("tree "+(i+1)+" wrong");
        }

        //nonTree testing
        for(int i=0; i<10;i++){
            double[] distances = new double[18];
            int[] type = new int[18];

            int count = 0;
            for (int j =0; j<10; j++)
                if(i!=j){
                    distances[count] = distance(treeRGB[j], nonTreeRGB[i] );
                    type[count] = 0;
                    count++;
                    distances[count] = distance(nonTreeRGB[j], nonTreeRGB[i] );
                    type[count] = 1;
                    count++;
                }

            for(int k=0; k<18;k++)
                for(int l=k+1; l<18; l++)
                    if(distances[k] > distances[l]){
                        double temp = distances[k];
                        distances[k] = distances[l];
                        distances[l] = temp;

                        int tType = type[k];
                        type[k] = type[l];
                        type[l] = tType;
                    }

            int[] knn = new int[2];
            for(int k=0; k<5;k++)
                knn[type[k]]++;

            if(knn[0] < knn[1]) {
                System.out.println("sunset "+(i+1)+" correct");
                nonTreeCorrect++;
            } else System.out.println("sunset "+(i+1)+" wrong");
        }

        System.out.println("tree: "+treeCorrect+"\nsunset: "+nonTreeCorrect);
        System.out.println("\n\n\n"); 
        
        
        //test image Testing
        double[] distances = new double[20];
        int[] type = new int[20];

        int count = 0;
        for (int j =0; j<10; j++){
            distances[count] = distance(treeRGB[j],testRGB);
            type[count] = 0;
            count++;
            distances[count] = distance(nonTreeRGB[j],testRGB);
            type[count] = 1;
            count++;
        }

        for(int k=0; k<20;k++)
            for(int l=k+1; l<20; l++)
                if(distances[k] > distances[l]){
                    double temp = distances[k];
                    distances[k] = distances[l];
                    distances[l] = temp;

                    int tType = type[k];
                    type[k] = type[l];
                    type[l] = tType;
                }

        int[] knn = new int[2];
        for(int k=0; k<5;k++)
            knn[type[k]]++;

        if(knn[0] < knn[1]) {
            System.out.println("test image tree");
            //nonTreeCorrect++;
        } else System.out.println("test image sunset");

     }

    private static double distance(int[] a, int[] b){
        double result = 0;
        for (int i=0;i<a.length;i++){
            result+=Math.pow((a[i]-b[i]),2);
        }
        return Math.sqrt(result);
    }

/*//    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight){
//        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
//        Graphics2D graphics2D = resizedImage.createGraphics();
//        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
//        graphics2D.dispose();
//        return resizedImage;
//    }
*/
}