import React from 'react';
import PropTypes from 'prop-types';
import {
    StoryMeta,
    StoryMetaElement,
    StoryTitle,
    StoryWrapper,
} from '../../assets/styles/StoryStyles';
import {ArticleBookmarkButton} from './ArticleBookmarkButton';

/**
 * A component that displays a bookmarked story.
 * The component renders the bookmarked story information
 * and also provides an option to unbookmark the story.
 *
 * @component
 * @param {Object} props - Component props.
 * @param {Object} props.bookmark - The bookmark data to display.
 * @return {JSX.Element} The rendered Bookmark component.
 * @example
 * <Bookmark={bookmarkData} />
 *
 * @author Vladislav Nasevich
 */
export const ArticleBookmark = ({bookmark}) => {
    return (
        <StoryWrapper data-testid="story">
            <StoryTitle>
                <a href={bookmark.articleUrl}>{bookmark.articleTitle}</a>
                <ArticleBookmarkButton story={Object.create({
                    title: bookmark.articleTitle,
                    url: bookmark.articleUrl,
                })}/>
            </StoryTitle>

            <StoryMeta>
                {bookmark.creationDate &&
                    <span data-test-id="story-time" className="story-time">
                        <StoryMetaElement
                            color="#000"
                            className="story-meta-element"
                        >
                            Added at:{' '}
                        </StoryMetaElement>
                        {new Date(bookmark.creationDate).toLocaleString()}
                    </span>
                }
            </StoryMeta>
        </StoryWrapper>
    );
};

ArticleBookmark.propTypes = {
    bookmark: PropTypes.object.isRequired,
};
