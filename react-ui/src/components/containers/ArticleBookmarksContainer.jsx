import React from 'react';
import {useBookmarksContext} from '../../context/useBookmarksContext';
import {ArticleBookmark} from '../bookmark/ArticleBookmark';

/**
 * A component that renders a list of bookmarked items.
 * It uses the `useBookmarksContext` hook to access
 * the current bookmarks from the Bookmarks Context.
 *
 * @component
 * @return {React.JSX.Element} The rendered ArticleBookmarksContainer component.
 */
export const ArticleBookmarksContainer = () => {
    const {bookmarks} = useBookmarksContext();
    return (
        <div className={"container d-grid p-0"}>
            {bookmarks.reverse().map((bookmark) => (
                <ArticleBookmark
                    key={bookmark.id}
                    bookmark={bookmark}
                />
            ))}
        </div>
    );
};
