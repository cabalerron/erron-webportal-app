/*
 * Default Images Display 
 *
 * v 00.001 - 11/06/2024
 *
 * PIC: emonteverde
 * 
 * Description: Used to display a default images 
 * 
 */

import React, { useEffect, useState } from 'react';
import apiAxiosConfig from '../common/apiAxiosConfig';  
import '../style/DefaultImagesDisplay.css';

interface ImageResponse {
    name: string;
    path: string;
}

interface DefaultImagesDisplayProps {
    onImageClick: (imagePath: string) => void;
}

const DefaultImagesDisplay: React.FC<DefaultImagesDisplayProps> = ({ onImageClick }) => {
    const [images, setImages] = useState<ImageResponse[]>([]);

    useEffect(() => {
        apiAxiosConfig.get<ImageResponse[]>('/NM003/displayImages')
            .then(response => {
                // console.log("images:", response.data); 
                setImages(response.data.slice(0, 6)); 
            })
            .catch(error => {
                console.error("Error fetching images:", error);
            });
    }, []);

    return (
        <div className="default-images-display">
            {images.length > 0 ? (
                images.map((image, index) => (
                    <div 
                        key={index} 
                        className="image-container" 
                        onClick={() => onImageClick(image.path)} 
                    >
                        <img 
                            src={image.path} 
                            alt={`Default ${index + 1}`} 
                            className="image" 
                        />
                    </div>
                ))
            ) : (
                <p>No images available.</p>
            )}
        </div>
    );
};

export default DefaultImagesDisplay;
