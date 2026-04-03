/*
 * NM002
 *
 * v 00.001 - 10/25/2024
 *
 * PIC: emonteverde
 * 
 * Description: Used to provide a drag-and-drop file upload functionality with image preview, allowing users to upload a file or select from default images.
 * 
 */

import React, { useCallback, useEffect, useState } from 'react';
import { Box, Paper, Typography, IconButton, CircularProgress } from '@mui/material';
import CloudUploadIcon from '@mui/icons-material/CloudUpload';
import { labelText } from '../common/appConstant';

interface DragDropFileUploadProps {
    onFileUpload: (file: File | null, imageSrc?: string) => void;
    defaultImages: string[];
}

const DragDropFileUpload: React.FC<DragDropFileUploadProps> = ({ onFileUpload, defaultImages }) => {
    const [dragOver, setDragOver] = useState(false);
    const [loading, setLoading] = useState(false);
    const [imagePreview, setImagePreview] = useState<string | null>(null);

    const handleDragOver = useCallback((event: React.DragEvent<HTMLDivElement>) => {
        event.preventDefault();
        setDragOver(true);
    }, []);

    const handleDragLeave = useCallback((event: React.DragEvent<HTMLDivElement>) => {
        event.preventDefault();
        setDragOver(false);
    }, []);

    const handleDrop = useCallback(
        (event: React.DragEvent<HTMLDivElement>) => {
            event.preventDefault();
            setDragOver(false);
            const files = event.dataTransfer.files;
            if (files && files[0]) {
                handleFileChange(files[0]);
            }
        },
        []
    );

    const handleFileChange = (file: File) => {
        setLoading(true);
        onFileUpload(file);
        const reader = new FileReader();
        reader.onloadend = () => {
            setLoading(false);
            setImagePreview(reader.result as string);
        };
        reader.readAsDataURL(file);
    };

    const handleDefaultImageSelect = (imageSrc: string) => {
        setImagePreview(imageSrc);
        onFileUpload(null, imageSrc);
    };

    const handleChange = useCallback(
        (event: React.ChangeEvent<HTMLInputElement>) => {
            const files = event.target.files;
            if (files && files[0]) {
                handleFileChange(files[0]);
            }
        },
        []
    );

    return (
        <Box>
            <Paper>
                {imagePreview && (
                    <Box
                        sx={{
                            height: '300px',
                            backgroundImage: `url(${imagePreview})`,
                            backgroundRepeat: 'no-repeat',
                            backgroundSize: 'cover',
                            backgroundPosition: 'center',
                        }}
                    />
                )}
            </Paper>

            <Paper
                variant="outlined"
                onDragOver={handleDragOver}
                onDragLeave={handleDragLeave}
                onDrop={handleDrop}
                sx={{
                    border: dragOver ? '2px dashed #000' : '2px dashed #aaa',
                    marginTop: 2,
                    textAlign: 'center',
                    cursor: 'pointer',
                    background: dragOver ? '#eee' : '#fafafa',
                    position: 'relative',
                    height: '80px'
                }}
            >
                <input
                    accept=".jpg,.jpeg,.png"
                    style={{ display: 'none' }}
                    id="raised-button-file"
                    type="file"
                    onChange={handleChange}
                />
                <label htmlFor="raised-button-file">
                    <Box display="flex" flexDirection="column" alignItems="center">
                        <IconButton color="primary" aria-label="upload picture" component="span">
                            <CloudUploadIcon sx={{ fontSize: 30 }} />
                        </IconButton>
                        <Typography>{labelText.dragAndDrop}</Typography>
                    </Box>
                </label>
                {loading && (
                    <CircularProgress
                        size={24}
                        sx={{
                            position: 'absolute',
                            top: '50%',
                            left: '50%',
                            marginTop: '-12px',
                            marginLeft: '-12px',
                        }}
                    />
                )}
            </Paper>
            <Box sx={{ mt: 2 }}>
                <Typography variant="subtitle1" gutterBottom>
                    {labelText.useThisImage}
                </Typography>
                <Box display="flex" flexWrap={'wrap'} justifyContent="flex-start" gap={'3%'} width={'100%'}>
                    {defaultImages.map((imgSrc, index) => (
                        <Box sx={{width:'30%', mt: '3%', display:'flex', alignItems:'center', justifyContent:'center'}}>
                            <Box
                                key={index}
                                component="img"
                                src={imgSrc}
                                alt={`Default option ${index + 1}`}
                                sx={{
                                    width: '50%',
                                    maxHeight: 70,
                                    cursor: 'pointer',
                                    border: imagePreview === imgSrc ? '2px solid #1976d2' : '2px solid transparent',
                                    borderRadius: '4px',
                                }}
                                onClick={() => handleDefaultImageSelect(imgSrc)}
                            />
                        </Box>
                    ))}
                </Box>
            </Box>
        </Box>
    );
};

export default DragDropFileUpload;
