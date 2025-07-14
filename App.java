package cs1501_p5;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("🎨 Welcome to the Image Quantizer!");

            // Prompt for image name
            String imageName = "";
            while (imageName.isEmpty()) {
                System.out.print("Enter the name of your image (e.g., bird.bmp): ");
                imageName = scanner.nextLine().trim();
                if (!imageName.endsWith(".bmp")) {
                    System.out.println("Please make sure your file ends with .bmp.");
                    imageName = "";
                }
            }
            String imagePath = System.getProperty("user.home") + "/Downloads/" + imageName;

            // Prompt for quantization method
            String method = "";
            while (!(method.equals("clustering") || method.equals("bucketing"))) {
                System.out.print("Choose quantization method (clustering / bucketing): ");
                method = scanner.nextLine().trim().toLowerCase();
                if (!(method.equals("clustering") || method.equals("bucketing"))) {
                    System.out.println("Invalid choice. Please enter 'clustering' or 'bucketing'.");
                }
            }

            // Prompt for distance metric
            String metricChoice = "";
            while (!(metricChoice.equals("squared") || metricChoice.equals("circular"))) {
                System.out.print("Choose distance metric (squared / circular): ");
                metricChoice = scanner.nextLine().trim().toLowerCase();
                if (!(metricChoice.equals("squared") || metricChoice.equals("circular"))) {
                    System.out.println("Invalid choice. Please enter 'squared' or 'circular'.");
                }
            }

            // Prompt for number of colors
            int k = 0;
            while (k <= 0) {
                System.out.print("Enter number of colors to quantize to (e.g., 3): ");
                String input = scanner.nextLine().trim();
                try {
                    k = Integer.parseInt(input);
                    if (k <= 0) {
                        System.out.println("Number must be greater than 0.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid integer.");
                }
            }

            // Set up metric and generator
            DistanceMetric metric = metricChoice.equals("circular")
                ? new CircularMetric()
                : new SquaredEuclideanMetric();

            ColorMapGenerator_Inter generator = method.equals("bucketing")
                ? new BucketingMapGenerator()
                : new ClusteringMapGenerator(metric);

            // Create and run quantizer
            ColorQuantizer quantizer = new ColorQuantizer(imagePath, generator);
            String outputName = "build/resources/main/" + imageName.replace(".bmp", "") + "_quantized.bmp";
            quantizer.quantizeToBMP(outputName, k);

            System.out.println("Quantization complete! Output saved to: " + outputName);

        } catch (Exception e) {
            System.err.println("Unexpected error during quantization:");
            e.printStackTrace();
        }
    }
}
