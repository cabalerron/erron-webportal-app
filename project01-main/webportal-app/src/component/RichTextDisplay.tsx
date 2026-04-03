/*
 * NM005
 *
 * v 00.001 - 10/23/2024
 *
 * PIC: emonteverde
 * 
 * Description: Used to display the news_content with format applied.
 * 
 */
import React from 'react';
import DOMPurify from 'dompurify';

interface RichTextDisplayProps {
    content: string;
}

const RichTextDisplay: React.FC<RichTextDisplayProps> = ({ content }) => {

    // Sanitize the HTML content to prevent XSS
    const sanitizedHtml = DOMPurify.sanitize(content);

    return (
        <div
            className="quill-content"
            dangerouslySetInnerHTML={{ __html: sanitizedHtml }}
        />);
};

export default RichTextDisplay;
