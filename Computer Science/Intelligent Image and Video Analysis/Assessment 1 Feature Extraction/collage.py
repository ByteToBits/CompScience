"""
ITO5221: Intelligent Image and Video Analysis
Assessment 1 - Task 3: Make Collage

Python Version: 3.13
Operating System: Windows 11 Pro

Author: Tristan Sim Yook Min
Student ID: 30428831 
Date: 10/05/2026

This Python Script creates a Collage from the 5 Vidoe Frames and are sorted based on the colour histogram variance and edge density features. 
Images with less variation are placed in the center whilst images with larger variations are placed at the boundary of the collage. 
Alpha blending is used for a smoother transitions between the adjacent images.

"""

# Import the Required Libraries
import os
import numpy as np
from skimage import io
from skimage.color import rgb2gray
from skimage.feature import canny
from skimage.transform import resize
from matplotlib import pyplot as plt
from PIL import Image


def compute_colour_variance(image):
    """
    Compute the colour variance of an image across all RGB channels where higher variance indicates more colour variation in the image

    Args:
    image: (Numpy Array) RGB Image with shape (Height, Width, 3)

    Returns:
    colour_variance: (Float) Sum of variance across R, G, B channels
    """
    colour_variance = 0.0
    for channel in range(image.shape[2]):
        colour_variance += np.var(image[:, :, channel])
    return colour_variance


def compute_edge_density(image, sigma=1.0):
    """
    Compute the edge density of an image using the Canny Edge Detector
    Edge density is the ratio of edge pixels to total pixels and higher edge density indicates more structural detail

    Args:
    image: (Numpy Array) RGB Image with shape(Height, Width, 3)
    sigma: (Float) Standard Deviation for Gaussian Smoothing 

    Returns:
    edge_density: (Float) Ratio of edge pixels to total pixels [0, 1]
    """
    grayscale_image = rgb2gray(image)
    edge_map = canny(grayscale_image, sigma=sigma)
    edge_density = np.sum(edge_map) / edge_map.size
    return edge_density


def compute_feature_score(image, sigma=1.0):
    """
    Compute colour variance and edge density for a given image

    Args:
    image: (Numpy Array) RGB Image with shape (H, W, 3)
    sigma: (Float) Standard Deviation for Gaussian Smoothing in Canny detector

    Returns:
    colour_variance: (Float) Raw colour variance value
    edge_density: (Float) Raw edge density value
    """
    colour_variance = compute_colour_variance(image)
    edge_density = compute_edge_density(image, sigma)
    return colour_variance, edge_density


def sort_images_by_features(images, filenames):
    """
    Sort images by their combined feature score which is the colour variance + edge density
    Images with less variation are placed in the centre of the collage and images with more variation are placed on the boundary
    The collage layout for 5 images is [boundary, boundary, centre, boundary, boundary]

    Args:
    images: (List of Numpy Arrays) List of RGB images
    filenames: (List of Strings) Corresponding file names

    Returns:
    sorted_images: (List of Numpy Arrays) Images sorted for collage placement
    sorted_filenames: (List of Strings) Corresponding sorted file names
    feature_data: (List of Tuples) (index, colour_variance, edge_density) for each image
    """
    # Compute features for each image
    feature_data = []
    for i, image in enumerate(images):
        colour_variation, edge_density = compute_feature_score(image)
        feature_data.append((i, colour_variation, edge_density))
        print(f"{filenames[i]}: Colour Variance = {colour_variation:.4f}, Edge Density = {edge_density:.4f}")

    # Normalise features to [0, 1] range so they contribute equally
    colour_values = [feature[1] for feature in feature_data]
    edge_values = [feature[2] for feature in feature_data]

    colour_min, colour_max = min(colour_values), max(colour_values)
    edge_min, edge_max = min(edge_values), max(edge_values)

    # Check for any division by zero
    colour_range = colour_max - colour_min if colour_max != colour_min else 1.0
    edge_range = edge_max - edge_min if edge_max != edge_min else 1.0

    combined_scores = []
    for i, colour_variation, edge_density in feature_data:
        normalized_colour = (colour_variation - colour_min) / colour_range
        normalized_edge = (edge_density - edge_min) / edge_range
        combined = 0.5 * normalized_colour + 0.5 * normalized_edge
        combined_scores.append((i, combined))
        print(f"{filenames[i]}: Combined Score = {combined:.4f}")

    # Sort by combined score where least variation is first
    combined_scores.sort(key = lambda x: x[1])

    # Arrange for collage: least variation in centre, most on boundaries
    sorted_indices = [scores[0] for scores in combined_scores]

    collage_order = [
        sorted_indices[4],  # Left outer (highest variation)
        sorted_indices[2],  # Left inner
        sorted_indices[0],  # Centre (lowest variation)
        sorted_indices[1],  # Right inner
        sorted_indices[3],  # Right outer (second highest variation)
    ]

    sorted_images = [images[i] for i in collage_order]
    sorted_filenames = [filenames[i] for i in collage_order]

    return sorted_images, sorted_filenames, feature_data


def Create_collage(folder_path):
    """
    Create a collage from images in the specified folder path. Images are sorted by colour variance and edge density features 
    where images with less variation are placed in the centre and images with more variation are placed on the boundaries. 
    Alpha blending is applied at the borders for smooth transitions.

    Args:
    folder_path: (String) Relative address of the folder containing images

    Returns:
    None (saves the collage image to the Output folder)
    """
    # Load all images from the folder and filter out any previous collage outputs
    supported_formats = ('.png', '.jpg', '.jpeg')
    filenames = []
    for f in os.listdir(folder_path):
        if f.lower().endswith(supported_formats) and 'collage' not in f.lower():
            filenames.append(f)
    filenames.sort()

    if len(filenames) < 5:
        print(f"Error: Expected at least 5 images, found {len(filenames)}")
        return

    print(f"\nLoading {len(filenames)} images from: {folder_path}")
    images = []
    for filename in filenames:
        filepath = os.path.join(folder_path, filename)
        image = io.imread(filepath)
        # Convert RGBA to RGB if necessary
        if image.ndim == 3 and image.shape[2] == 4:
            image = image[:, :, :3]
        images.append(image)
        print(f"Loaded: {filename} ({image.shape[1]} x {image.shape[0]})")

    # Resize all images to the same dimensions (use the first image as reference)
    target_height, target_width = images[0].shape[:2]
    print(f"\nResizing all images to {target_width} x {target_height}")

    resized_images = []
    for image in images:
        if image.shape[0] != target_height or image.shape[1] != target_width:
            resized = resize(image, (target_height, target_width), preserve_range=True, anti_aliasing=True)
            resized_images.append(resized.astype(np.uint8))
        else:
            resized_images.append(image)

    # Sort images by features for collage placement
    print("\nComputing features for each image:")
    sorted_images, sorted_filenames, feature_data = sort_images_by_features(resized_images, filenames)
    print(f"\nCollage order (left to right): {sorted_filenames}")

    # Create the collage with alpha blending between adjacent images
    # Blend width controls how many pixels are used for the transition region
    blend_width = int(target_width * 0.15)  # 15% of image width for blending overlap
    single_visible_width = target_width - blend_width  # Visible portion per image (excluding overlap)
    collage_width = single_visible_width * len(sorted_images) + blend_width
    collage_height = target_height

    # Initialise the collage canvas and weight map for normalisation
    collage = np.zeros((collage_height, collage_width, 3), dtype=np.float64)
    weight_map = np.zeros((collage_height, collage_width), dtype=np.float64)

    for i, image in enumerate(sorted_images):

        # Calculate the x-offset for this image in the collage
        x_offset = i * single_visible_width
        image_float = image.astype(np.float64)

        # Create the alpha mask for this image
        alpha_mask = np.ones(target_width, dtype=np.float64)

        # Apply fade-out on the right side
        if i < len(sorted_images) - 1:
            alpha_mask[-blend_width:] = np.linspace(1.0, 0.0, blend_width)

        # Apply fade-in on the left side
        if i > 0:
            alpha_mask[:blend_width] = np.linspace(0.0, 1.0, blend_width)

        # Vectorised blending and apply alpha across all rows at once
        alpha_2d = alpha_mask[np.newaxis, :] 
        end_column = min(x_offset + target_width, collage_width)
        actual_width = end_column - x_offset

        for column in range(3):
            collage[:, x_offset:end_column, column] += image_float[:, :actual_width, column] * alpha_2d[:, :actual_width]
        weight_map[:, x_offset:end_column] += alpha_2d[:, :actual_width]

    # Normalise the collage by the weight map to handle overlapping regions
    for column in range(3):
        collage[:, :, column] = np.where(weight_map > 0, collage[:, :, column] / weight_map, 0)

    collage = np.clip(collage, 0, 255).astype(np.uint8)

    # Save the collage image to the Output folder
    script_directory = os.path.dirname(os.path.abspath(__file__))
    output_directory = os.path.join(script_directory, 'output')
    os.makedirs(output_directory, exist_ok=True)
    output_path = os.path.join(output_directory, 'collage_output.png')
    io.imsave(output_path, collage)
    # Image.fromarray(collage).save(output_path, quality=95)
    print(f"\nCollage saved to: {output_path}")


# Main Execution
if __name__ == '__main__':

    # The folder path is relative to the script location
    script_directory = os.path.dirname(os.path.abspath(__file__))
    scene_folder = os.path.join(script_directory, 'scenes')

    Create_collage(scene_folder)
