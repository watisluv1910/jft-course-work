import React from 'react';
import PropTypes from 'prop-types';
import bookmarkIconOutline from '../../assets/icons/bookmark_icon_outline.png';
import bookmarkIconFilled from '../../assets/icons/bookmark_icon_filled.png';
import {BookmarkService} from '../../services/bookmark.service';
import {useBookmarksContext} from '../../context/useBookmarksContext';

/**
 * The ArticleBookmarkButton component is used to bookmark a story.
 * It uses the 'useBookmarksContext' hook to provide bookmark state management.
 * If the story is already bookmarked, it displays a filled bookmark icon,
 * otherwise an outline.
 * Clicking on the button toggles the bookmarked state of the story.
 *
 * @component
 * @param {Object} props - Component props
 * @param {Object} props.story - Story object to be bookmarked
 * @return {React.Element} The rendered ArticleBookmarkButton component.
 * @example
 * <ArticleBookmarkButton story={story} />
 * @author Vladislav Nasevich
 */
export const ArticleBookmarkButton = ({story}) => {
    const {bookmarks, setBookmarks} = useBookmarksContext();

    const isBookmarked = () => bookmarks.some(
        (bookmark) => bookmark.articleTitle === story.title,
    );

    const getBookmarkIcon =
        () => isBookmarked() ? bookmarkIconFilled : bookmarkIconOutline;

    const handleBookmarkClick = () => {
        isBookmarked() ? deleteBookmark(story) : addBookmark(story);
    };

    const addBookmark = async (story) => {
        try {
            await BookmarkService.addBookmark(story).then(async () => {
                setBookmarks(await BookmarkService.getUserBookmarks());
            });
        } catch (error) {
            console.error('Error adding bookmark: ', error);
        }
    };

    const deleteBookmark = async (story) => {
        const currentStoryBookmarks = bookmarks
            .filter((bookmark) => bookmark.articleTitle === story.title);

        for (const currentStoryBookmark of currentStoryBookmarks) {
            try {
                await BookmarkService
                    .deleteBookmark(currentStoryBookmark.id)
                    .then(() => {
                        setBookmarks(
                            bookmarks.filter(
                                (bookmark) =>
                                    bookmark.id !== currentStoryBookmark.id),
                        );
                    });
            } catch (error) {
                console.error('Error deleting bookmark: ', error);
            }
        }
    };

    return (
        <button
            className={'border-0 bg-transparent'}
            onClick={handleBookmarkClick}
        >
            <img
                src={getBookmarkIcon()}
                alt="Bookmark"
                style={{width: 24, maxWidth: 24, aspectRatio: '1 / 1'}}
            />
        </button>
    );
};

ArticleBookmarkButton.propTypes = {
    story: PropTypes.object.isRequired,
};
