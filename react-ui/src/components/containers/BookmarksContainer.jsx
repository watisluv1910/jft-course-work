import React from 'react';
import {useBookmarksContext} from '../../contexts/useBookmarksContext';
import {Bookmark} from '../Bookmark';

/**
 * A component that renders a list of bookmarked items.
 * It uses the `useBookmarksContext` hook to access
 * the current bookmarks from the Bookmarks Context.
 *
 * @component
 * @return {React.JSX.Element} The rendered BookmarksContainer component.
 */
export const BookmarksContainer = () => {
    const {bookmarks} = useBookmarksContext();
    return (
        <div className={"container d-grid p-0"}>
            {bookmarks.reverse().map((bookmark) => (
                <Bookmark
                    key={bookmark.id}
                    bookmark={bookmark}
                />
            ))}
        </div>
    );
};
