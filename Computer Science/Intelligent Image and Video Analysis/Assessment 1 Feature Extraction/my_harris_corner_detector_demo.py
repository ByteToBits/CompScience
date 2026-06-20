
# ITO5221: Intelligent Image and Video Analysis
# Assessment 1 - Task 1: Harris Corner Detector

# Python Version: 3.13
# Operating System: Windows 11 Pro

# Author: Tristan Sim Yook Min
# Student ID: 30428831 
# Date: 10/05/2026

# This Python Script Implements a Custom Harris Corner Dectector.

# References: Harris, C. & Stephens, M. (1988). A Combined Corner and Edge Detector. Proceedings of the 4th Alvey Vision Conference, pp. 147-151.


# Import the Required Libraries
import os
import numpy as np
from scipy.ndimage import gaussian_filter, maximum_filter, label
from skimage import data
from skimage.color import rgb2gray
from skimage.feature import corner_harris, corner_peaks
from matplotlib import pyplot as plt
from scipy.signal import convolve2d

def my_harris_corner_detector(input_image, k, sigma, threshold_relativity, min_distance):
    """
    Custom Harris corner detector implementation.
    
    Args:
    input_image: (Numpy Array) Grayscale Image Input in 2D Array with Values from [0, 1] or [0, 255]
    k: (Float) Harris Dectector Sensitivity Factor with range [0, 0.2] where smaller values dectec sharper conners
    sigma: (Float) Standard Deviation for Gaussian sigma for smoothing gradient products
    threshold_relativity: (Float) Relative threshold for corner selection
    min_distance: (Integer) Minimum distance between corners in non-maximal suppression
    
    Returns: 
    corners_coordinates: (Numpy Array) Coordinates (x, y) of the Detected Corners relative to the Image Resolution
    harris_response: (Numpy Array) The full Harris response image R, same shape as input
    """
    # Ensure the Values of the Image Numpy Array are Floating Point Numbers (Cast to Float 64-bit Precision)
    image_grayscale_array = input_image.astype(np.float64)


    # Step 1: Compute image gradients Ix and Iy using Sobel Kernels --------------------------------------------------------------

    # The Sobel operator is a separable filter that combines smoothing [1,2,1] and differentiation [-1,0,1]
    # Divided by 8.0 to normalise the kernel weights
    sobel_x = np.array([[-1, 0, 1], [-2, 0, 2], [-1, 0, 1]], dtype = np.float64) / 8.0       # Detects horizontal intensity changes (vertical edges)
    sobel_y = np.array([[-1, -2, -1], [ 0,  0,  0], [ 1,  2,  1]], dtype = np.float64) / 8.0 # Detects vertical intensity changes (horizontal edges)

    # Apply Sobel Filters using SciPy convolve2d 
    image_gradient_x = convolve2d(image_grayscale_array, sobel_x, mode ='same', boundary ='symm')  # Horizontal Gradient
    image_gradient_y = convolve2d(image_grayscale_array, sobel_y, mode ='same', boundary ='symm')  # Vertical Gradient


    # Step 2: Compute gradient products -----------------------------------------------------------------------------------------
    # Forms the Components of the Structure Matrix M that captures the Local Gradient Distribution  M = [[Ix², IxIy], [IxIy, Iy²]]
    # Which Determines if the Region is Flat, An Edge or a Corner
    image_gradient_x2 = image_gradient_x ** 2     
    image_gradient_y2 = image_gradient_y ** 2     
    image_gradient_xy = image_gradient_x * image_gradient_y     


    # Step 3: Apply Gaussian smoothing to Gradient Products ------------------------------------------------------------------------
    # The Gaussian acts as the window function w(x, y) in the structure matrix: M = Σ w(x,y) [[Ix², IxIy], [IxIy, Iy²]]
    # The Gaussian weights nearby pixels more than distant ones, making the gradient estimate more noise-robust than a flat window
    smoothed_x2 = gaussian_filter(image_gradient_x2, sigma=sigma)   # Sigma controlsthe neighbourhood size
    smoothed_y2 = gaussian_filter(image_gradient_y2, sigma=sigma)
    smoothed_xy = gaussian_filter(image_gradient_xy , sigma=sigma)


    # Step 4: Compute Harris Response R for each pixel -------------------------------------------------------------------------
    # The Structure Matrix M at each pixel: M = [[Sx2, Sxy], [Sxy, Sy2]]
    # Harris Response: R = det(M) - k * (trace(M))^2  where   R > 0 is Corner | R < 0 is Edge | R ~ 0 is Flat 
    determinant_M = (smoothed_x2 * smoothed_y2) - (smoothed_xy ** 2)
    trace_M = smoothed_x2 + smoothed_y2
    harris_response = determinant_M - k * (trace_M ** 2)


    # Step 5: Threshold the Response to Select Candidate Corners ---------------------------------------------------------------
    # Keep pixels where R exceeds a fraction of the max response and a relative threshold adapts to different images
    threshold = threshold_relativity * harris_response.max()


    # Step 6: Non-Maximum Suppression -------------------------------------------------------------------------------------------
    neighbourhood_size = 2 * min_distance + 1  # Keep only local maxima within a neighbourhood of siz
    local_max = maximum_filter(harris_response, size = neighbourhood_size)  # Ensures detected corners are well separated and the strongest responses remain
    corner_mask = (harris_response > threshold) & (harris_response == local_max)  # Must be above threshold and a local maximum

    # If multiple adjacent pixels share the same max value, group them into connected regions and keep only the strongest
    labeled, number_of_features = label(corner_mask)
    corners_coordinates = []

    for i in range(1, number_of_features + 1):
        region = np.argwhere(labeled == i)
        best_index = np.argmax(harris_response [region[:, 0], region[:, 1]])
        corners_coordinates.append(region[best_index])
    
    if len(corners_coordinates) > 0:
        corners_coordinates = np.array(corners_coordinates)
    else: 
        corners_coordinates = np.empty((0, 2), dtype = int)

    return corners_coordinates, harris_response


def count_overlapped_corners(corners_detected_custom, corners_detected_skimage, tolerance = 3):
    """
    Count the number of overlapping corners between Custom Harris and the Skimage Library. 
    This method compares the two different implementations using the euclidean distance between
    the pixels with a tolerance threshold

    Args:
    corners_detected_custom: (Numpy Array, Shape (N, 2)) Cordinates detected (x, y) from custom Harris Implementation
    corners_detected_skimage: (Numpy Array, Shape (N, 2)) Cordinates detected (x, y) from Skimage Implementation
    tolerance: (Integer)  Maximum Pixel Distance for the Corners to be Considered as Overlapping

    Returns: 
    overlap_count: (Integer) Number of overlapping corners
    """

    # Skip Method if either Implementations Detect no Corners
    if len(corners_detected_custom) == 0 or len(corners_detected_skimage) == 0:
        return 0
    
    # Initialize Variables
    overlap_count = 0
    matching_corners = set()  # Track which skimage corners have been matched

    # For each custom corner, find the closest unmatched skimage corner
    for corners in corners_detected_custom:

        distances = np.sqrt(np.sum((corners_detected_skimage - corners) ** 2, axis = 1))
        sorted_indices = np.argsort(distances) # Sort by Nearest to Farthest

        for index in sorted_indices:
            if distances[index] > tolerance:
                break
            if index not in matching_corners: # Ensure each skimage corner is only matched once
                overlap_count += 1
                matching_corners.add(index)
                break

    return overlap_count


def process_image(input_image, image_name, k = 0.05, sigma = 1.0, threshold_relativity = 0.01, min_distance = 5, tolerance = 3):
    """
    Run both the custom and skimage Harris corner detectors on an image,
    display the results side by side, and print corner count statistics.

    Args:
    input_image: (Numpy Array) Grayscale Image Input in 2D Array with Values from [0, 1] or [0, 255]
    image_name: (String) Name of the image for display titles
    k: (Float) Harris Dectector Sensitivity Factor with range [0, 0.2] where smaller values dectec sharper conners
    sigma: (Float) Standard Deviation for Gaussian sigma for smoothing gradient products
    threshold_relativity: (Float) Relative threshold for corner selection
    min_distance: (Integer) Minimum distance between corners in non-maximal suppression
    tolerance: (Integer)  Maximum Pixel Distance for the Corners to be Considered as Overlapping

    Returns:
    corners_custom: (Numpy Array) Coordinates detected from custom implementation
    corners_skimage: (Numpy Array) Coordinates detected from skimage implementation
    overlap_count: (Integer) Number of overlapping corners between the two methods
    """
    # Convert to Grayscale if the image is RGB
    if input_image.ndim == 3:
        gray_image = rgb2gray(input_image)
    else:
        gray_image = input_image.astype(np.float64) # Clamp Values to [0, 1] if Already in Grayscale
        if gray_image.max() > 1.0:
            gray_image = gray_image / 255.0

    # Run the Custom Harris Corner Detector
    corners_custom, response_custom = my_harris_corner_detector(gray_image, k, sigma,threshold_relativity, min_distance)

    # Run Skimage Harris Coner Detector with same Parameters as the Custom Implementation
    harris_response_skimage = corner_harris(gray_image, method = 'k', k = k, sigma = sigma)
    corners_skimage = corner_peaks(harris_response_skimage, min_distance=min_distance, threshold_rel=threshold_relativity)

    # Count the number of Overlapping Corners that Occur
    overlap_count = count_overlapped_corners(corners_custom, corners_skimage, tolerance)

    # Print the Results of the Corner Detection
    print(f"\nCorner Detection Result for: {image_name}")
    print(f"Input Parameters: k = {k}, Sigma = {sigma}, Threshold Relativity = {threshold_relativity}, Min. Distance = {min_distance}")
    print(f"\nCorners Detected for Custom Harris Detector = {len(corners_custom)}")
    print(f"Corners Detected for Skimage Harris Detector = {len(corners_skimage)}")
    print(f"\nNumber of Overlapping Corners (with a Tolerance of {tolerance} pixels) = {overlap_count}")
    print(f"\nGenerating Results for Image: {image_name}")

    # Task 1.1: Plot both sets of corners on the same image 
    figure, axis = plt.subplots(1, 1, figsize=(10, 8))
    axis.imshow(gray_image, cmap='gray')

    # For the Custom Harris Corners, it is indicated using red circle markers
    if len(corners_custom) > 0:
        axis.plot(corners_custom[:, 1], corners_custom[:, 0],'ro', markersize=6, markerfacecolor='none', linewidth=1.5, label=f'Custom Harris ({len(corners_custom)} corners)')
    
    # For the Skimage Harris Corners, it is indicated using blue '+' markers
    if len(corners_skimage) > 0:
        axis.plot(corners_skimage[:, 1], corners_skimage[:, 0],'b+', markersize=8, markeredgewidth=1.5, label=f'skimage corner_peaks ({len(corners_skimage)} corners)')
    
    # Plot the Image with all the Detected Corners
    axis.set_title(f'Harris Corner Detection for Image: {image_name}\n' f'Custom (red o): {len(corners_custom)} | 'f'skimage (blue +): {len(corners_skimage)} | '
                 f'Overlap: {overlap_count}', fontsize=12)
    axis.legend(loc='upper right', fontsize=10)
    axis.axis('off')
    plt.tight_layout()
    
    # Save Plots to the Frames Folder
    script_directory = os.path.dirname(os.path.abspath(__file__))
    output_directory = os.path.join(script_directory, 'output')
    os.makedirs(output_directory, exist_ok=True)
    plt.savefig(os.path.join(output_directory, f'harris_corners_{image_name.lower().replace(" ", "_")}.png'),dpi=150, bbox_inches='tight')
    # plt.show()
    plt.close()
    print(f"End...\n")

    return corners_custom, corners_skimage, overlap_count


# Main execution Python Execution
if __name__ == '__main__':

    # Image 1: Astronaut 
    astronaut_image = data.astronaut()
    process_image(astronaut_image,"Astronaut",k=0.05, sigma=1.0,threshold_relativity=0.01,min_distance=5)

    # Image 2: Checkerboard 
    checkerboard_image = data.checkerboard()
    process_image(checkerboard_image,"Checkerboard",k=0.05, sigma=1.0,threshold_relativity=0.01,min_distance=5)
