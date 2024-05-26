import {
    StoryMeta,
    StoryMetaElement,
    StoryTitle,
    StoryWrapper,
} from '../../assets/styles/StoryStyles';
import {mapTime} from '../../mappers/mapTime';
import React from 'react';
import {Link} from 'react-router-dom';

export const BlogListItem = ({_, blog}) => {
    return blog && (
        <StoryWrapper data-testid={'blog'}>
            <StoryTitle className={'fw-bold '}>
                <Link to={`/blogs/${blog.id}`}>
                    {blog.title}
                </Link>
            </StoryTitle>
            <small className={'text-muted mb-lg-2'}>
                {blog.description}
            </small>
            <StoryMeta>
                <span data-test-id="story-by">
                    <StoryMetaElement color="#000">
                        Author:
                    </StoryMetaElement> {blog.author.username}
                </span>
                <span data-test-id="story-time">
                    <StoryMetaElement color="#000">
                        Last Edited:
                    </StoryMetaElement> {` `}
                    {mapTime(blog.lastEditDate / 1000) + ' ago'}
                </span>
                <span data-test-id="story-by">
                    <StoryMetaElement color="#000">
                        By:
                    </StoryMetaElement> {blog.lastEditor.username}
                </span>
            </StoryMeta>
        </StoryWrapper>
    );
};
