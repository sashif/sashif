import cv2
import numpy as np
import skimage
from matplotlib import pyplot as plt

image = cv2.imread('heart.jpg')  # import source image that we will be using

# the following will be applications of cv2 functions to add filters and produce alterations of source image
orig = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)  # apply the original RGB values of source image

heavy_blur = cv2.GaussianBlur(orig, (11, 11), 100)  # apply heavy gaussian filter onto the original image

gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)  # produce a gray scale version of original image
smooth_gray = cv2.GaussianBlur(gray, (3, 3), 0)  # apply slight gaussian blur to grayscale image to remove noise

laplacian = cv2.Laplacian(smooth_gray, cv2.CV_64F)  # apply laplacian filter on smooth, grayscale image

hst_eq = cv2.equalizeHist(gray)  # apply histogram equalization on grayscale version of image

blur = skimage.filters.gaussian(gray, 3.0)  # produce a blurred version of the grayscale image
t = 0.4  # threshold, ideal value varies depending on image used
mask = blur < t  # apply a mask using the blurred image within the threshold

edge = cv2.Laplacian(smooth_gray, cv2.CV_16S, 3)  # apply laplacian filter/edge detection
edge_detection = cv2.convertScaleAbs(edge)

matrix = np.ones((3, 3), np.float32) / 9  # creating a 3x3 matrix with 9 cells
light_blur = cv2.filter2D(orig, -1, matrix)  # applying a light blur to original image
kernel = np.array([[0, -1, 0],  # adding values to the cells of the kernel
                   [-1, 5, -1],
                   [0, -1, 0]])
sharpened = cv2.filter2D(orig, -1, kernel)  # apply sharpening filter to the original image

# display the resulting images in a 9x9 grid, labeled with names of respective filters
plt.subplot(3, 3, 1), plt.imshow(orig, 'gray')
plt.title('Original'), plt.xticks([]), plt.yticks([])
plt.subplot(3, 3, 2), plt.imshow(light_blur, 'gray')
plt.title('Smooth'), plt.xticks([]), plt.yticks([])
plt.subplot(3, 3, 3), plt.imshow(heavy_blur, 'gray')
plt.title('Gaussian Blur'), plt.xticks([]), plt.yticks([])
plt.subplot(3, 3, 4), plt.imshow(sharpened, 'gray')
plt.title('Sharpness'), plt.xticks([]), plt.yticks([])
plt.subplot(3, 3, 5), plt.imshow(edge_detection, 'gray')
plt.title('Edge Detection'), plt.xticks([]), plt.yticks([])
plt.subplot(3, 3, 6), plt.imshow(laplacian, 'gray')
plt.title('Laplacian Filter'), plt.xticks([]), plt.yticks([])
plt.subplot(3, 3, 7), plt.imshow(mask, 'gray')
plt.title('Threshold Result'), plt.xticks([]), plt.yticks([])
plt.subplot(3, 3, 8), plt.imshow(hst_eq, 'gray')
plt.title('Histogram Equalization'), plt.xticks([]), plt.yticks([])

plt.show()
