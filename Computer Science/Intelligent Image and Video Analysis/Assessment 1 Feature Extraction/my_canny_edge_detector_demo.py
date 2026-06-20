
# ITO5221: Intelligent Image and Video Analysis
# Assessment 1 - Task 2: Canny Edge Detector

# Python Version: 3.13
# Operating System: Windows 11 Pro

# Author: Tristan Sim Yook Min
# Student ID: 30428831 
# Date: 10/05/2026

# This Python Script Implements a Custom Canny Edge Detector and compares its output against skimage's canny edge detector.

# References: Canny, J. (1986). A Computational Approach to Edge Detection. IEEE Transactions on Pattern Analysis and Machine Intelligence, 8(6), pp. 679-698.

# Import the Required Libraries
import os
import numpy as np
from scipy.ndimage import gaussian_filter
from scipy.signal import convolve2d
from skimage import data
from skimage.color import rgb2gray
from skimage.feature import canny
from skimage.metrics import peak_signal_noise_ratio, structural_similarity
from matplotlib import pyplot as plt

def interpolate_gradient_horizontal(gradient_magnitude, i, j, offset, ratio, sin_gradient_direction):
    """
    Interpolate gradient magnitude along the horizontal direction

    Args:
        gradient_magnitude: (Numpy Array) 2D array of gradient magnitudes
        i: (Integer) Row index of the current pixel
        j: (Integer) Column index of the current pixel
        offset: (Integer) Column offset direction
        ratio: Interpolation weight derived from abs(sin/cos) of the gradient angle where 0 means horizontal and closer to 1 means closer to diagonal
        sin_gradient_direction: Sine of the gradient direction, used to select the diagonal neighbour row

    Returns:
        Interpolated gradient magnitude between the horizontal and diagonal neighbours
    """
    base = (1 - ratio) * gradient_magnitude[i, j + offset]

    if sin_gradient_direction < 0:
        neighbor = gradient_magnitude[i - 1, j + offset]
    else:
        neighbor = gradient_magnitude[i + 1, j + offset]

    return base + ratio * neighbor


def interpolate_gradient_vertical(gradient_magnitude, i, j, offset, ratio, cos_gradient_direction):
    """
    Interpolate gradient magnitude along a primarily vertical direction

    Args:
        gradient_magnitude: (Numpy Array) 2D array of gradient magnitudes
        i: (Integer) Row index of the current pixel
        j: (Integer) Column index of the current pixel
        offset: (Integer) Row offset direction
        ratio: Interpolation weight derived from abs(cos/sin) of the gradient angle where 0 means vertical and closer to 1 means closer to diagonal
        cos_gradient_direction: Cosine of the gradient direction, used to select the diagonal neighbour column

    Returns:
        Interpolated gradient magnitude between the vertical and diagonal neighbours
    """
    base = (1 - ratio) * gradient_magnitude[i + offset, j]

    if cos_gradient_direction > 0:
        neighbor = gradient_magnitude[i + offset, j + 1]
    else:
        neighbor = gradient_magnitude[i + offset, j - 1]

    return base + ratio * neighbor


def my_canny_edge_detector(image, low_threshold, high_threshold, sigma = 1.0):
    """
    Custom Canny Edge Detector Implementation.

    Args:
    image: (Numpy Array) Grayscale Image Input in 2D Array with values in [0, 1]
    low_threshold: (Float) Lower bound for hysteresis thresholding
    high_threshold: (Float) Upper bound for hysteresis thresholding
    sigma: (Float) Standard Deviation for Gaussian Smoothing. Default is 1.0

    Returns: 
    edge_map: (Numpy Array) Binary edge map with the same shape as the input image
    """

    # Ensure the Values of the Image are Floating Point Numbers 
    image_array = image.astype(np.float64)


    # Step 1: Gaussian Smoothing ------------------------------------------------------------------------------------------------
    # Smooth the image to supress any noise before computing the gradients (helps prevents false edges)
    smoothed_image = gaussian_filter(image_array, sigma=sigma)


    # Step 2: Compute Gradient Magnitude and Direction --------------------------------------------------------------------------
    # Using Sobel Kernels to estimate the image gradients in x and y directions
    # The Sobel operator combines smoothing [1,2,1] and differentiation [-1,0,1]
    sobel_x = np.array([[-1, 0, 1], [-2, 0, 2], [-1, 0, 1]], dtype=np.float64) / 8.0
    sobel_y = np.array([[-1, -2, -1], [0, 0, 0], [1, 2, 1]], dtype=np.float64) / 8.0

    # Apply Sobel Filters using SciPy convolve2d
    gradient_x = convolve2d(smoothed_image, sobel_x, mode='same', boundary='symm')  # Horizontal Gradient
    gradient_y = convolve2d(smoothed_image, sobel_y, mode='same', boundary='symm')  # Vertical Gradient

    # Compute the Gradient Magnitude to determine how strong the edge is at each pixel
    gradient_magnitude = np.sqrt(gradient_x ** 2 + gradient_y ** 2)

    # Normalise Gradient Magnitude to [0, 1] 
    if gradient_magnitude.max() > 0:
        gradient_magnitude = gradient_magnitude / gradient_magnitude.max()

    # Compute the Gradient Direction by determining the angle of the edge at each pixel in radians
    gradient_direction = np.arctan2(gradient_y, gradient_x)


    # Step 3: Non-Maximum Suppression -------------------------------------------------------------------------------------------
    # Interpolate along the exact graidient direction
    rows, cols = gradient_magnitude.shape
    suppressed = np.zeros_like(gradient_magnitude)

    for i in range(1, rows - 1):
        for j in range(1, cols - 1):
            direction = gradient_direction[i, j]
            magnitude = gradient_magnitude[i, j]

            # calculate the interpolated magnitudes along the gradient direction
            cos_direction = np.cos(direction)
            sin_direction = np.sin(direction)

            # Interpolate in the positive gradient direction
            if abs(cos_direction) > abs(sin_direction):
                ratio = abs(sin_direction / cos_direction)
                if cos_direction > 0:
                    neighbour_positive_direction = interpolate_gradient_horizontal(gradient_magnitude, i, j, 1, ratio, sin_direction)
                    neightbour_negative_direction = interpolate_gradient_horizontal(gradient_magnitude, i, j, -1, ratio, -sin_direction)
                else:
                    neighbour_positive_direction = interpolate_gradient_horizontal(gradient_magnitude, i, j, -1, ratio, sin_direction)
                    neightbour_negative_direction = interpolate_gradient_horizontal(gradient_magnitude, i, j, 1, ratio, -sin_direction)
            else:
                ratio = abs(cos_direction / sin_direction)
                if sin_direction > 0:
                    neighbour_positive_direction = interpolate_gradient_vertical(gradient_magnitude, i, j, 1, ratio, cos_direction)
                    neightbour_negative_direction = interpolate_gradient_vertical(gradient_magnitude, i, j, -1, ratio, -cos_direction)
                else:
                    neighbour_positive_direction = interpolate_gradient_vertical(gradient_magnitude, i, j, -1, ratio, cos_direction)
                    neightbour_negative_direction = interpolate_gradient_vertical(gradient_magnitude, i, j, 1, ratio, -cos_direction)

            # Only Keep the Pixel if it is a local maximum
            if magnitude >= neighbour_positive_direction and magnitude >= neightbour_negative_direction:
                suppressed[i, j] = magnitude


    # Step 4: Hysteresis Thresholding (Double Threshold and Edge Linking) -------------------------------------------------------
    # Classify pixels into strong edges, weak edges, and non-edges using two thresholds
    strong_mask = suppressed >= high_threshold
    weak_mask = (suppressed >= low_threshold) & (suppressed < high_threshold)

    # Edge Linking: Use connected component labelling to find groups of edge pixels
    # A weak edge is promoted if it belongs to the same connected region as any strong edge
    from scipy.ndimage import label as ndi_label
    combined_mask = strong_mask | weak_mask
    labeled_edges, number_of_features = ndi_label(combined_mask)

    # For each connected component, check if it contains at least one strong edge pixel
    edge_map = np.zeros_like(suppressed)
    for i in range(1, number_of_features + 1):
        component = (labeled_edges == i)
        if np.any(strong_mask[component]):
            edge_map[component] = 1.0

    return edge_map.astype(np.float64)


def process_image(input_image, image_name, low_threshold=0.05, high_threshold=0.15, sigma=1.0):
    """
    Execute both the custom and skimage canny edge Detectors to process the image.
    Compare results using  Peak Signal-to-Noise Ratio (PSNR) and Structural Similarity Index (SSI). 

    Args:
    input_image: (Numpy Array) The input image which can be RGB or grayscale
    image_name: (String) Name of the image for display titles
    low_threshold: (Float) Lower bound for hysteresis thresholding
    high_threshold: (Float) Upper bound for hysteresis thresholding
    sigma: (Float) Standard Deviation for Gaussian Smoothing

    Returns:
    edges_custom: (Numpy Array) Edge map from custom implementation
    edges_skimage: (Numpy Array) Edge map from skimage implementation
    psnr_value: (Float) Peak Signal-to-Noise Ratio between the two edge maps
    ssim_value: (Float) Structural Similarity Index between the two edge maps
    """
    # Convert to Grayscale if the image is RGB
    if input_image.ndim == 3:
        gray_image = rgb2gray(input_image)
    else:
        gray_image = input_image.astype(np.float64)
        if gray_image.max() > 1.0:
            gray_image = gray_image / 255.0

    # Run the Custom Canny Edge Detector
    edges_custom = my_canny_edge_detector(gray_image, low_threshold, high_threshold, sigma)

    # Run Skimage Canny Edge Detector with the same parameters
    edges_skimage = canny(gray_image, sigma=sigma, low_threshold=low_threshold, high_threshold=high_threshold)
    edges_skimage = edges_skimage.astype(np.float64)  # Convert boolean to float for comparison

    # Calculate Peak Signal-to-Noise Ratio and Structural Similarity Index between the two edge maps
    psnr_value = peak_signal_noise_ratio(edges_skimage, edges_custom, data_range=1.0) # Measures the pixel-level similarity (higher is better)
    ssi_value = structural_similarity(edges_skimage, edges_custom, data_range=1.0) # Measures the structural similarity (closer to 1.0 is better)

    # Print the Results
    print(f"\nCanny Edge Detection Result for: {image_name}")
    print(f"Input Parameters: Low Threshold = {low_threshold}, High Threshold = {high_threshold}, Sigma = {sigma}")
    print(f"\nPSNR (Peak Signal-to-Noise Ratio) = {psnr_value:.4f} dB")
    print(f"SSI (Structural Similarity Index) = {ssi_value:.4f}")
    print(f"\nGenerating Results for Image: {image_name}")

    # Plot the custom edge map and skimage edge map side by side
    figure, axes = plt.subplots(1, 2, figsize=(14, 6))
    axes[0].imshow(edges_custom, cmap='gray')
    axes[0].set_title(f'Custom Canny Edge Detector for {image_name}', fontsize=11)
    axes[0].axis('off')
    axes[1].imshow(edges_skimage, cmap='gray')
    axes[1].set_title(f'Skimage Canny Edge Detector for {image_name}', fontsize=11)
    axes[1].axis('off')
    plt.suptitle(f'Canny Edge Detection Comparison for {image_name}\n' f'PSNR: {psnr_value:.4f} dB | SSIM: {ssi_value:.4f}', fontsize=13)
    plt.tight_layout()

    # Save Plots to the Frames Folder
    script_directory = os.path.dirname(os.path.abspath(__file__))
    output_directory = os.path.join(script_directory, 'output')
    os.makedirs(output_directory, exist_ok=True)
    plt.savefig(os.path.join(output_directory, f'canny_edges_{image_name.lower().replace(" ", "_")}.png'), dpi=150, bbox_inches='tight')
    # plt.show()
    plt.close()
    print(f"End...\n")

    return edges_custom, edges_skimage, psnr_value, ssi_value


# Main Execution
if __name__ == '__main__':

    # Image 1: Astronaut 
    astronaut_image = data.astronaut()
    process_image(astronaut_image,"Astronaut",low_threshold=0.08,high_threshold=0.1,sigma=1.0)

    # Image 2: Checkerboard 
    checkerboard_image = data.checkerboard()
    process_image(checkerboard_image,"Checkerboard",low_threshold=0.1,high_threshold=0.2,sigma=1.0)
